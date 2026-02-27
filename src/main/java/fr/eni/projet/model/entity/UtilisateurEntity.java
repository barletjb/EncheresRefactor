package fr.eni.projet.model.entity;

import fr.eni.projet.bo.Article;
import fr.eni.projet.bo.Enchere;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.util.List;


@Entity
@Table(name = "utilisateur",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "pseudo"),
        @UniqueConstraint(columnNames = "email")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UtilisateurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tilisateur")
    private Long idUtilisateur;

    @NotBlank(message = "Le pseudo est obligatoire.")
    @Column(nullable = false)
    private String pseudo;

    @Pattern(regexp = "^[a-zA-Z0-9À-ÿ'\\- ]+$", message = "Caractères non autorisés")
    private String nom;

    @Pattern(regexp = "^[a-zA-Z0-9À-ÿ'\\- ]+$", message = "Caractères non autorisés")
    private String prenom;

    @Email(message = "Adresse email invalide")
    @Column(nullable = false)
    private String email;

    @Pattern(regexp = "^(\\+33|0)[1-9](\\s?\\d{2}){4}$", message = "Numéro de téléphone invalide")
    private String telephone;

    @Pattern(regexp = "^[a-zA-Z0-9À-ÿ'\\-,. ]+$", message = "Adresse invalide")
    private String rue;

    @Pattern(regexp = "^\\d{5}$", message = "Le code postal doit contenir 5 chiffres")
    @Column(name = "code_postal")
    private String codePostal;

    @Pattern(regexp = "^[a-zA-ZÀ-ÿ'\\- ]+$", message = "Ville invalide")
    private String ville;

    @NotBlank(message = "Le mot de passe est obligatoire.")
    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @Column(nullable = false)
    private int credit;


    //TODO Faire une ENUM
    @Column(nullable = false)
    private String roles;


    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Enchere> encheres;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Article> articles;

}
