package com.example.demo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "Lütfen isminizi giriniz.")
    private String name;

    @Email(message = "Lütfen geçerli bir e-posta giriniz.")
    private String email;

    @NotBlank
    @Size(min = 8, message = "Şifreniz en az 8 karakter içermelidir.")
    private String password;

}
