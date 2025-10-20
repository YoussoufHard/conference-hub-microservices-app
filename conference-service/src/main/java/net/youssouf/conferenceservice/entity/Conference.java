package net.youssouf.conferenceservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "conferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conference {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String titre;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private LocalDateTime dateHeureDebut;
    
    @Column(nullable = false)
    private LocalDateTime dateHeureFin;
    
    @Column(nullable = false)
    private String lieu;
    
    @Column(nullable = false)
    private Integer nombrePlacesDisponibles;
    
    @Column(nullable = false)
    private String organisateur;
    
    @Column(nullable = false)
    private String theme;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatutConference statut;
    
    public enum StatutConference {
        PREVUE,
        EN_COURS,
        TERMINEE,
        ANNULEE
    }
}
