package com.ezmeal.company.domain.model;

import com.ezmeal.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_company_delivery_area",
        schema = "company_service",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_company_delivery_area_region",
                        columnNames = {"company_id", "delivery_region"}
                )
        })
public class CompanyDeliveryArea extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "company_delivery_area_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_region", nullable = false)
    private DeliveryRegion region;

    @Column(name = "estimated_delivery_minutes", nullable = false)
    private Integer estimatedDeliveryMinutes;

    public CompanyDeliveryArea(Company company, DeliveryRegion region, Integer estimatedDeliveryMinutes) {
        validation(company, region, estimatedDeliveryMinutes);

        this.company = company;
        this.region = region;
        this.estimatedDeliveryMinutes = estimatedDeliveryMinutes;
    }

    public void update(DeliveryRegion region, Integer estimatedDeliveryMinutes) {
        DeliveryRegion nextRegion = region != null ? region : this.region;
        Integer nextEstimatedDeliveryMinutes =
                estimatedDeliveryMinutes != null ? estimatedDeliveryMinutes : this.estimatedDeliveryMinutes;

        validation(this.company, nextRegion, nextEstimatedDeliveryMinutes);

        this.region = nextRegion;
        this.estimatedDeliveryMinutes = nextEstimatedDeliveryMinutes;
    }

    private void validation(Company company, DeliveryRegion region, Integer estimatedDeliveryMinutes) {
        if (company == null) {
            throw new IllegalArgumentException("업체는 존재해야 합니다.");
        }
        if (region == null) {
            throw new IllegalArgumentException("지역은 존재해야 합니다.");
        }
        if (estimatedDeliveryMinutes == null) {
            throw new IllegalArgumentException("배달 예상 시간은 존재해야 합니다.");
        }
        if (estimatedDeliveryMinutes <= 0) {
            throw new IllegalArgumentException("배달 예상 시간은 1분 이상이어야 합니다.");
        }
    }
}
