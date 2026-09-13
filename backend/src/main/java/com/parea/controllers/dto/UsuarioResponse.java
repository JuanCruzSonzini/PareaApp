package com.parea.controllers.dto;
import com.parea.entities.Rol;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder 
@AllArgsConstructor 
@NoArgsConstructor
public class UsuarioResponse {


    private Long id;
    private String nombre;
    private String email;
    private Rol rol;

    public static UsuarioResponse from(com.parea.entities.Usuario usuario){

        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .build();
    }
    
}
