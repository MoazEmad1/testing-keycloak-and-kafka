package com.javatechie.keycloak.service;

import com.javatechie.keycloak.entity.FormData;
import com.javatechie.keycloak.repo.FormRepo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class FormService {
    private final FormRepo formRepo;

    @Autowired
    public FormService(FormRepo formRepo) {
        this.formRepo = formRepo;
    }
    public void saveFormData(FormData formData){
        formRepo.save(formData);
    }
}
