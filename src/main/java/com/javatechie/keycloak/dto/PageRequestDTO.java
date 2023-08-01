package com.javatechie.keycloak.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

@Getter
@Setter
public class PageRequestDTO {
    private Integer page;
    private Integer size;

    public PageRequestDTO() {
        this.page = 0;
        this.size = 10;
    }
    public Pageable getPageable(PageRequestDTO dto){
        Integer page= Objects.nonNull(dto.getPage())?dto.getPage():this.page;
        Integer size= Objects.nonNull(dto.getSize())?dto.getSize():this.size;
        return PageRequest.of(page,size);
    }
}
