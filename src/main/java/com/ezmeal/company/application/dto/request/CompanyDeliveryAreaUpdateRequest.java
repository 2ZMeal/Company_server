package com.ezmeal.company.application.dto.request;

import com.ezmeal.company.domain.model.DeliveryRegion;

public record CompanyDeliveryAreaUpdateRequest(
        DeliveryRegion region,
        Integer estimatedDeliveryMinutes
) {
}
