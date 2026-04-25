package com.ezmeal.company.domain.repository;

import com.ezmeal.company.domain.model.Company;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {

    Company save(Company company);
    Optional<Company> findById(UUID companyId);
    Boolean existsByName(String name);
}
