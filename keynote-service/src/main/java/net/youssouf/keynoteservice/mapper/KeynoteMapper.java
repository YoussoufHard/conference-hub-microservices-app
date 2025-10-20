package net.youssouf.keynoteservice.mapper;

import net.youssouf.keynoteservice.dto.KeynoteRequestDTO;
import net.youssouf.keynoteservice.dto.KeynoteResponseDTO;
import net.youssouf.keynoteservice.entity.Keynote;
import org.springframework.stereotype.Component;

@Component
public class KeynoteMapper {
    
    public KeynoteResponseDTO toKeynoteResponseDTO(Keynote keynote) {
        if (keynote == null) {
            return null;
        }
        return KeynoteResponseDTO.builder()
                .id(keynote.getId())
                .nom(keynote.getNom())
                .prenom(keynote.getPrenom())
                .email(keynote.getEmail())
                .fonction(keynote.getFonction())
                .build();
    }
    
    public Keynote toKeynote(KeynoteRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return Keynote.builder()
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .email(dto.getEmail())
                .fonction(dto.getFonction())
                .build();
    }
}
