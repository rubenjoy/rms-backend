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
            BooleanExpression genderExp = employee.gender.eq(filter.getGender());
            expression = expression == null ?  genderExp : expression.and(genderExp);
        }

        if (!StringUtils.isEmpty(filter.getDivision())) {
            BooleanExpression divisionExp = employee.division.eq(filter.getDivision());
            expression = expression == null ? divisionExp  : expression.and(divisionExp);
        }

        if (!StringUtils.isEmpty(filter.isActive())) {
            BooleanExpression activeExp = filter.isActive() ? employee.suspendDate.isNull() : employee.suspendDate.isNotNull();
            expression = expression == null ?  activeExp : expression.and(activeExp);
        }

//        if (!StringUtils.isEmpty(filter.getGrade())) {
//            BooleanExpression gradeExpression = employee.grades.isNotEmpty().and(employee.grades.contains());
//
//            System.out.println("gradeExpression: " + gradeExpression);
//            expression = expression == null ? gradeExpression :
//                    expression.and(gradeExpression);
//        }
    }
}
