package com.ezmeal.company.domain.model;

import com.ezmeal.common.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_company", schema = "company_service")
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "company_id")
    private UUID id;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyDeliveryArea> deliveryAreas = new ArrayList<>();

    @Column(name = "manager_user_id", nullable = false)
    private UUID managerUserId;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "lot_address", length = 255)
    private String lotAddress;

    @Column(name = "road_address", length = 255)
    private String roadAddress;

    @Column(name = "description", length = 255)
    private String description;

    //업체 생성 정보
    public Company(UUID managerUserId, String name, String lotAddress, String roadAddress,
                   String description) {
        validate(managerUserId, name, lotAddress, roadAddress);
        this.managerUserId = managerUserId;
        this.name = name;
        this.lotAddress = lotAddress;
        this.roadAddress = roadAddress;
        this.description = description;
    }

    //업체 수정 정보
    public void update(String name, String lotAddress, String roadAddress, String description) {
        String nextName = name != null ? name : this.name;
        String nextLotAddress = lotAddress != null ? lotAddress : this.lotAddress;
        String nextRoadAddress = roadAddress != null ? roadAddress : this.roadAddress;

        validate(this.managerUserId, nextName, nextLotAddress, nextRoadAddress);

        this.name = nextName;
        this.lotAddress = nextLotAddress;
        this.roadAddress = nextRoadAddress;
        if (description != null) {
            this.description = description;
        }
    }


    //빠지면 안되는 값들 검증
    private void validate(UUID managerUserId, String name, String lotAddress, String roadAddress) {
        if (managerUserId == null) {
            throw new IllegalArgumentException("업체 관리자 ID는 필수입니다.");
        }

        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("업체명은 필수입니다.");
        } else if (name.length() > 255) {
            throw new IllegalArgumentException("업체명은 길이가 255이하여야 합니다.");
        }

        if (!StringUtils.hasText(lotAddress) && !StringUtils.hasText(roadAddress)) {
            throw new IllegalArgumentException("도로명 주소와 지번 주소 둘 중 하나는 입력해야 됩니다.");
        }
        if (StringUtils.hasText(lotAddress) && lotAddress.length() > 255) {
            throw new IllegalArgumentException("지번 주소의 길이는 255자 이하여야 합니다.");
        }
        if (StringUtils.hasText(roadAddress) && roadAddress.length() > 255) {
            throw new IllegalArgumentException("도로명 주소의 길이는 255자 이하여야 합니다.");
        }
    }
}
