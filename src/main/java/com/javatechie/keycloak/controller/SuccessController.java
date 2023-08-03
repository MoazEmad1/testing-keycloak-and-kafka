package com.javatechie.keycloak.controller;

import com.javatechie.keycloak.dto.PageRequestDTO;
import com.javatechie.keycloak.entity.FormData;
import com.javatechie.keycloak.kafka.KafkaConsumer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.javatechie.keycloak.service.FormService;
import com.javatechie.keycloak.specification.FormSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
    @PreAuthorize("hasRole('user')")
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
    @PreAuthorize("hasRole('user')")
    public String search(Model model,
                         @RequestParam(value = "search", required = false) String search,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "10") int size) {
        Page<FormData> data;
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setPage(page);
        pageRequestDTO.setSize(size);
        Pageable pageable = pageRequestDTO.getPageable(pageRequestDTO);
        if (search != null && !search.isEmpty()) {
            Specification<FormData> spec = Specification
                    .where(FormSpecification.addressContains(search))
                    .or(FormSpecification.phoneContains(search));
            data = formService.getFormRepo().findAll(spec, pageable);
        } else {
            data = formService.getFormRepo().findAll(pageable);
        }
        model.addAttribute("data", data);
        model.addAttribute("totalPages", data.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("search", search);
        return "search";
    }

}
