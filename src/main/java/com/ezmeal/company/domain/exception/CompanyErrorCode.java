package com.ezmeal.company.domain.exception;

import com.ezmeal.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CompanyErrorCode implements ErrorCode {

    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "COMPANY_404", "업체를 찾을 수 없습니다."),
    COMPANY_ALREADY_EXISTS(HttpStatus.CONFLICT, "COMPANY_409", "이미 존재하는 업체입니다."),
    UNAUTHORIZED_COMPANY_ACCESS(HttpStatus.FORBIDDEN, "COMPANY_403", "해당 업체에 대한 권한이 없습니다."),
    COMPANY_ACCESS_DENIED(HttpStatus.FORBIDDEN, "COMPANY_403", "업체에 접근할 권한이 없습니다."),

    COMPANY_DELIVERY_AREA_NOT_FOUND(HttpStatus.NOT_FOUND, "COMPANY_DELIVERY_AREA_404", "배달 지역을 찾을 수 없습니다."),
    COMPANY_DELIVERY_AREA_ALREADY_EXISTS(HttpStatus.CONFLICT, "COMPANY_DELIVERY_AREA_409", "이미 등록된 업체 배달 가능 지역입니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}
