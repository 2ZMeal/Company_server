package com.ezmeal.company.infrastructure.persistence;

import com.ezmeal.company.domain.model.Company;
import com.ezmeal.company.domain.repository.CompanyRepository;
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

}
