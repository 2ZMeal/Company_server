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
public class CompanyDeletedEvent {

    private UUID eventId;
    private EventType eventType;
    private OffsetDateTime occurredAt;
    private UUID companyId;


    public static CompanyDeletedEvent of(UUID companyId
    ) {
        return new CompanyDeletedEvent(UUID.randomUUID(),
                EventType.COMPANY_DELETED,
                OffsetDateTime.now(), companyId);
    }
}
