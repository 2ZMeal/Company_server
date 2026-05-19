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
import java.time.LocalTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_company_delivery_area")
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

    @Enumerated(EnumType.STRING)
    @Column(name = "meal_period", nullable = false)
    private CompanyMealPeriod mealPeriod;

    @Column(name = "estimated_arrival_start_time", nullable = false)
    private LocalTime estimatedArrivalStartTime;

    @Column(name = "estimated_arrival_end_time", nullable = false)
    private LocalTime estimatedArrivalEndTime;

    public CompanyDeliveryArea(Company company, DeliveryRegion region, CompanyMealPeriod mealPeriod,
                               LocalTime estimatedArrivalStartTime, LocalTime estimatedArrivalEndTime) {
        validation(company, region, mealPeriod, estimatedArrivalStartTime, estimatedArrivalEndTime);

        this.company = company;
        this.region = region;
        this.mealPeriod = mealPeriod;
        this.estimatedArrivalStartTime = estimatedArrivalStartTime;
        this.estimatedArrivalEndTime = estimatedArrivalEndTime;
    }

    public void update(DeliveryRegion region, CompanyMealPeriod mealPeriod,
                       LocalTime estimatedArrivalStartTime, LocalTime estimatedArrivalEndTime) {
        DeliveryRegion nextRegion = region != null ? region : this.region;
        CompanyMealPeriod nextMealPeriod = mealPeriod != null ? mealPeriod : this.mealPeriod;
        LocalTime nextEstimatedArrivalStartTime =
                estimatedArrivalStartTime != null ? estimatedArrivalStartTime : this.estimatedArrivalStartTime;
        LocalTime nextEstimatedArrivalEndTime =
                estimatedArrivalEndTime != null ? estimatedArrivalEndTime : this.estimatedArrivalEndTime;

        validation(this.company, nextRegion, nextMealPeriod, nextEstimatedArrivalStartTime,
                nextEstimatedArrivalEndTime);

        this.region = nextRegion;
        this.mealPeriod = nextMealPeriod;
        this.estimatedArrivalStartTime = nextEstimatedArrivalStartTime;
        this.estimatedArrivalEndTime = nextEstimatedArrivalEndTime;

    }

    private void validation(Company company, DeliveryRegion region, CompanyMealPeriod mealPeriod,
                            LocalTime estimatedArrivalStartTime, LocalTime estimatedArrivalEndTime) {
        if (company == null) {
            throw new IllegalArgumentException("업체는 필수입니다.");
        }
        if (region == null) {
            throw new IllegalArgumentException("배송 지역은 필수입니다.");
        }
        if (mealPeriod == null) {
            throw new IllegalArgumentException("식사 시간대는 필수입니다.");
        }
        if (estimatedArrivalStartTime == null) {
            throw new IllegalArgumentException("도착 예정 시작 시간은 필수입니다.");
        }
        if (estimatedArrivalEndTime == null) {
            throw new IllegalArgumentException("도착 예정 종료 시간은 필수입니다.");
        }
        if (!estimatedArrivalStartTime.isBefore(estimatedArrivalEndTime)) {
            throw new IllegalArgumentException("도착 예정 시작 시간은 종료 시간보다 빨라야 합니다.");
        }
    }
}
