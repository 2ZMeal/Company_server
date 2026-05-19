package com.ezmeal.company.domain.event;

import com.ezmeal.company.domain.event.payload.CompanyCreatedEvent;
import com.ezmeal.company.domain.event.payload.CompanyDeletedEvent;
import com.ezmeal.company.domain.event.payload.CompanySnapshotUpdatedEvent;

public interface CompanyEventProducer {
    void publishCreatedEvent(CompanyCreatedEvent event);
    void publishSnapshotUpdatedEvent(CompanySnapshotUpdatedEvent event);
    void publishDeletedEvent(CompanyDeletedEvent event);
}
