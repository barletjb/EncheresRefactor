package fr.eni.projet.model.entity;

import fr.eni.projet.bo.Article;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "retrait")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RetraitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rue;
    private String codePostal;
    private String ville;


    @ManyToOne
    @JoinColumn(name = "article_id_article")
    @ToString.Exclude
    private Article article;

}
