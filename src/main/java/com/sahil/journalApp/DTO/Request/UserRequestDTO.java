package com.sahil.journalApp.DTO.Request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 20)
    @Schema(example = "sahil123")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 5)
    @Schema(example = "Password@123")
    private String password;
}
