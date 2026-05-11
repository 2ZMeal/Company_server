package com.ezmeal.company.domain.event.payload;

import com.ezmeal.common.message.DomainEvent;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanyDeletedEvent implements DomainEvent {

    private UUID companyId;

    public static CompanyDeletedEvent of(UUID companyId
    ) {
        return new CompanyDeletedEvent(companyId);
    }
}
