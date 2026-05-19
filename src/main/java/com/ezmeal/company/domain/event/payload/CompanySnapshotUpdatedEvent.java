package com.ezmeal.company.domain.event.payload;

import com.ezmeal.common.message.DomainEvent;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanySnapshotUpdatedEvent implements DomainEvent {

    private UUID companyId;
    private UUID managerUserId;
    private String companyName;
    private String companyLotAddress;
    private String companyRoadAddress;
    private String companyDescription;
    private List<CompanyDeliveryAreaEventPayload> deliveryAreas;

    public static CompanySnapshotUpdatedEvent of(UUID companyId, UUID managerUserId, String companyName,
                                                 String companyLotAddress,
                                                 String companyRoadAddress, String companyDescription,
                                                 List<CompanyDeliveryAreaEventPayload> deliveryAreas
    ) {
        return new CompanySnapshotUpdatedEvent(
                companyId, managerUserId, companyName, companyLotAddress, companyRoadAddress,
                companyDescription, deliveryAreas
        );
    }
}
