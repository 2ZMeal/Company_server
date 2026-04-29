package com.ezmeal.company.infrastructure.persistence.companydeliveryarea;

import com.ezmeal.company.domain.model.CompanyDeliveryArea;
import com.ezmeal.company.domain.model.DeliveryRegion;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCompanyDeliveryAreaRepository extends JpaRepository<CompanyDeliveryArea, UUID> {

    Optional<CompanyDeliveryArea> findByIdAndCompany_IdAndDeletedAtIsNull(
            UUID deliveryAreaId,
            UUID companyId
    );

    boolean existsByCompany_IdAndRegionAndEstimatedDeliveryMinutesAndDeletedAtIsNull(
            UUID companyId,
            DeliveryRegion region,
            Integer estimatedDeliveryMinutes
    );
}
