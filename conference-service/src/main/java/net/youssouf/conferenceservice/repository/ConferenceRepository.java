package net.youssouf.conferenceservice.repository;

import net.youssouf.conferenceservice.entity.Conference;
import net.youssouf.conferenceservice.entity.Conference.StatutConference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {
    
    List<Conference> findByStatut(StatutConference statut);
    
    List<Conference> findByDateHeureDebutAfter(LocalDateTime date);
    
    List<Conference> findByDateHeureDebutBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Conference> findByOrganisateur(String organisateur);
    
    List<Conference> findByThemeContainingIgnoreCase(String theme);
    
    List<Conference> findByTitreContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String titre, String description);
    
    boolean existsByTitreAndDateHeureDebut(String titre, LocalDateTime dateHeureDebut);
}
