package com.javatechie.keycloak.repository;

import com.javatechie.keycloak.entity.FormData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<FormData, Integer> {
}
