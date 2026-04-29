package com.ezmeal.company.application.dto.response;

import com.ezmeal.company.domain.model.CompanyDeliveryArea;
import com.ezmeal.company.domain.model.CompanyMealPeriod;
import com.ezmeal.company.domain.model.DeliveryRegion;
import java.time.LocalTime;
import java.util.UUID;

public record CompanyDeliveryAreaResponse(
        UUID companyDeliveryAreaId,
        DeliveryRegion region,
        CompanyMealPeriod mealPeriod,
        LocalTime estimatedArrivalStartTime,
        LocalTime estimatedArrivalEndTime
) {
    public static CompanyDeliveryAreaResponse from(CompanyDeliveryArea deliveryArea) {
        return new CompanyDeliveryAreaResponse(
                deliveryArea.getId(),
                deliveryArea.getRegion(),
                deliveryArea.getMealPeriod(),
                deliveryArea.getEstimatedArrivalStartTime(),
                deliveryArea.getEstimatedArrivalEndTime()

        );
    }
}
