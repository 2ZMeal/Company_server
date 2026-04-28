package com.ezmeal.company.domain.event;

import com.ezmeal.company.domain.event.payload.CompanyCreatedEvent;
import com.ezmeal.company.domain.event.payload.CompanyDeletedEvent;
import com.ezmeal.company.domain.event.payload.CompanyUpdatedEvent;

public interface CompanyEventProducer {
    void publishCreatedEvent(CompanyCreatedEvent event);
    void publishUpdatedEvent(CompanyUpdatedEvent event);
    void publishDeletedEvent(CompanyDeletedEvent event);
}
