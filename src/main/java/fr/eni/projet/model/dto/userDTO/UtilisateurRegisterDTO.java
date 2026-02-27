package fr.eni.projet.model.dto.userDTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilisateurRegisterDTO {

    @NotBlank
    private String pseudo;

    @Email
    private String email;

    @NotBlank
    private String motDePasse;

    @NotBlank
    private String confirmationMotDePasse;

    private String nom;
    private String prenom;

}
