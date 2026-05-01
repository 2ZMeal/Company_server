package com.ezmeal.company.domain.event.payload;

import com.ezmeal.company.domain.model.CompanyDeliveryArea;
import com.ezmeal.company.domain.model.CompanyMealPeriod;
import com.ezmeal.company.domain.model.DeliveryRegion;
import java.time.LocalTime;
import java.util.UUID;

public record CompanyDeliveryAreaEventPayload(
        UUID deliveryAreaId,
        DeliveryRegion region,
        CompanyMealPeriod mealPeriod,
        LocalTime estimatedArrivalStartTime,
        LocalTime estimatedArrivalEndTime
) {
    public static CompanyDeliveryAreaEventPayload from(CompanyDeliveryArea deliveryArea) {
        return new CompanyDeliveryAreaEventPayload(deliveryArea.getId(), deliveryArea.getRegion(),
                deliveryArea.getMealPeriod(),
                deliveryArea.getEstimatedArrivalStartTime(), deliveryArea.getEstimatedArrivalEndTime());
    }
}
