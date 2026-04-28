package com.ezmeal.company.domain.event.payload;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanyCreatedEvent {

    private UUID companyId;
    private String companyName;
    private String companyLotAddress;
    private String companyRoadAddress;
    private String companyDescription;

    public static CompanyCreatedEvent of(UUID companyId, String companyName, String companyLotAddress,
                                         String companyRoadAddress, String companyDescription
    ) {
        return new CompanyCreatedEvent(companyId, companyName, companyLotAddress, companyRoadAddress,
                companyDescription);
    }

}
