package com.ezmeal.company.presentation.controller;

import com.ezmeal.common.response.CommonApiResponse;
import com.ezmeal.company.application.dto.response.CompanyInfo;
import com.ezmeal.company.application.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/companies")
public class CompanyInternalController {

    private final CompanyService companyService;

    @GetMapping("/by-company")
    public ResponseEntity<CommonApiResponse<CompanyInfo>> getCompanyByManager(
            @RequestParam("managerUserId") String managerUserId
    ) {
        return ResponseEntity.ok(CommonApiResponse.success(
                companyService.getCompanyInfoByManagerUserId(managerUserId)
        ));
    }
}
