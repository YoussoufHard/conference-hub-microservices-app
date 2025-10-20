package net.youssouf.conferenceservice.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDTO {
    private Long id;
    private String commentaire;
    private Integer note;
    private String auteurId;
    private LocalDateTime dateCreation;
    private Long conferenceId;
}
