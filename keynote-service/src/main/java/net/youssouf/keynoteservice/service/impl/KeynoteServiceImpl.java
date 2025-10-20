package net.youssouf.keynoteservice.service.impl;

import lombok.RequiredArgsConstructor;
import net.youssouf.keynoteservice.dto.KeynoteRequestDTO;
import net.youssouf.keynoteservice.dto.KeynoteResponseDTO;
import net.youssouf.keynoteservice.entity.Keynote;
import net.youssouf.keynoteservice.exception.KeynoteNotFoundException;
import net.youssouf.keynoteservice.mapper.KeynoteMapper;
import net.youssouf.keynoteservice.repository.KeynoteRepository;
import net.youssouf.keynoteservice.service.KeynoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class KeynoteServiceImpl implements KeynoteService {

    private final KeynoteRepository keynoteRepository;
    private final KeynoteMapper keynoteMapper;

    @Override
    public KeynoteResponseDTO save(KeynoteRequestDTO keynoteDTO) {
        Keynote keynote = keynoteMapper.toKeynote(keynoteDTO);
        Keynote savedKeynote = keynoteRepository.save(keynote);
        return keynoteMapper.toKeynoteResponseDTO(savedKeynote);
    }

    @Override
    @Transactional(readOnly = true)
    public KeynoteResponseDTO getKeynote(Long id) {
        Keynote keynote = keynoteRepository.findById(id)
                .orElseThrow(() -> new KeynoteNotFoundException("Keynote not found with id: " + id));
        return keynoteMapper.toKeynoteResponseDTO(keynote);
    }

    @Override
    public KeynoteResponseDTO update(KeynoteRequestDTO keynoteDTO, Long id) {
        Keynote existingKeynote = keynoteRepository.findById(id)
                .orElseThrow(() -> new KeynoteNotFoundException("Keynote not found with id: " + id));
        
        existingKeynote.setNom(keynoteDTO.getNom());
        existingKeynote.setPrenom(keynoteDTO.getPrenom());
        existingKeynote.setEmail(keynoteDTO.getEmail());
        existingKeynote.setFonction(keynoteDTO.getFonction());
        
        Keynote updatedKeynote = keynoteRepository.save(existingKeynote);
        return keynoteMapper.toKeynoteResponseDTO(updatedKeynote);
    }

    @Override
    public void delete(Long id) {
        if (!keynoteRepository.existsById(id)) {
            throw new KeynoteNotFoundException("Keynote not found with id: " + id);
        }
        keynoteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<KeynoteResponseDTO> getAllKeynotes() {
        return keynoteRepository.findAll().stream()
                .map(keynoteMapper::toKeynoteResponseDTO)
                .collect(Collectors.toList());
    }
}
