package com.ezmeal.company.application.dto.request;

import com.ezmeal.company.domain.model.CompanyMealPeriod;
import com.ezmeal.company.domain.model.DeliveryRegion;
import java.time.LocalTime;

public record CompanyDeliveryAreaCreateRequest(
        DeliveryRegion region,
        CompanyMealPeriod mealPeriod,
        LocalTime estimatedArrivalStartTime,
        LocalTime estimatedArrivalEndTime

) {
}
