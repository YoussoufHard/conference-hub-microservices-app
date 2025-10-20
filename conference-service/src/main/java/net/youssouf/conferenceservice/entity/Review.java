package net.youssouf.conferenceservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 500)
    private String commentaire;
    
    @Column(nullable = false)
    private Integer note; // Note sur 10
    
    @Column(nullable = false)
    private String auteurId; // ID de l'utilisateur qui a posté l'avis
    
    @Column(nullable = false)
    private LocalDateTime dateCreation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conference_id", nullable = false)
    @ToString.Exclude
    private Conference conference;
}
