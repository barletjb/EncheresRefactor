package fr.eni.projet.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategorieDTO {

    private Long id;
    private String libelle;
    private Integer nombreArticles;

}