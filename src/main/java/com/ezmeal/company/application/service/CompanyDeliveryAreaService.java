package com.ezmeal.company.application.service;

import com.ezmeal.common.exception.CustomException;
import com.ezmeal.company.application.dto.request.CompanyDeliveryAreaRequest;
import com.ezmeal.company.application.dto.response.CompanyDeliveryAreaResponse;
import com.ezmeal.company.domain.exception.CompanyErrorCode;
import com.ezmeal.company.domain.model.Company;
import com.ezmeal.company.domain.model.CompanyDeliveryArea;
import com.ezmeal.company.domain.model.DeliveryRegion;
import com.ezmeal.company.domain.repository.CompanyDeliveryAreaRepository;
import com.ezmeal.company.domain.repository.CompanyRepository;
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
    public CompanyDeliveryAreaResponse create(UUID companyId, CompanyDeliveryAreaRequest companyDeliveryAreaRequest) {

        Company company = companyRepository.findByIdAndDeletedAtIsNull(companyId)
                .orElseThrow(() -> new CustomException(CompanyErrorCode.COMPANY_NOT_FOUND));

        boolean exists = companyDeliveryAreaRepository.existsByCompany_IdAndRegionAndEstimatedDeliveryMinutesAndDeletedAtIsNull(
                companyId, companyDeliveryAreaRequest.region(),
                companyDeliveryAreaRequest.estimatedDeliveryMinutes());
        if (exists) {
            throw new CustomException(CompanyErrorCode.COMPANY_DELIVERY_AREA_ALREADY_EXISTS);
        }

        CompanyDeliveryArea companyDeliveryArea = new CompanyDeliveryArea(company,
                companyDeliveryAreaRequest.region(), companyDeliveryAreaRequest.estimatedDeliveryMinutes());

        CompanyDeliveryArea companyDeliveryAreaSaved = companyDeliveryAreaRepository.save(companyDeliveryArea);

        return CompanyDeliveryAreaResponse.from(companyDeliveryAreaSaved);
    }

    @Transactional
    public CompanyDeliveryAreaResponse update(UUID deliveryAreaId, UUID companyId,
                                              CompanyDeliveryAreaRequest companyDeliveryAreaRequest) {

        CompanyDeliveryArea companyDeliveryArea = companyDeliveryAreaRepository.findByIdAndCompany_IdAndDeletedAtIsNull(
                        deliveryAreaId, companyId)
                .orElseThrow(() -> new CustomException(CompanyErrorCode.COMPANY_DELIVERY_AREA_NOT_FOUND));

        DeliveryRegion nextRegion =
                companyDeliveryAreaRequest.region() != null ? companyDeliveryAreaRequest.region()
                        : companyDeliveryArea.getRegion();

        Integer nextEstimatedDeliveryMinutes =
                companyDeliveryAreaRequest.estimatedDeliveryMinutes() != null
                        ? companyDeliveryAreaRequest.estimatedDeliveryMinutes()
                        : companyDeliveryArea.getEstimatedDeliveryMinutes();
        boolean sameValue =
                companyDeliveryArea.getRegion() == nextRegion
                        && companyDeliveryArea.getEstimatedDeliveryMinutes().equals(nextEstimatedDeliveryMinutes);
        if (!sameValue) {
            boolean exists = companyDeliveryAreaRepository.existsByCompany_IdAndRegionAndEstimatedDeliveryMinutesAndDeletedAtIsNull(
                    companyId, nextRegion, nextEstimatedDeliveryMinutes);

            if (exists) {
                throw new CustomException(CompanyErrorCode.COMPANY_DELIVERY_AREA_ALREADY_EXISTS);
            }
        }

        companyDeliveryArea.update(nextRegion, nextEstimatedDeliveryMinutes);

        return CompanyDeliveryAreaResponse.from(companyDeliveryArea);
    }
}
