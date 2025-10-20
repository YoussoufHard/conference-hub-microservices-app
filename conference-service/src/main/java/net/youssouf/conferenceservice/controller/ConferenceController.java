package net.youssouf.conferenceservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.youssouf.conferenceservice.dto.ConferenceRequestDTO;
import net.youssouf.conferenceservice.dto.ConferenceResponseDTO;
import net.youssouf.conferenceservice.service.ConferenceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conferences")
@RequiredArgsConstructor
@Tag(name = "Conference", description = "API pour la gestion des conférences")
public class ConferenceController {

    private final ConferenceService conferenceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer une nouvelle conférence")
    public ConferenceResponseDTO createConference(@Valid @RequestBody ConferenceRequestDTO conferenceDTO) {
        return conferenceService.createConference(conferenceDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une conférence par son ID")
    public ResponseEntity<ConferenceResponseDTO> getConferenceById(@PathVariable Long id) {
        return ResponseEntity.ok(conferenceService.getConferenceById(id));
    }

    @GetMapping
    @Operation(summary = "Récupérer toutes les conférences")
    public ResponseEntity<List<ConferenceResponseDTO>> getAllConferences(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        List<ConferenceResponseDTO> conferences = conferenceService.getAllConferences();
        return ResponseEntity.ok(conferences);
    }

    @GetMapping("/upcoming")
    @Operation(summary = "Récupérer les prochaines conférences")
    public ResponseEntity<List<ConferenceResponseDTO>> getUpcomingConferences() {
        return ResponseEntity.ok(conferenceService.getUpcomingConferences());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une conférence existante")
    public ResponseEntity<ConferenceResponseDTO> updateConference(
            @PathVariable Long id,
            @Valid @RequestBody ConferenceRequestDTO conferenceDTO) {
        
        return ResponseEntity.ok(conferenceService.updateConference(id, conferenceDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer une conférence")
    public void deleteConference(@PathVariable Long id) {
        conferenceService.deleteConference(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des conférences par mot-clé")
    public ResponseEntity<List<ConferenceResponseDTO>> searchConferences(
            @RequestParam String keyword) {
        
        return ResponseEntity.ok(conferenceService.searchConferences(keyword));
    }

    @GetMapping("/organizer/{organizer}")
    @Operation(summary = "Récupérer les conférences par organisateur")
    public ResponseEntity<List<ConferenceResponseDTO>> getConferencesByOrganizer(
            @PathVariable String organizer) {
        
        return ResponseEntity.ok(conferenceService.getConferencesByOrganizer(organizer));
    }
}
