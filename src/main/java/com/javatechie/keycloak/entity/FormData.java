package com.javatechie.keycloak.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Data
public class FormData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "address is required")
    private String address;

    @NotBlank(message = "phone Number is required")
        private String phone;

    public FormData(String address, String phone) {
        this.address = address;
        this.phone = phone;
    }
}