package net.youssouf.conferenceservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDTO {
    
    @NotBlank(message = "Le commentaire ne peut pas être vide")
    @Size(max = 500, message = "Le commentaire ne peut pas dépasser 500 caractères")
    private String commentaire;
    
    @NotNull(message = "La note est obligatoire")
    @Min(value = 1, message = "La note minimale est 1")
    @Max(value = 10, message = "La note maximale est 10")
    private Integer note;
    
    @NotBlank(message = "L'ID de l'auteur est obligatoire")
    private String auteurId;
    
    @NotNull(message = "L'ID de la conférence est obligatoire")
    private Long conferenceId;
}
