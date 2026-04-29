package com.ezmeal.company.domain.repository;

import com.ezmeal.company.domain.model.CompanyDeliveryArea;
import com.ezmeal.company.domain.model.DeliveryRegion;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CompanyDeliveryAreaRepository {

    CompanyDeliveryArea save(CompanyDeliveryArea companyDeliveryArea);

    Optional<CompanyDeliveryArea> findByIdAndCompany_IdAndDeletedAtIsNull(
            UUID deliveryAreaId,
            UUID companyId
    );

    boolean existsByCompany_IdAndRegionAndDeletedAtIsNull(
            UUID companyId,
            DeliveryRegion region
    );

    List<CompanyDeliveryArea> findAllByCompany_IdAndDeletedAtIsNull(UUID companyId);
}


