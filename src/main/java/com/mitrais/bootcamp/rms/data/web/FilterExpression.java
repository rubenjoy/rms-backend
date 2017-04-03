package com.mitrais.bootcamp.rms.data.web;

import com.mitrais.bootcamp.rms.data.entity.QEmployee;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class FilterExpression {
    @PersistenceContext
    private EntityManager entityManager;

    private BooleanExpression expression = null;

    public BooleanExpression getExpression() {
        return expression;
    }

    public FilterExpression(FilterDTO filter) {

        QEmployee employee = QEmployee.employee;

        if (!StringUtils.isEmpty(filter.getGender())) {
            BooleanExpression exp = employee.gender.eq(filter.getGender());
            expression = expression == null ?  exp : expression.and(exp);
        }

        if (!StringUtils.isEmpty(filter.getDivision())) {
            BooleanExpression exp = employee.division.eq(filter.getDivision());
            expression = expression == null ? exp  : expression.and(exp);
        }

        System.out.println("bug 1: " + !StringUtils.isEmpty(filter.getIsActive()));
        if (!StringUtils.isEmpty(filter.getIsActive())) {
            BooleanExpression exp = Boolean.parseBoolean(filter.getIsActive()) ? employee.suspendDate.isNull() : employee.suspendDate.isNotNull();
            expression = expression == null ?  exp : expression.and(exp);
        }

        if (!StringUtils.isEmpty(filter.getEmpStatus())) {
            BooleanExpression exp = employee.empStatus.eq(filter.getEmpStatus());
            expression = expression == null ?  exp : expression.and(exp);
        }

        if (!StringUtils.isEmpty(filter.getGrade())) {
            BooleanExpression exp = employee.grades.isNotEmpty().and(
                    employee.grades.any().grade.eq(filter.getGrade()).andAnyOf(employee.grades.any().endDate.isNotNull()));

            System.out.println("gradeExpression: " + exp);
            expression = expression == null ? exp :
                    expression.and(exp);
        }

        System.out.println("expression: " + expression);
    }
}
