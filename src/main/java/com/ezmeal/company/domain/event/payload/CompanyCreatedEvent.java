package com.ezmeal.company.domain.event.payload;

import com.ezmeal.company.domain.event.EventType;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanyCreatedEvent {

    private UUID eventId;
    private EventType eventType;
    private OffsetDateTime occurredAt;
    private UUID companyId;
    private String companyName;
    private String companyLotAddress;
    private String companyRoadAddress;
    private String companyDescription;

    public static CompanyCreatedEvent of(UUID companyId, String companyName, String companyLotAddress,
                                         String companyRoadAddress, String companyDescription
    ) {
        return new CompanyCreatedEvent(UUID.randomUUID(),
                EventType.COMPANY_CREATED,
                OffsetDateTime.now(), companyId, companyName, companyLotAddress, companyRoadAddress,
                companyDescription);
    }

}
