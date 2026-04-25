package com.ezmeal.company.application.dto.request;

import java.util.UUID;

//인증인가가 완료되면 managerUserId 는 뺄예정
public record CompanyCreateRequest(
        UUID managerUserId,
        String name,
        String lotAddress,
        String roadAddress,
        String description
) {
}
