package com.ezmeal.company.infrastructure.persistence;

import static com.ezmeal.company.domain.model.QCompany.company;
import com.ezmeal.company.application.dto.request.CompanySearchRequest;
import com.ezmeal.company.domain.model.Company;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;


@RequiredArgsConstructor
public class JpaCompanyRepositoryImpl implements JpaCompanyRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Company> searchCompanies(CompanySearchRequest request, Pageable pageable) {
        List<Company> content = jpaQueryFactory
                .selectFrom(company)
                .where(
                        company.deletedAt.isNull(),
                        nameContains(request.name())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory
                .select(company.count())
                .from(company)
                .where(
                        company.deletedAt.isNull(),
                        nameContains(request.name())
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }

    private BooleanExpression nameContains(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }

        return company.name.contains(name);
    }
}
