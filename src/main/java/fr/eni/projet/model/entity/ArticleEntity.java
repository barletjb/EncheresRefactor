package fr.eni.projet.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "article")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photo;

    @Column(nullable = false)
    private String nom;

    @Column(length = 1000)
    private String description;

    private LocalDateTime dateDebutEncheres;

    private LocalDateTime dateFinEncheres;

    private int miseAPrix;

    private int prixVente;

    private String etatVente;


    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<EnchereEntity> encheres;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    @ToString.Exclude
    private UtilisateurEntity utilisateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id")
    @ToString.Exclude
    private CategorieEntity categorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "retrait_id")
    @ToString.Exclude
    private RetraitEntity retrait;
}
