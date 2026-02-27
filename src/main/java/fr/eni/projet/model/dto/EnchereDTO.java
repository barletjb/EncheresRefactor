package fr.eni.projet.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnchereDTO {

    private Long id;
    private LocalDateTime date;
    private Long utilisateurId;
    private Long articleId;

}
