package net.youssouf.conferenceservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.youssouf.conferenceservice.dto.ReviewRequestDTO;
import net.youssouf.conferenceservice.dto.ReviewResponseDTO;
import net.youssouf.conferenceservice.entity.Conference;
import net.youssouf.conferenceservice.entity.Review;
import net.youssouf.conferenceservice.exception.ConferenceNotFoundException;
import net.youssouf.conferenceservice.exception.DuplicateReviewException;
import net.youssouf.conferenceservice.exception.ReviewNotFoundException;
import net.youssouf.conferenceservice.mapper.ReviewMapper;
import net.youssouf.conferenceservice.repository.ConferenceRepository;
import net.youssouf.conferenceservice.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ConferenceRepository conferenceRepository;
    private final ReviewMapper reviewMapper;

    @Transactional
    public ReviewResponseDTO createReview(ReviewRequestDTO reviewDTO) {
        log.info("Creating new review for conference ID: {} by user: {}", 
                reviewDTO.getConferenceId(), reviewDTO.getAuteurId());
        
        // Vérifier si la conférence existe
        Conference conference = conferenceRepository.findById(reviewDTO.getConferenceId())
                .orElseThrow(() -> new ConferenceNotFoundException(
                        "Conférence non trouvée avec l'ID : " + reviewDTO.getConferenceId()));
        
        // Vérifier si l'utilisateur a déjà posté un avis pour cette conférence
        if (reviewRepository.existsByConferenceIdAndAuteurId(
                reviewDTO.getConferenceId(), 
                reviewDTO.getAuteurId())) {
            throw new DuplicateReviewException("Vous avez déjà posté un avis pour cette conférence");
        }
        
        // Créer et sauvegarder l'avis
        Review review = reviewMapper.toEntity(reviewDTO);
        review.setConference(conference);
        
        Review savedReview = reviewRepository.save(review);
        log.info("Created review with ID: {}", savedReview.getId());
        
        return reviewMapper.toDto(savedReview);
    }

    @Transactional(readOnly = true)
    public ReviewResponseDTO getReviewById(Long id) {
        log.info("Fetching review with ID: {}", id);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Avis non trouvé avec l'ID : " + id));
        
        return reviewMapper.toDto(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> getReviewsByConferenceId(Long conferenceId) {
        log.info("Fetching all reviews for conference ID: {}", conferenceId);
        
        // Vérifier si la conférence existe
        if (!conferenceRepository.existsById(conferenceId)) {
            throw new ConferenceNotFoundException("Conférence non trouvée avec l'ID : " + conferenceId);
        }
        
        return reviewRepository.findByConferenceId(conferenceId).stream()
                .map(reviewMapper::toDto)
                .collect(java.util.stream.Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> getReviewsByUserId(String userId) {
        log.info("Fetching all reviews by user ID: {}", userId);
        return reviewRepository.findByAuteurId(userId).stream()
                .map(reviewMapper::toDto)
                .collect(java.util.stream.Collectors.toList());
    }

    @Transactional
    public ReviewResponseDTO updateReview(Long id, ReviewRequestDTO reviewDTO) {
        log.info("Updating review with ID: {}", id);
        
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Avis non trouvé avec l'ID : " + id));
        
        // Mettre à jour les champs non nuls
        if (reviewDTO.getCommentaire() != null) {
            existingReview.setCommentaire(reviewDTO.getCommentaire());
        }
        if (reviewDTO.getNote() != null) {
            existingReview.setNote(reviewDTO.getNote());
        }
        
        Review updatedReview = reviewRepository.save(existingReview);
        log.info("Updated review with ID: {}", id);
        
        return reviewMapper.toDto(updatedReview);
    }

    @Transactional
    public void deleteReview(Long id) {
        log.info("Deleting review with ID: {}", id);
        
        if (!reviewRepository.existsById(id)) {
            throw new ReviewNotFoundException("Avis non trouvé avec l'ID : " + id);
        }
        
        reviewRepository.deleteById(id);
        log.info("Deleted review with ID: {}", id);
    }
    
    @Transactional(readOnly = true)
    public Double getAverageRatingByConferenceId(Long conferenceId) {
        log.info("Calculating average rating for conference ID: {}", conferenceId);
        
        // Vérifier si la conférence existe
        if (!conferenceRepository.existsById(conferenceId)) {
            throw new ConferenceNotFoundException("Conférence non trouvée avec l'ID : " + conferenceId);
        }
        
        Double averageRating = reviewRepository.findAverageNoteByConferenceId(conferenceId);
        return averageRating != null ? averageRating : 0.0;
    }
    
    @Transactional(readOnly = true)
    public ReviewResponseDTO getUserReviewForConference(Long conferenceId, String userId) {
        log.info("Fetching review for conference ID: {} by user: {}", conferenceId, userId);
        
        // Vérifier si la conférence existe
        if (!conferenceRepository.existsById(conferenceId)) {
            throw new ConferenceNotFoundException("Conférence non trouvée avec l'ID : " + conferenceId);
        }
        
        return reviewRepository.findByConferenceIdAndAuteurId(conferenceId, userId)
                .map(reviewMapper::toDto)
                .orElse(null);
    }
}
