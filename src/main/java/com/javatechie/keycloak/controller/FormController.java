package com.javatechie.keycloak.controller;

import com.javatechie.keycloak.entity.FormData;
import com.javatechie.keycloak.kafka.KafkaProducer;
import com.javatechie.keycloak.service.FormService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @RolesAllowed("user")
    public String showForm(Model model) {
        model.addAttribute("formData", new FormData());
        return "form";
    }

    @PostMapping("/save")
    @RolesAllowed("user")
    public String submitForm(@ModelAttribute("formData") @Valid FormData formData,
                             @RequestParam("address") String address,
                             @RequestParam("phone") String phone){
        formData.setAddress(address);
        formData.setPhone(phone);
        kafkaProducer.sendMessage(address+" "+phone);
        formService.saveFormData(formData);
        return "success";
    }
}
