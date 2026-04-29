package com.ezmeal.company.infrastructure.persistence.companydeliveryarea;

import com.ezmeal.company.domain.model.CompanyDeliveryArea;
import com.ezmeal.company.domain.model.DeliveryRegion;
import com.ezmeal.company.domain.repository.CompanyDeliveryAreaRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompanyDeliveryAreaRepositoryImpl implements CompanyDeliveryAreaRepository {

    private final JpaCompanyDeliveryAreaRepository jpaCompanyDeliveryAreaRepository;

    @Override
    public CompanyDeliveryArea save(CompanyDeliveryArea companyDeliveryArea) {
        return jpaCompanyDeliveryAreaRepository.save(companyDeliveryArea);
    }

    @Override
    public Optional<CompanyDeliveryArea> findByIdAndCompany_IdAndDeletedAtIsNull(UUID deliveryAreaId, UUID companyId) {
        return jpaCompanyDeliveryAreaRepository.findByIdAndCompany_IdAndDeletedAtIsNull(deliveryAreaId, companyId);
    }

    @Override
    public boolean existsByCompany_IdAndRegionAndEstimatedDeliveryMinutesAndDeletedAtIsNull(UUID companyId,
                                                                                            DeliveryRegion region,
                                                                                            Integer estimatedDeliveryMinutes) {
        return jpaCompanyDeliveryAreaRepository.existsByCompany_IdAndRegionAndEstimatedDeliveryMinutesAndDeletedAtIsNull(
                companyId, region, estimatedDeliveryMinutes);
    }


}
