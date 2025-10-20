package net.youssouf.keynoteservice.service;

import net.youssouf.keynoteservice.dto.KeynoteRequestDTO;
import net.youssouf.keynoteservice.dto.KeynoteResponseDTO;
import java.util.List;

public interface KeynoteService {
    KeynoteResponseDTO save(KeynoteRequestDTO keynoteDTO);
    KeynoteResponseDTO getKeynote(Long id);
    KeynoteResponseDTO update(KeynoteRequestDTO keynoteDTO, Long id);
    void delete(Long id);
    List<KeynoteResponseDTO> getAllKeynotes();
}
