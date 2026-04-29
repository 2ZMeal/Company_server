package com.ezmeal.company.application.event;

import com.ezmeal.company.domain.event.CompanyEventProducer;
import com.ezmeal.company.domain.event.payload.CompanyCreatedEvent;
import com.ezmeal.company.domain.event.payload.CompanyDeletedEvent;
import com.ezmeal.company.domain.event.payload.CompanyUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class CompanyKafkaListener {

    private final CompanyEventProducer companyEventProducer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCompanyCreatedEvent(CompanyCreatedEvent event) {
        companyEventProducer.publishCreatedEvent(event);
    }


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCompanyUpdatedEvent(CompanyUpdatedEvent event) {
        companyEventProducer.publishUpdatedEvent(event);
    }


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCompanyDeletedEvent(CompanyDeletedEvent event) {
        companyEventProducer.publishDeletedEvent(event);
    }

}
