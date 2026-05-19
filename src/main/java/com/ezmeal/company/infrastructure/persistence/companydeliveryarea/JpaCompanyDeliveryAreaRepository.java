package com.ezmeal.company.infrastructure.persistence.companydeliveryarea;

import com.ezmeal.company.domain.model.CompanyDeliveryArea;
import com.ezmeal.company.domain.model.CompanyMealPeriod;
import com.ezmeal.company.domain.model.DeliveryRegion;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCompanyDeliveryAreaRepository extends JpaRepository<CompanyDeliveryArea, UUID> {

    Optional<CompanyDeliveryArea> findByIdAndCompany_IdAndDeletedAtIsNull(
            UUID deliveryAreaId,
            UUID companyId
    );

    boolean existsByCompany_IdAndRegionAndMealPeriodAndDeletedAtIsNull(
            UUID companyId,
            DeliveryRegion region,
            CompanyMealPeriod mealPeriod
    );

    List<CompanyDeliveryArea> findAllByCompany_IdAndDeletedAtIsNull(UUID companyId);
}
