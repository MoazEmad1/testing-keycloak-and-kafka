package com.javatechie.keycloak.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Data
public class FormData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Address is mandatory")
    private String address;
    @NotBlank(message = "Phone is mandatory")
    private String phone;

    public FormData(String address, String phone) {
        this.address = address;
        this.phone = phone;
    }
}