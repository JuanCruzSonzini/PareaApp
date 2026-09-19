package com.parea.controllers.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder 
@AllArgsConstructor 
@NoArgsConstructor
public class CambiarRolRequest {
    
    @NotBlank (message = "El rol es obligatorio (USUARIO o ADMIN)")
    private String rol;
    
}
