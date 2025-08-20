package com.creafund.creafund_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Media {
    @Id
    @GeneratedValue
    private Long id;

    private String url;     // lien S3
    private String fileName;
    private String type;    // image, video, pdf...

    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;

    @ManyToOne
    @JoinColumn(name = "prestation_id")
    private Prestation prestation;
}
