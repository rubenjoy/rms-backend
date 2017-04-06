package com.mitrais.bootcamp.rms.data.web;

import com.mitrais.bootcamp.rms.data.entity.QEmployee;
import com.mitrais.bootcamp.rms.data.entity.QGrade;
import com.mitrais.bootcamp.rms.data.entity.QOfficeLocation;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class FilterExpression {
    @PersistenceContext
    private EntityManager entityManager;

    private BooleanBuilder expression = new BooleanBuilder();

    public BooleanBuilder getExpression() {
        return expression;
    }

    public FilterExpression(FilterDto filter) {

        QEmployee employee = QEmployee.employee;

        if (!StringUtils.isEmpty(filter.getGender())) {
            BooleanExpression exp = employee.gender.eq(filter.getGender());
            expression = expression.and(exp);
        }

        if (!StringUtils.isEmpty(filter.getDivision())) {
            BooleanExpression exp = employee.division.eq(filter.getDivision());
            expression = expression.and(exp);
        }

        if (!StringUtils.isEmpty(filter.getIsActive())) {
            BooleanExpression exp = Boolean.parseBoolean(filter.getIsActive()) ? employee.suspendDate.isNull() : employee.suspendDate.isNotNull();
            expression = expression.and(exp);
        }

        if (!StringUtils.isEmpty(filter.getEmpStatus())) {
            BooleanExpression exp = employee.empStatus.eq(filter.getEmpStatus());
            expression = expression.and(exp);
        }

        if (!StringUtils.isEmpty(filter.getMaritalStatus())) {
            BooleanExpression exp = employee.maritalStatus.eq(filter.getMaritalStatus());
            expression = expression.and(exp);
        }

        if (!StringUtils.isEmpty(filter.getGrade())) {
            QGrade grade = QGrade.grade1;

            BooleanExpression exp = employee.grades.isNotEmpty().and(
                    JPAExpressions.select(grade).from(grade).where(grade.in(employee.grades)
                            .and(grade.endDate.isNull().and(grade.grade.eq(filter.getGrade())))).exists());

            expression = expression.and(exp);
        }

        if (!StringUtils.isEmpty(filter.getLocation())) {
            QOfficeLocation location = QOfficeLocation.officeLocation1;

            BooleanExpression exp = employee.officeLocations.isNotEmpty().and(
                    JPAExpressions.select(location).from(location).where(location.in(employee.officeLocations)
                            .and(location.endDate.isNull().and(location.officeLocation.eq(filter.getLocation())))).exists());

            expression = expression.and(exp);
        }
    }
}
