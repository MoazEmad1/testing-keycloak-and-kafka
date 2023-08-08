package com.javatechie.keycloak.service;

import com.javatechie.keycloak.dto.PageRequestDTO;
import com.javatechie.keycloak.entity.FormData;
import com.javatechie.keycloak.repo.FormRepo;
import com.javatechie.keycloak.specification.FormSpecification;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;


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

    public Page<FormData> searchData(String search, int page, int size) {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setPage(page);
        pageRequestDTO.setSize(size);
        Pageable pageable = pageRequestDTO.getPageable(pageRequestDTO);

        if (search != null && !search.isEmpty()) {
            Specification<FormData> spec = Specification
                    .where(FormSpecification.addressContains(search))
                    .or(FormSpecification.phoneContains(search));
            return formRepo.findAll(spec, pageable);
        } else {
            return formRepo.findAll(pageable);
        }
    }
}
