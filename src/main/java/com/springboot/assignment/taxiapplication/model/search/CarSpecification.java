package com.springboot.assignment.taxiapplication.model.search;

import com.springboot.assignment.taxiapplication.model.Car;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Objects;

public class CarSpecification implements Specification<Car> {
    private final SearchCriteria searchCriteria;

    public CarSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Car>root, CriteriaQuery<?>query, CriteriaBuilder criteriaBuilder) {

        switch(Objects.requireNonNull(EnumSearchOperation.getSimpleOperation(
                searchCriteria.getOperation()))){
            case EQUAL:
                return criteriaBuilder.equal(root.get(searchCriteria.getFilterKey()),searchCriteria.getValue());
            case NOT_EQUAL:
                return criteriaBuilder.notEqual(root.get(searchCriteria.getFilterKey()),searchCriteria.getValue());
            default:
                return null;
        }
    }
}
