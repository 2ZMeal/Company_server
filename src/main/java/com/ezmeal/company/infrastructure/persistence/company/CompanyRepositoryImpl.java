package com.ezmeal.company.infrastructure.persistence.company;

import com.ezmeal.company.application.dto.request.CompanySearchRequest;
import com.ezmeal.company.domain.model.Company;
import com.ezmeal.company.domain.repository.CompanyRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Optional<Company> findByIdAndDeletedAtIsNull(UUID companyId) {
        return jpaCompanyRepository.findByIdAndDeletedAtIsNull(companyId);
    }

    @Override
    public Boolean existsByNameAndDeletedAtIsNull(String name) {
        return jpaCompanyRepository.existsByNameAndDeletedAtIsNull(name);
    }

    @Override
    public Page<Company> searchCompanies(CompanySearchRequest request, Pageable pageable) {
        return jpaCompanyRepository.searchCompanies(request,pageable);
    }

}
