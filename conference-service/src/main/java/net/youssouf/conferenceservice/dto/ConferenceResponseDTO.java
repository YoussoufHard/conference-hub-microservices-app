package net.youssouf.conferenceservice.dto;

import lombok.*;
import net.youssouf.conferenceservice.entity.Conference.StatutConference;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConferenceResponseDTO {
    private Long id;
    private String titre;
    private String description;
    private LocalDateTime dateHeureDebut;
    private LocalDateTime dateHeureFin;
    private String lieu;
    private Integer nombrePlacesDisponibles;
    private String organisateur;
    private String theme;
    private StatutConference statut;
    private Double noteMoyenne; // Calculée à partir des avis
}
