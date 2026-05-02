package com.ezmeal.company.domain.event.payload;

import com.ezmeal.company.domain.event.CompanyEventType;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanySnapshotUpdatedEvent {

    private UUID eventId;
    private CompanyEventType companyEventType;
    private OffsetDateTime occurredAt;
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
        return new CompanySnapshotUpdatedEvent(UUID.randomUUID(),
                CompanyEventType.COMPANY_SNAPSHOT_UPDATED,
                OffsetDateTime.now(), companyId, managerUserId, companyName, companyLotAddress, companyRoadAddress,
                companyDescription, deliveryAreas);
    }
}
