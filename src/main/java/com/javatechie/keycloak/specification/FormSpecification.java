package com.javatechie.keycloak.specification;

import com.javatechie.keycloak.entity.FormData;
import org.springframework.data.jpa.domain.Specification;

public class FormSpecification {
    public static Specification<FormData> addressContains(String address) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("address")), "%" + address + "%");
    }
    public static Specification<FormData> phoneContains(String phone) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("phone")), "%" + phone + "%");
    }

}
