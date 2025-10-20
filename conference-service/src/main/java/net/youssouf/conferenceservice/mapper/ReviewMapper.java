package net.youssouf.conferenceservice.mapper;

import net.youssouf.conferenceservice.dto.ReviewRequestDTO;
import net.youssouf.conferenceservice.dto.ReviewResponseDTO;
import net.youssouf.conferenceservice.entity.Review;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReviewMapper {
    
    public Review toEntity(ReviewRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Review.builder()
                .commentaire(dto.getCommentaire())
                .note(dto.getNote())
                .auteurId(dto.getAuteurId())
                .dateCreation(java.time.LocalDateTime.now())
                .build();
    }
    
    public ReviewResponseDTO toDto(Review entity) {
        if (entity == null) {
            return null;
        }
        
        return ReviewResponseDTO.builder()
                .id(entity.getId())
                .commentaire(entity.getCommentaire())
                .note(entity.getNote())
                .auteurId(entity.getAuteurId())
                .dateCreation(entity.getDateCreation())
                .conferenceId(entity.getConference() != null ? entity.getConference().getId() : null)
                .build();
    }
    
    public List<ReviewResponseDTO> toDtoList(List<Review> reviews) {
        return reviews.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
