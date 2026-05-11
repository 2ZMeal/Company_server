package com.ezmeal.company.infrastructure.message.kafka.producer;

import com.ezmeal.common.message.CommonKafkaEventPublisher;
import com.ezmeal.company.domain.event.CompanyEventProducer;
import com.ezmeal.company.domain.event.CompanyEventType;
import com.ezmeal.company.domain.event.payload.CompanyCreatedEvent;
import com.ezmeal.company.domain.event.payload.CompanyDeletedEvent;
import com.ezmeal.company.domain.event.payload.CompanySnapshotUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class CompanyEventProducerImpl implements CompanyEventProducer {

    private final CommonKafkaEventPublisher commonKafkaEventPublisher;

    @Override
    public void publishCreatedEvent(CompanyCreatedEvent event) {
        commonKafkaEventPublisher.publish(
                "company.created",
                event.getCompanyId().toString(),
                CompanyEventType.COMPANY_CREATED.name(),
                event
        );
    }

    @Override
    public void publishSnapshotUpdatedEvent(CompanySnapshotUpdatedEvent event) {
        commonKafkaEventPublisher.publish(
                "company.snapshot.updated",
                event.getCompanyId().toString(),
                CompanyEventType.COMPANY_SNAPSHOT_UPDATED.name(),
                event
        );
    }

    @Override
    public void publishDeletedEvent(CompanyDeletedEvent event) {
        commonKafkaEventPublisher.publish(
                "company.deleted",
                event.getCompanyId().toString(),
                CompanyEventType.COMPANY_DELETED.name(),
                event
        );
    }
}
