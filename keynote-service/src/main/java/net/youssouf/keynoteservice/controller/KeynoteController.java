package net.youssouf.keynoteservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.youssouf.keynoteservice.dto.KeynoteRequestDTO;
import net.youssouf.keynoteservice.dto.KeynoteResponseDTO;
import net.youssouf.keynoteservice.service.KeynoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/keynotes")
@RequiredArgsConstructor
@Tag(name = "Keynote", description = "API for managing keynotes")
public class KeynoteController {

    private final KeynoteService keynoteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new keynote")
    public KeynoteResponseDTO createKeynote(@Valid @RequestBody KeynoteRequestDTO keynoteDTO) {
        return keynoteService.save(keynoteDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a keynote by ID")
    public ResponseEntity<KeynoteResponseDTO> getKeynote(@PathVariable Long id) {
        return ResponseEntity.ok(keynoteService.getKeynote(id));
    }

    @GetMapping
    @Operation(summary = "Get all keynotes")
    public ResponseEntity<List<KeynoteResponseDTO>> getAllKeynotes() {
        return ResponseEntity.ok(keynoteService.getAllKeynotes());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a keynote")
    public ResponseEntity<KeynoteResponseDTO> updateKeynote(
            @PathVariable Long id,
            @Valid @RequestBody KeynoteRequestDTO keynoteDTO) {
        return ResponseEntity.ok(keynoteService.update(keynoteDTO, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a keynote")
    public void deleteKeynote(@PathVariable Long id) {
        keynoteService.delete(id);
    }
}
