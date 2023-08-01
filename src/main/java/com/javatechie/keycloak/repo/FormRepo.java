package com.javatechie.keycloak.repo;

import com.javatechie.keycloak.entity.FormData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository
public interface FormRepo extends JpaRepository<FormData, Long>, JpaSpecificationExecutor<FormData> {
}
