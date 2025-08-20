package com.creafund.creafund_api.aws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Service
public class S3Service {

    private static final Logger log = LoggerFactory.getLogger(S3Service.class);

    private final S3Client s3Client;

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    /* =======================
       Méthode HISTORIQUE (compatibilité)
       ======================= */
    /**
     * Upload avec la clé = nom de fichier d'origine.
     * (Conservée pour compatibilité avec ton code existant)
     */
    public void uploadFile(MultipartFile file) throws IOException {
        Objects.requireNonNull(file, "file ne peut pas être null");
        String original = Objects.requireNonNullElse(file.getOriginalFilename(), "file.bin");

        putObjectInternal(original, file.getBytes(), file.getContentType(), false);
        log.info("Upload S3 terminé (legacy). key={}", original);
    }

    /* =======================
       Nouvelles méthodes recommandées
       ======================= */

    /**
     * Upload et renvoie l’URL publique/théorique de l’objet (si le bucket/policy le permet).
     * La clé générée est unique (UUID + timestamp + nom nettoyé).
     */
    public String uploadFileAndGetUrl(MultipartFile file) throws IOException {
        S3ObjectInfo info = uploadFile(null, file, false);
        return info.url();
    }

    /**
     * Upload avancé avec:
     * - prefix (ex: "projets/123" ou "prestations/45")
     * - contrôle public/privé via ACL
     * - retour d’un objet info (key, url, etag, size, contentType)
     */
    public S3ObjectInfo uploadFile(String prefix, MultipartFile file, boolean makePublic) throws IOException {
        Objects.requireNonNull(file, "file ne peut pas être null");
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide");
        }

        String cleanName = sanitizeFilename(Objects.requireNonNullElse(file.getOriginalFilename(), "file.bin"));
        String key = buildKey(prefix, cleanName);

        byte[] bytes = file.getBytes();
        String contentType = file.getContentType();

        PutObjectResponse response = putObjectInternal(key, bytes, contentType, makePublic);

        String url = getObjectUrl(key); // URL utilisable si bucket public ou si tu génères un presigned plus tard
        log.info("Upload S3 OK key={} (public={})", key, makePublic);

        return new S3ObjectInfo(key, url, response.eTag(), bytes.length, contentType);
    }

    /**
     * Télécharger un objet (byte[]).
     */
    public byte[] downloadFile(String key) {
        ResponseBytes<GetObjectResponse> objectAsBytes =
                s3Client.getObjectAsBytes(GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build());
        return objectAsBytes.asByteArray();
    }

    /**
     * Supprimer un objet.
     */
    public void deleteFile(String key) {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build());
        log.info("Objet S3 supprimé key={}", key);
    }

    /**
     * Tester l’existence d’un objet (HEAD).
     */
    public boolean exists(String key) {
        try {
            s3Client.headObject(HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build());
            return true;
        } catch (S3Exception e) {
            if ("NotFound".equals(e.awsErrorDetails().errorCode()) ||
                    "NoSuchKey".equals(e.awsErrorDetails().errorCode())) {
                return false;
            }
            throw e; // relancer si c'est une autre erreur
        }
    }

    /**
     * Construire l’URL “publique”/directe d’un objet (fonctionne out-of-the-box si bucket/policy autorise la lecture).
     * Sinon, cette URL sera 403 — tu pourras générer un pre-signed URL si nécessaire (méthode à ajouter plus tard).
     */
    public String getObjectUrl(String key) {
        return s3Client.utilities()
                .getUrl(GetUrlRequest.builder().bucket(bucketName).key(key).build())
                .toExternalForm();
    }

    /* =======================
       Helpers internes
       ======================= */

    private PutObjectResponse putObjectInternal(String key, byte[] bytes, String contentType, boolean publicRead) {
        PutObjectRequest.Builder req = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentLength((long) bytes.length);

        if (contentType != null && !contentType.isBlank()) {
            req.contentType(contentType);
        }

        if (publicRead) {
            req.acl(ObjectCannedACL.PUBLIC_READ);
        }

        return s3Client.putObject(req.build(), RequestBody.fromBytes(bytes));
    }

    private String buildKey(String prefix, String filename) {
        String unique = UUID.randomUUID() + "_" + Instant.now().toEpochMilli();
        String key = unique + "_" + filename;

        if (prefix == null || prefix.isBlank()) {
            return key;
        }
        // enlever éventuels slashes en trop
        String p = prefix.replaceAll("^/+", "").replaceAll("/+$", "");
        return p + "/" + key;
    }

    private String sanitizeFilename(String name) {
        String n = name.trim();
        // remplace espaces par _ et supprime caractères problématiques basiques
        n = n.replace(" ", "_")
                .replaceAll("[\\r\\n\\t]", "_");

        // S3 accepte large set; on fait simple:
        if (n.isBlank()) {
            n = "file.bin";
        }

        // garantir encodage UTF-8 safe-ish
        return new String(n.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

    /* =======================
       DTO d’info retour upload
       ======================= */
    public record S3ObjectInfo(
            String key,
            String url,
            String eTag,
            long size,
            String contentType
    ) {}
}
