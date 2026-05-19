package com.ezmeal.company.presentation.controller;

import com.ezmeal.common.response.CommonApiResponse;
import com.ezmeal.common.security.principal.CustomUserPrincipal;
import com.ezmeal.company.application.dto.request.CompanyCreateRequest;
import com.ezmeal.company.application.dto.request.CompanyDeliveryAreaCreateRequest;
import com.ezmeal.company.application.dto.request.CompanyDeliveryAreaUpdateRequest;
import com.ezmeal.company.application.dto.request.CompanySearchRequest;
import com.ezmeal.company.application.dto.request.CompanyUpdateRequest;
import com.ezmeal.company.application.dto.response.CompanyDeliveryAreaResponse;
import com.ezmeal.company.application.dto.response.CompanyResponse;
import com.ezmeal.company.application.dto.response.PageResponse;
import com.ezmeal.company.application.service.CompanyDeliveryAreaService;
import com.ezmeal.company.application.service.CompanyService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyDeliveryAreaService companyDeliveryAreaService;

    //업체 생성 요청
    @PostMapping
    public ResponseEntity<CommonApiResponse<CompanyResponse>> createCompany(
            @RequestBody CompanyCreateRequest companyCreateRequest,
            Authentication authentication
    ) {
        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();

        CompanyResponse response = companyService.createCompany(companyCreateRequest, principal.getUserId(),
                principal.getRole());

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonApiResponse.success("업체가 생성되었습니다.", response));
    }

    //업체 상세조회 요청
    @GetMapping("/{companyId}")
    public ResponseEntity<CommonApiResponse<CompanyResponse>> getCompany(
            @PathVariable UUID companyId
    ) {
        CompanyResponse response = companyService.getCompany(companyId);

        return ResponseEntity.ok(CommonApiResponse.success("업체가 상세조회 되었습니다.", response));
    }

    //업체 목록조회 요청
    @GetMapping
    public ResponseEntity<CommonApiResponse<PageResponse<CompanyResponse>>> getCompanies(
            @RequestParam(required = false) String name,
            Pageable pageable
    ) {
        CompanySearchRequest companySearchRequest = new CompanySearchRequest(name);
        PageResponse<CompanyResponse> response = companyService.getCompanies(companySearchRequest, pageable);

        return ResponseEntity.ok(CommonApiResponse.success("업체가 목록조회 되었습니다.", response));
    }

    //업체 수정 요청
    @PatchMapping("/{companyId}")
    public ResponseEntity<CommonApiResponse<CompanyResponse>> updateCompany(
            @PathVariable UUID companyId,
            @RequestBody CompanyUpdateRequest companyUpdateRequest,
            Authentication authentication
    ) {
        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();

        CompanyResponse response = companyService.updateCompany(companyId, companyUpdateRequest, principal.getUserId(),
                principal.getRole());

        return ResponseEntity.ok(CommonApiResponse.success("업체가 수정 되었습니다.", response));
    }

    //업체 삭제 요청
    @DeleteMapping("/{companyId}")
    public ResponseEntity<CommonApiResponse<Void>> deleteCompany(
            @PathVariable UUID companyId,
            Authentication authentication
    ) {
        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();

        companyService.deleteCompany(companyId, principal.getUserId(), principal.getRole());

        return ResponseEntity.ok(CommonApiResponse.success());
    }

    //배송지역 생성
    @PostMapping("/{companyId}/delivery-areas")
    public ResponseEntity<CommonApiResponse<CompanyDeliveryAreaResponse>> createCompanyDeliveryArea(
            @PathVariable UUID companyId,
            @RequestBody CompanyDeliveryAreaCreateRequest companyDeliveryAreaCreateRequest,
            Authentication authentication
    ) {
        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();

        CompanyDeliveryAreaResponse response = companyDeliveryAreaService.create(companyId,
                companyDeliveryAreaCreateRequest, principal.getUserId(), principal.getRole());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonApiResponse.success("배달 가능 지역이 생성되었습니다.", response));
    }

    //배송지역 수정
    @PatchMapping("/{companyId}/delivery-areas/{deliveryAreaId}")
    public ResponseEntity<CommonApiResponse<CompanyDeliveryAreaResponse>> updateDeliveryArea(
            @PathVariable UUID companyId,
            @PathVariable UUID deliveryAreaId,
            @RequestBody CompanyDeliveryAreaUpdateRequest request,
            Authentication authentication
    ) {
        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();

        CompanyDeliveryAreaResponse response =
                companyDeliveryAreaService.update(deliveryAreaId, companyId, request, principal.getUserId(),
                        principal.getRole());

        return ResponseEntity.ok(
                CommonApiResponse.success("배달 가능 지역이 수정되었습니다.", response)
        );
    }


    //배송지역 조회
    @GetMapping("/{companyId}/delivery-areas")
    public ResponseEntity<CommonApiResponse<List<CompanyDeliveryAreaResponse>>> getCompanyDeliveryAreas(
            @PathVariable UUID companyId
    ) {
        List<CompanyDeliveryAreaResponse> response =
                companyDeliveryAreaService.getCompanyDeliveryAreas(companyId);

        return ResponseEntity.ok(
                CommonApiResponse.success("배달 가능 지역 목록이 조회되었습니다.", response)
        );
    }


    // 배송지역 삭제
    @DeleteMapping("/{companyId}/delivery-areas/{deliveryAreaId}")
    public ResponseEntity<CommonApiResponse<Void>> deleteCompanyDeliveryArea(
            @PathVariable UUID companyId,
            @PathVariable UUID deliveryAreaId,
            Authentication authentication
    ) {
        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();

        companyDeliveryAreaService.delete(deliveryAreaId, companyId, principal.getUserId(), principal.getRole());

        return ResponseEntity.ok(CommonApiResponse.success());
    }
}
