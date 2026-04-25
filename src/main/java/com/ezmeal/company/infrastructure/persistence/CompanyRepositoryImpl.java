package com.ezmeal.company.infrastructure.persistence;

import com.ezmeal.company.domain.model.Company;
import com.ezmeal.company.domain.repository.CompanyRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepository{

    private final JpaCompanyRepository jpaCompanyRepository;

    @Override
    public Company save(Company company) {
        return jpaCompanyRepository.save(company);
    }

    @Override
    public Optional<Company> findById(UUID companyId) {
        return jpaCompanyRepository.findById(companyId);
    }

    @Override
    public Boolean existsByName(String name) {
        return jpaCompanyRepository.existsByName(name);
    }

}
