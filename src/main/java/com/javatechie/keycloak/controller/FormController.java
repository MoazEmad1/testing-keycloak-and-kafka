package com.javatechie.keycloak.controller;

import com.javatechie.keycloak.entity.FormData;
import com.javatechie.keycloak.kafka.KafkaProducer;
import com.javatechie.keycloak.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class FormController {
    private final KafkaProducer kafkaProducer;
    private final FormService formService;

    @Autowired
    public FormController(KafkaProducer kafkaProducer, FormService formService) {
        this.kafkaProducer = kafkaProducer;
        this.formService = formService;
    }

    @GetMapping("/form")
    @PreAuthorize("hasRole('user')")
    public String showForm(Model model) {
        model.addAttribute("formData", new FormData());
        return "form";
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('client_user')")
    public String submitForm(@ModelAttribute("formData") FormData formData) {
        kafkaProducer.sendMessage(formData.getAddress() + " " + formData.getPhone());
        formService.saveFormData(formData);
        return "success";
    }
}

