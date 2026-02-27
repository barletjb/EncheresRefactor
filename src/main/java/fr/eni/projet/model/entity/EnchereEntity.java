package fr.eni.projet.model.entity;

import fr.eni.projet.bo.Article;
import fr.eni.projet.bo.Utilisateur;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table (name = "enchere")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EnchereEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateEnchere;
    private int montantEnchere;


    @ManyToOne
    @JoinColumn(name = "utilisateur_id_utilisateur")
    @ToString.Exclude
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "enchere_article_id_article")
    @ToString.Exclude
    private Article enchereArticle;

}
