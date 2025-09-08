package com.creafund.creafund_api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private Projet projet;

    @ManyToOne
    @JoinColumn(name = "prestation_id")
    @JsonBackReference
    private Prestation prestation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Media media = (Media) o;
        return id != null && id.equals(media.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
