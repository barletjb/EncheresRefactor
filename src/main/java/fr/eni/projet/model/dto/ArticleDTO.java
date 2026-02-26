package fr.eni.projet.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDTO {

    // A Verifier si besoin de refactor au fur et à mesure du refactor

    private Long id;
    private String photo;
    private String nom;
    private String description;
    private LocalDateTime dateDebutEncheres;
    private LocalDateTime dateFinEncheres;
    private int miseAPrix;
    private int prixVente;
    private String etatVente;
    private Long utilisateurId;
    private Long categorieId;
    private Long retraitId;
}
