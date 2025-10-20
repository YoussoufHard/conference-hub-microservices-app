package net.youssouf.conferenceservice.repository;

import net.youssouf.conferenceservice.entity.Conference;
import net.youssouf.conferenceservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    List<Review> findByConferenceId(Long conferenceId);
    
    List<Review> findByAuteurId(String auteurId);
    
    Optional<Review> findByConferenceIdAndAuteurId(Long conferenceId, String auteurId);
    
    @Query("SELECT AVG(r.note) FROM Review r WHERE r.conference.id = :conferenceId")
    Double findAverageNoteByConferenceId(@Param("conferenceId") Long conferenceId);
    
    int countByConference(Conference conference);
    
    boolean existsByConferenceIdAndAuteurId(Long conferenceId, String auteurId);
    
    @Modifying
    @Query("DELETE FROM Review r WHERE r.conference.id = :conferenceId")
    void deleteByConferenceId(@Param("conferenceId") Long conferenceId);
}
