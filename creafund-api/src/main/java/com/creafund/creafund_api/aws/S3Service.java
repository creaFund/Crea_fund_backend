package com.creafund.creafund_api.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Autowired
    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    // Méthode d'upload originale, inchangée
    public void uploadFile(MultipartFile file) throws IOException {
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(file.getOriginalFilename())
                        .build(),
                RequestBody.fromBytes(file.getBytes()));
    }

    // Nouvelle surcharge qui accepte une clé personnalisée (dossier + nom fichier)
    public S3ObjectInfo uploadFile(String key, MultipartFile file, boolean isPublic) throws IOException {
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build(),
                RequestBody.fromBytes(file.getBytes()));

        // Construire l’URL (publique de base)
        String url = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;

        // Optionnel : gérer les permissions ici si isPublic == false (non couvert)

        return new S3ObjectInfo(key, url);
    }

    // Classe interne pour retourner info fichier uploadé
    public static class S3ObjectInfo {
        private final String key;
        private final String url;

        public S3ObjectInfo(String key, String url) {
            this.key = key;
            this.url = url;
        }

        public String key() {
            return key;
        }

        public String url() {
            return url;
        }
    }

    public byte[] downloadFile(String key) {
        ResponseBytes<GetObjectResponse> objectAsBytes =
                s3Client.getObjectAsBytes(GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build());
        return objectAsBytes.asByteArray();
    }
}
