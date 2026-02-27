package fr.eni.projet.model.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RetraitDTO {

    private Long id;
    private String rue;
    private String codePostal;
    private String ville;
    private Long articleId;
}
