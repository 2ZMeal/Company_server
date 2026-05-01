package com.ezmeal.company.application.service;

import com.ezmeal.common.exception.CustomException;
import com.ezmeal.company.application.dto.request.CompanyDeliveryAreaCreateRequest;
import com.ezmeal.company.application.dto.request.CompanyDeliveryAreaUpdateRequest;
import com.ezmeal.company.application.dto.response.CompanyDeliveryAreaResponse;
import com.ezmeal.company.domain.event.payload.CompanyDeliveryAreaEventPayload;
import com.ezmeal.company.domain.event.payload.CompanySnapshotUpdatedEvent;
import com.ezmeal.company.domain.exception.CompanyErrorCode;
import com.ezmeal.company.domain.model.Company;
import com.ezmeal.company.domain.model.CompanyDeliveryArea;
import com.ezmeal.company.domain.model.CompanyMealPeriod;
import com.ezmeal.company.domain.model.DeliveryRegion;
import com.ezmeal.company.domain.repository.CompanyDeliveryAreaRepository;
import com.ezmeal.company.domain.repository.CompanyRepository;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyDeliveryAreaService {

    private final CompanyDeliveryAreaRepository companyDeliveryAreaRepository;
    private final CompanyRepository companyRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public CompanyDeliveryAreaResponse create(UUID companyId,
                                              CompanyDeliveryAreaCreateRequest companyDeliveryAreaCreateRequest) {

        Company company = companyRepository.findByIdAndDeletedAtIsNull(companyId)
                .orElseThrow(() -> new CustomException(CompanyErrorCode.COMPANY_NOT_FOUND));

        boolean exists = companyDeliveryAreaRepository.existsByCompany_IdAndRegionAndMealPeriodAndDeletedAtIsNull(
                companyId,
                companyDeliveryAreaCreateRequest.region(), companyDeliveryAreaCreateRequest.mealPeriod());
        if (exists) {
            throw new CustomException(CompanyErrorCode.COMPANY_DELIVERY_AREA_ALREADY_EXISTS);
        }

        CompanyDeliveryArea companyDeliveryArea = new CompanyDeliveryArea(company,
                companyDeliveryAreaCreateRequest.region(), companyDeliveryAreaCreateRequest.mealPeriod(),
                companyDeliveryAreaCreateRequest.estimatedArrivalStartTime(),
                companyDeliveryAreaCreateRequest.estimatedArrivalEndTime());

        CompanyDeliveryArea companyDeliveryAreaSaved = companyDeliveryAreaRepository.save(companyDeliveryArea);

        publishCompanySnapshotUpdatedEvent(companyId);

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

        CompanyMealPeriod nextMealPeriod =
                companyDeliveryAreaUpdateRequest.mealPeriod() != null
                        ? companyDeliveryAreaUpdateRequest.mealPeriod()
                        : companyDeliveryArea.getMealPeriod();

        LocalTime nextEstimatedArrivalStartTime =
                companyDeliveryAreaUpdateRequest.estimatedArrivalStartTime() != null
                        ? companyDeliveryAreaUpdateRequest.estimatedArrivalStartTime()
                        : companyDeliveryArea.getEstimatedArrivalStartTime();

        LocalTime nextEstimatedArrivalEndTime =
                companyDeliveryAreaUpdateRequest.estimatedArrivalEndTime() != null
                        ? companyDeliveryAreaUpdateRequest.estimatedArrivalEndTime()
                        : companyDeliveryArea.getEstimatedArrivalEndTime();

        boolean sameDeliveryOption =
                companyDeliveryArea.getRegion() == nextRegion
                        && companyDeliveryArea.getMealPeriod() == nextMealPeriod;

        if (!sameDeliveryOption) {
            boolean exists = companyDeliveryAreaRepository.existsByCompany_IdAndRegionAndMealPeriodAndDeletedAtIsNull(
                    companyId,
                    nextRegion,
                    nextMealPeriod
            );

            if (exists) {
                throw new CustomException(CompanyErrorCode.COMPANY_DELIVERY_AREA_ALREADY_EXISTS);
            }
        }

        companyDeliveryArea.update(nextRegion,
                nextMealPeriod,
                nextEstimatedArrivalStartTime,
                nextEstimatedArrivalEndTime);

        publishCompanySnapshotUpdatedEvent(companyId);

        return CompanyDeliveryAreaResponse.from(companyDeliveryArea);
    }

    @Transactional
    public void delete(UUID deliveryAreaId, UUID companyId, String deletedBy) {

        CompanyDeliveryArea companyDeliveryArea = companyDeliveryAreaRepository.findByIdAndCompany_IdAndDeletedAtIsNull(
                        deliveryAreaId, companyId)
                .orElseThrow(() -> new CustomException(CompanyErrorCode.COMPANY_DELIVERY_AREA_NOT_FOUND));

        companyDeliveryArea.delete(deletedBy);

        publishCompanySnapshotUpdatedEvent(companyId);
    }

    @Transactional(readOnly = true)
    public List<CompanyDeliveryAreaResponse> getCompanyDeliveryAreas(UUID companyId) {
        companyRepository.findByIdAndDeletedAtIsNull(companyId)
                .orElseThrow(() -> new CustomException(CompanyErrorCode.COMPANY_NOT_FOUND));

        return companyDeliveryAreaRepository.findAllByCompany_IdAndDeletedAtIsNull(companyId).stream()
                .map(CompanyDeliveryAreaResponse::from).toList();
    }

    private void publishCompanySnapshotUpdatedEvent(UUID companyId) {
        Company company = companyRepository.findByIdAndDeletedAtIsNull(companyId)
                .orElseThrow(() -> new CustomException(CompanyErrorCode.COMPANY_NOT_FOUND));

        List<CompanyDeliveryAreaEventPayload> deliveryAreas =
                companyDeliveryAreaRepository.findAllByCompany_IdAndDeletedAtIsNull(companyId).stream()
                        .map(CompanyDeliveryAreaEventPayload::from)
                        .toList();

        eventPublisher.publishEvent(CompanySnapshotUpdatedEvent.of(
                company.getId(),
                company.getName(),
                company.getLotAddress(),
                company.getRoadAddress(),
                company.getDescription(),
                deliveryAreas));
    }
}
