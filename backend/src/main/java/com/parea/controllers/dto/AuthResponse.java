package com.parea.controllers.dto;

import com.parea.entities.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String token;
    private Long id;
    private String nombre;
    private String email;
    private Rol rol;
}
