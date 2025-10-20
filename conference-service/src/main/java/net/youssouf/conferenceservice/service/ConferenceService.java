package net.youssouf.conferenceservice.service;

import net.youssouf.conferenceservice.dto.ConferenceRequestDTO;
import net.youssouf.conferenceservice.dto.ConferenceResponseDTO;
import net.youssouf.conferenceservice.entity.Conference;
import net.youssouf.conferenceservice.exception.ConferenceNotFoundException;
import net.youssouf.conferenceservice.mapper.ConferenceMapper;
import net.youssouf.conferenceservice.repository.ConferenceRepository;
import net.youssouf.conferenceservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final ReviewRepository reviewRepository;
    private final ConferenceMapper conferenceMapper;

    @Transactional
    public ConferenceResponseDTO createConference(ConferenceRequestDTO conferenceDTO) {
        log.info("Creating new conference: {}", conferenceDTO.getTitre());
        
        // Vérifier si une conférence avec le même titre et la même date existe déjà
        if (conferenceRepository.existsByTitreAndDateHeureDebut(
                conferenceDTO.getTitre(), 
                conferenceDTO.getDateHeureDebut())) {
            throw new IllegalArgumentException("Une conférence avec le même titre et la même date existe déjà");
        }
        
        // Vérifier que la date de fin est après la date de début
        if (conferenceDTO.getDateHeureFin().isBefore(conferenceDTO.getDateHeureDebut())) {
            throw new IllegalArgumentException("La date de fin doit être postérieure à la date de début");
        }
        
        // Vérifier que le nombre de places est positif
        if (conferenceDTO.getNombrePlacesDisponibles() <= 0) {
            throw new IllegalArgumentException("Le nombre de places disponibles doit être supérieur à zéro");
        }
        
        Conference conference = conferenceMapper.toEntity(conferenceDTO);
        
        // Si le statut n'est pas fourni, le définir sur PREVUE par défaut
        if (conference.getStatut() == null) {
            conference.setStatut(Conference.StatutConference.PREVUE);
        }
        
        Conference savedConference = conferenceRepository.save(conference);
        log.info("Created conference with ID: {}", savedConference.getId());
        
        return conferenceMapper.toDto(savedConference);
    }

    @Transactional(readOnly = true)
    public ConferenceResponseDTO getConferenceById(Long id) {
        log.info("Fetching conference with ID: {}", id);
        Conference conference = conferenceRepository.findById(id)
                .orElseThrow(() -> new ConferenceNotFoundException("Conférence non trouvée avec l'ID : " + id));
        
        ConferenceResponseDTO response = conferenceMapper.toDto(conference);
        
        // Calculer la note moyenne des avis
        Double averageRating = reviewRepository.findAverageNoteByConferenceId(id);
        response.setNoteMoyenne(averageRating != null ? averageRating : 0.0);
        
        return response;
    }

    @Transactional(readOnly = true)
    public List<ConferenceResponseDTO> getAllConferences() {
        log.info("Fetching all conferences");
        return conferenceRepository.findAll().stream()
                .map(conference -> {
                    ConferenceResponseDTO dto = conferenceMapper.toDto(conference);
                    Double averageRating = reviewRepository.findAverageNoteByConferenceId(conference.getId());
                    dto.setNoteMoyenne(averageRating != null ? averageRating : 0.0);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConferenceResponseDTO> getUpcomingConferences() {
        log.info("Fetching upcoming conferences");
        return conferenceRepository.findByDateHeureDebutAfter(LocalDateTime.now()).stream()
                .map(conference -> {
                    ConferenceResponseDTO dto = conferenceMapper.toDto(conference);
                    Double averageRating = reviewRepository.findAverageNoteByConferenceId(conference.getId());
                    dto.setNoteMoyenne(averageRating != null ? averageRating : 0.0);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public ConferenceResponseDTO updateConference(Long id, ConferenceRequestDTO conferenceDTO) {
        log.info("Updating conference with ID: {}", id);
        
        Conference existingConference = conferenceRepository.findById(id)
                .orElseThrow(() -> new ConferenceNotFoundException("Conférence non trouvée avec l'ID : " + id));
        
        // Mettre à jour les champs non nuls du DTO
        if (conferenceDTO.getTitre() != null) {
            existingConference.setTitre(conferenceDTO.getTitre());
        }
        if (conferenceDTO.getDescription() != null) {
            existingConference.setDescription(conferenceDTO.getDescription());
        }
        if (conferenceDTO.getDateHeureDebut() != null) {
            existingConference.setDateHeureDebut(conferenceDTO.getDateHeureDebut());
        }
        if (conferenceDTO.getDateHeureFin() != null) {
            existingConference.setDateHeureFin(conferenceDTO.getDateHeureFin());
        }
        if (conferenceDTO.getLieu() != null) {
            existingConference.setLieu(conferenceDTO.getLieu());
        }
        if (conferenceDTO.getNombrePlacesDisponibles() != null) {
            existingConference.setNombrePlacesDisponibles(conferenceDTO.getNombrePlacesDisponibles());
        }
        if (conferenceDTO.getOrganisateur() != null) {
            existingConference.setOrganisateur(conferenceDTO.getOrganisateur());
        }
        if (conferenceDTO.getTheme() != null) {
            existingConference.setTheme(conferenceDTO.getTheme());
        }
        if (conferenceDTO.getStatut() != null) {
            existingConference.setStatut(conferenceDTO.getStatut());
        }
        
        Conference updatedConference = conferenceRepository.save(existingConference);
        log.info("Updated conference with ID: {}", id);
        
        return conferenceMapper.toDto(updatedConference);
    }

    @Transactional
    public void deleteConference(Long id) {
        log.info("Deleting conference with ID: {}", id);
        
        if (!conferenceRepository.existsById(id)) {
            throw new ConferenceNotFoundException("Conférence non trouvée avec l'ID : " + id);
        }
        
        // Supprimer d'abord les avis associés
        reviewRepository.deleteByConferenceId(id);
        
        // Puis supprimer la conférence
        conferenceRepository.deleteById(id);
        log.info("Deleted conference with ID: {}", id);
    }
    
    @Transactional(readOnly = true)
    public List<ConferenceResponseDTO> searchConferences(String keyword) {
        log.info("Searching conferences with keyword: {}", keyword);
        return conferenceRepository.findByTitreContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        keyword, keyword).stream()
                .map(conference -> {
                    ConferenceResponseDTO dto = conferenceMapper.toDto(conference);
                    Double averageRating = reviewRepository.findAverageNoteByConferenceId(conference.getId());
                    dto.setNoteMoyenne(averageRating != null ? averageRating : 0.0);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ConferenceResponseDTO> getConferencesByOrganizer(String organizer) {
        log.info("Fetching conferences by organizer: {}", organizer);
        return conferenceRepository.findByOrganisateur(organizer).stream()
                .map(conference -> {
                    ConferenceResponseDTO dto = conferenceMapper.toDto(conference);
                    Double averageRating = reviewRepository.findAverageNoteByConferenceId(conference.getId());
                    dto.setNoteMoyenne(averageRating != null ? averageRating : 0.0);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
