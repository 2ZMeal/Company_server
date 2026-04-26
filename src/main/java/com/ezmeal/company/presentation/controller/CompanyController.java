package com.ezmeal.company.presentation.controller;

import com.ezmeal.company.application.dto.request.CompanyCreateRequest;
import com.ezmeal.company.application.dto.request.CompanySearchRequest;
import com.ezmeal.company.application.dto.request.CompanyUpdateRequest;
import com.ezmeal.company.application.dto.response.CompanyResponse;
import com.ezmeal.company.application.dto.response.PageResponse;
import com.ezmeal.company.application.service.CompanyService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 공통 응답 구조가 Common에 구현되면 CompanyResponse를 공통 응답 구조로 감쌀 예정
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;

    //업체 생성 요청
    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(
            @RequestBody CompanyCreateRequest companyCreateRequest
    ) {
        CompanyResponse response = companyService.createCompany(companyCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //업체 상세조회 요청
    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> getCompany(
            @PathVariable UUID companyId
    ) {
        CompanyResponse response = companyService.getCompany(companyId);

        return ResponseEntity.ok(response);
    }

    //업체 목록조회 요청
    @GetMapping
    public ResponseEntity<PageResponse<CompanyResponse>> getCompanies(
            @RequestParam(required = false) String name,
            Pageable pageable
    ) {
        CompanySearchRequest companySearchRequest = new CompanySearchRequest(name);
        PageResponse<CompanyResponse> response = companyService.getCompanies(companySearchRequest, pageable);

        return ResponseEntity.ok(response);
    }

    //업체 수정 요청
    @PatchMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> updateCompany(
            @PathVariable UUID companyId,
            @RequestBody CompanyUpdateRequest companyUpdateRequest
    ) {
        CompanyResponse response = companyService.updateCompany(companyId, companyUpdateRequest);

        return ResponseEntity.ok(response);
    }

    //업체 삭제 요청
    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteCompany(
            @PathVariable UUID companyId
    ) {
        companyService.deleteCompany(companyId);

        return ResponseEntity.noContent().build();
    }
}
