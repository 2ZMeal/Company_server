package com.ezmeal.company.infrastructure.persistence;

import com.ezmeal.company.domain.model.Company;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCompanyRepository extends JpaRepository<Company, UUID> {

}
