package com.ezmeal.company.application.service;

import com.ezmeal.common.exception.CustomException;
import com.ezmeal.company.application.dto.request.CompanyCreateRequest;
import com.ezmeal.company.application.dto.request.CompanySearchRequest;
import com.ezmeal.company.application.dto.request.CompanyUpdateRequest;
import com.ezmeal.company.application.dto.response.CompanyResponse;
import com.ezmeal.company.application.dto.response.PageResponse;
import com.ezmeal.company.domain.event.CompanyEventProducer;
import com.ezmeal.company.domain.event.payload.CompanyCreatedEvent;
import com.ezmeal.company.domain.event.payload.CompanyDeletedEvent;
import com.ezmeal.company.domain.event.payload.CompanyUpdatedEvent;
import com.ezmeal.company.domain.exception.CompanyErrorCode;
import com.ezmeal.company.domain.model.Company;
import com.ezmeal.company.domain.repository.CompanyRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyEventProducer companyEventProducer;

    //인증인가가 완료되면 managerUserId는 request에서 빼고 인증 정보에서 조회예정

    //업체 생성
    @Transactional
    public CompanyResponse createCompany(CompanyCreateRequest companyCreateRequest) {

        boolean exists = companyRepository.existsByNameAndDeletedAtIsNull(companyCreateRequest.name());
        if (exists) {
            throw new CustomException(CompanyErrorCode.COMPANY_ALREADY_EXISTS);
        }

        Company company = new Company(
                companyCreateRequest.managerUserId(),
                companyCreateRequest.name(),
                companyCreateRequest.lotAddress(),
                companyCreateRequest.roadAddress(),
                companyCreateRequest.description()
        );

        Company companySaved = companyRepository.save(company);

        CompanyCreatedEvent event = CompanyCreatedEvent.of(companySaved.getId(), companySaved.getName(),
                companySaved.getLotAddress(), companySaved.getRoadAddress(), companySaved.getDescription());
        companyEventProducer.publishCreatedEvent(event);

        return CompanyResponse.from(companySaved);
    }


    //업체 수정
    @Transactional
    public CompanyResponse updateCompany(UUID companyId, CompanyUpdateRequest companyUpdateRequest) {

        Company company = companyRepository.findByIdAndDeletedAtIsNull(companyId)
                .orElseThrow(() -> new CustomException(CompanyErrorCode.COMPANY_NOT_FOUND));

        company.update(
                companyUpdateRequest.name(),
                companyUpdateRequest.lotAddress(),
                companyUpdateRequest.roadAddress(),
                companyUpdateRequest.description()
        );

        CompanyUpdatedEvent event = CompanyUpdatedEvent.of(company.getId(), company.getName(), company.getLotAddress(),
                company.getRoadAddress(), company.getDescription());
        companyEventProducer.publishUpdatedEvent(event);

        return CompanyResponse.from(company);
    }

    //업체 논리 삭제
    @Transactional
    public void deleteCompany(UUID companyId, String deletedBy) {

        Company company = companyRepository.findByIdAndDeletedAtIsNull(companyId)
                .orElseThrow(() -> new CustomException(CompanyErrorCode.COMPANY_NOT_FOUND));

        company.delete(deletedBy);

        CompanyDeletedEvent event = CompanyDeletedEvent.of(company.getId());
        companyEventProducer.publishDeletedEvent(event);
    }

    //업체 상세 조회
    @Transactional(readOnly = true)
    public CompanyResponse getCompany(UUID companyId) {

        Company company = companyRepository.findByIdAndDeletedAtIsNull(companyId)
                .orElseThrow(() -> new CustomException(CompanyErrorCode.COMPANY_NOT_FOUND));

        return CompanyResponse.from(company);
    }

    //업체 목록 조회
    @Transactional(readOnly = true)
    public PageResponse<CompanyResponse> getCompanies(CompanySearchRequest companySearchRequest, Pageable pageable) {

        Page<Company> companies = companyRepository.searchCompanies(companySearchRequest, pageable);
        Page<CompanyResponse> responses = companies.map(CompanyResponse::from);

        return PageResponse.from(responses);
    }
}
