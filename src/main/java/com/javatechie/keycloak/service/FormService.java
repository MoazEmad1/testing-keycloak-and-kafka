package com.javatechie.keycloak.service;

import com.javatechie.keycloak.entity.FormData;
import com.javatechie.keycloak.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormService {
    private final FormRepository formRepository;
    @Autowired
    public FormService(FormRepository formRepository) {
        this.formRepository = formRepository;
    }
    public void saveFormData(FormData formData) {
        formRepository.save(formData);
    }


}
