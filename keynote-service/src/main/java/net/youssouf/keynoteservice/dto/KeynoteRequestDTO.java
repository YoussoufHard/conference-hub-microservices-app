package net.youssouf.keynoteservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeynoteRequestDTO {
    private String nom;
    private String prenom;
    private String email;
    private String fonction;
}
