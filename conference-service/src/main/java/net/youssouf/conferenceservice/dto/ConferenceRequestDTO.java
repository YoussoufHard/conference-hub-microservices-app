package net.youssouf.conferenceservice.dto;

import lombok.*;
import net.youssouf.conferenceservice.entity.Conference.StatutConference;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConferenceRequestDTO {
    private String titre;
    private String description;
    private LocalDateTime dateHeureDebut;
    private LocalDateTime dateHeureFin;
    private String lieu;
    private Integer nombrePlacesDisponibles;
    private String organisateur;
    private String theme;
    private StatutConference statut;
}
