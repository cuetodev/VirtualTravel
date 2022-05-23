package com.cuetodev.backempresa.User.infrastructure.controller.dto.input;

import com.cuetodev.backempresa.User.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInputDTO {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String role = "ROLE_USER";

    public User convertEntityToDTO() {
        return new User(null, this.email, this.password, this.role);
    }
}
