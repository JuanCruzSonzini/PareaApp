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
public class CambiarRolRequest {
    
    @NotNull(message = "El rol es obligatorio (USUARIO o ADMIN)")
    private Rol rol;
    
}
