package com.javatechie.keycloak.controller;

import com.javatechie.keycloak.entity.FormData;
import com.javatechie.keycloak.kafka.KafkaConsumer;
import org.springframework.data.domain.Page;
import com.javatechie.keycloak.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class SuccessController {
    private final KafkaConsumer kafkaConsumer;
    private final FormService formService;
    @Autowired
    public SuccessController(KafkaConsumer kafkaConsumer, FormService formService) {
        this.kafkaConsumer = kafkaConsumer;
        this.formService = formService;
    }

    @GetMapping("/view")
    @PreAuthorize("hasRole('client_user')")
    public String success(Model model, @RequestParam("view") String view){
        if("SEARCH".equals(view)){
            return "search";
        }
        List<FormData> all=formService.getFormRepo().findAll();
        model.addAttribute("all", all);
        FormData formData=kafkaConsumer.getFormData();
        model.addAttribute("formDataDisplay", formData);
        return "view";
    }

    @GetMapping("/makeSearch")
    @PreAuthorize("hasRole('client_user')")
    public String search(Model model,
                         @RequestParam(value = "search", required = false) String search,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "10") int size) {
        Page<FormData> data = formService.searchData(search, page, size);
        model.addAttribute("data", data);
        model.addAttribute("totalPages", data.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("search", search);
        return "search";
    }

}
