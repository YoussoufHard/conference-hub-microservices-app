package net.youssouf.conferenceservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.youssouf.conferenceservice.dto.ReviewRequestDTO;
import net.youssouf.conferenceservice.dto.ReviewResponseDTO;
import net.youssouf.conferenceservice.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Review", description = "API pour la gestion des avis sur les conférences")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un nouvel avis sur une conférence")
    public ReviewResponseDTO createReview(@Valid @RequestBody ReviewRequestDTO reviewDTO) {
        return reviewService.createReview(reviewDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un avis par son ID")
    public ResponseEntity<ReviewResponseDTO> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping("/conference/{conferenceId}")
    @Operation(summary = "Récupérer tous les avis pour une conférence")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByConferenceId(
            @PathVariable Long conferenceId) {
        
        return ResponseEntity.ok(reviewService.getReviewsByConferenceId(conferenceId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Récupérer tous les avis d'un utilisateur")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByUserId(
            @PathVariable String userId) {
        
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un avis existant")
    public ResponseEntity<ReviewResponseDTO> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody ReviewRequestDTO reviewDTO) {
        
        return ResponseEntity.ok(reviewService.updateReview(id, reviewDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un avis")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }

    @GetMapping("/conference/{conferenceId}/average")
    @Operation(summary = "Obtenir la note moyenne d'une conférence")
    public ResponseEntity<Double> getAverageRatingByConferenceId(
            @PathVariable Long conferenceId) {
        
        return ResponseEntity.ok(reviewService.getAverageRatingByConferenceId(conferenceId));
    }

    @GetMapping("/conference/{conferenceId}/user/{userId}")
    @Operation(summary = "Obtenir l'avis d'un utilisateur pour une conférence spécifique")
    public ResponseEntity<ReviewResponseDTO> getUserReviewForConference(
            @PathVariable Long conferenceId,
            @PathVariable String userId) {
        
        ReviewResponseDTO review = reviewService.getUserReviewForConference(conferenceId, userId);
        if (review != null) {
            return ResponseEntity.ok(review);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
