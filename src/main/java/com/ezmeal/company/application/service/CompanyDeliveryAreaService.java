package com.ezmeal.company.application.service;

import com.ezmeal.common.exception.CustomException;
import com.ezmeal.company.application.dto.request.CompanyDeliveryAreaCreateRequest;
import com.ezmeal.company.application.dto.request.CompanyDeliveryAreaUpdateRequest;
import com.ezmeal.company.application.dto.response.CompanyDeliveryAreaResponse;
import com.ezmeal.company.domain.exception.CompanyErrorCode;
import com.ezmeal.company.domain.model.Company;
import com.ezmeal.company.domain.model.CompanyDeliveryArea;
import com.ezmeal.company.domain.model.DeliveryRegion;
import com.ezmeal.company.domain.repository.CompanyDeliveryAreaRepository;
import com.ezmeal.company.domain.repository.CompanyRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyDeliveryAreaService {

    private final CompanyDeliveryAreaRepository companyDeliveryAreaRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public CompanyDeliveryAreaResponse create(UUID companyId,
                                              CompanyDeliveryAreaCreateRequest companyDeliveryAreaCreateRequest) {

        Company company = companyRepository.findByIdAndDeletedAtIsNull(companyId)
                .orElseThrow(() -> new CustomException(CompanyErrorCode.COMPANY_NOT_FOUND));

        boolean exists = companyDeliveryAreaRepository.existsByCompany_IdAndRegionAndDeletedAtIsNull(companyId,
                companyDeliveryAreaCreateRequest.region());
        if (exists) {
            throw new CustomException(CompanyErrorCode.COMPANY_DELIVERY_AREA_ALREADY_EXISTS);
        }

        CompanyDeliveryArea companyDeliveryArea = new CompanyDeliveryArea(company,
                companyDeliveryAreaCreateRequest.region(), companyDeliveryAreaCreateRequest.estimatedDeliveryMinutes());

        CompanyDeliveryArea companyDeliveryAreaSaved = companyDeliveryAreaRepository.save(companyDeliveryArea);

        return CompanyDeliveryAreaResponse.from(companyDeliveryAreaSaved);
    }

    @Transactional
    public CompanyDeliveryAreaResponse update(UUID deliveryAreaId, UUID companyId,
                                              CompanyDeliveryAreaUpdateRequest companyDeliveryAreaUpdateRequest) {

        CompanyDeliveryArea companyDeliveryArea = companyDeliveryAreaRepository.findByIdAndCompany_IdAndDeletedAtIsNull(
                        deliveryAreaId, companyId)
                .orElseThrow(() -> new CustomException(CompanyErrorCode.COMPANY_DELIVERY_AREA_NOT_FOUND));

        DeliveryRegion nextRegion =
                companyDeliveryAreaUpdateRequest.region() != null ? companyDeliveryAreaUpdateRequest.region()
                        : companyDeliveryArea.getRegion();

        Integer nextEstimatedDeliveryMinutes = companyDeliveryAreaUpdateRequest.estimatedDeliveryMinutes() != null
                ? companyDeliveryAreaUpdateRequest.estimatedDeliveryMinutes()
                : companyDeliveryArea.getEstimatedDeliveryMinutes();

        boolean sameRegion = companyDeliveryArea.getRegion() == nextRegion;

        if (!sameRegion) {
            boolean exists = companyDeliveryAreaRepository.existsByCompany_IdAndRegionAndDeletedAtIsNull(companyId,
                    nextRegion);

            if (exists) {
                throw new CustomException(CompanyErrorCode.COMPANY_DELIVERY_AREA_ALREADY_EXISTS);
            }
        }

        companyDeliveryArea.update(nextRegion, nextEstimatedDeliveryMinutes);

        return CompanyDeliveryAreaResponse.from(companyDeliveryArea);
    }

    @Transactional
    public void delete(UUID deliveryAreaId, UUID companyId, String deletedBy) {

        CompanyDeliveryArea companyDeliveryArea = companyDeliveryAreaRepository.findByIdAndCompany_IdAndDeletedAtIsNull(
                        deliveryAreaId, companyId)
                .orElseThrow(() -> new CustomException(CompanyErrorCode.COMPANY_DELIVERY_AREA_NOT_FOUND));

        companyDeliveryArea.delete(deletedBy);
    }

    @Transactional(readOnly = true)
    public List<CompanyDeliveryAreaResponse> getCompanyDeliveryAreas(UUID companyId) {
        companyRepository.findByIdAndDeletedAtIsNull(companyId)
                .orElseThrow(() -> new CustomException(CompanyErrorCode.COMPANY_NOT_FOUND));

        return companyDeliveryAreaRepository.findAllByCompany_IdAndDeletedAtIsNull(companyId).stream()
                .map(CompanyDeliveryAreaResponse::from).toList();
    }
}
