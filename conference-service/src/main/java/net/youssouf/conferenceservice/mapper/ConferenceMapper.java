package net.youssouf.conferenceservice.mapper;

import net.youssouf.conferenceservice.dto.ConferenceRequestDTO;
import net.youssouf.conferenceservice.dto.ConferenceResponseDTO;
import net.youssouf.conferenceservice.entity.Conference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConferenceMapper {
    
    public Conference toEntity(ConferenceRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Conference.builder()
                .titre(dto.getTitre())
                .description(dto.getDescription())
                .dateHeureDebut(dto.getDateHeureDebut())
                .dateHeureFin(dto.getDateHeureFin())
                .lieu(dto.getLieu())
                .nombrePlacesDisponibles(dto.getNombrePlacesDisponibles())
                .organisateur(dto.getOrganisateur())
                .theme(dto.getTheme())
                .statut(dto.getStatut())
                .build();
    }
    
    public ConferenceResponseDTO toDto(Conference entity) {
        if (entity == null) {
            return null;
        }
        
        return ConferenceResponseDTO.builder()
                .id(entity.getId())
                .titre(entity.getTitre())
                .description(entity.getDescription())
                .dateHeureDebut(entity.getDateHeureDebut())
                .dateHeureFin(entity.getDateHeureFin())
                .lieu(entity.getLieu())
                .nombrePlacesDisponibles(entity.getNombrePlacesDisponibles())
                .organisateur(entity.getOrganisateur())
                .theme(entity.getTheme())
                .statut(entity.getStatut())
                // La note moyenne sera calculée dans le service
                .build();
    }
    
    public List<ConferenceResponseDTO> toDtoList(List<Conference> conferences) {
        return conferences.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
