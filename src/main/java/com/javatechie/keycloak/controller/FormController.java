package com.javatechie.keycloak.controller;

import com.javatechie.keycloak.entity.FormData;
import com.javatechie.keycloak.service.FormService;
import com.javatechie.keycloak.kafka.KafkaProducer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormController {
    @Value("${keycloak.realm}")
    private String realm;

    private String clientId="springboot-keycloack";

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    private String redirectUri="http://localhost:8080/form";

    private final FormService formService;
    private final KafkaProducer kafkaProducer;

    @Autowired
    public FormController(FormService formService, KafkaProducer kafkaProducer) {
        this.formService = formService;
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        return "redirect:"+String.format("%s/realms/%s/protocol/openid-connect/auth?response_type=code&client_id=%s&redirect_uri=%s",
                authServerUrl, realm, clientId, redirectUri);
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("formData", new FormData());
        return "form";
    }

    @PostMapping("/save")
    public String submitForm(@ModelAttribute("formData") @Valid FormData formData, BindingResult bindingResult,
                             @RequestParam("address") String address, @RequestParam("phone") String phone) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        FormData formDataEdited = new FormData(address, phone);
        kafkaProducer.sendMessage(formDataEdited.toString());
        formService.saveFormData(formDataEdited);
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String showSuccessPage() {
        // Add any logic or model attributes needed for the success page
        return "success"; // Assuming you have a "success" view to show the success message.
    }
}
