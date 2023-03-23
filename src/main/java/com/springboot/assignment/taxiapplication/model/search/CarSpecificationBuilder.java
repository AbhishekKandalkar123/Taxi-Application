package com.springboot.assignment.taxiapplication.model.search;

import com.springboot.assignment.taxiapplication.model.Car;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CarSpecificationBuilder {
    private final List<SearchCriteria> params;

    public CarSpecificationBuilder() {
        this.params = new ArrayList<>();
    }
    public final CarSpecificationBuilder with(String key,String operation, Object value){
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Car> build(){
        if(params.size()==0){
            return null;
        }

        List<Specification<Car>> specs = new ArrayList<>();
        for(SearchCriteria param: params){
            specs.add(new CarSpecification(param));
        }

        Specification<Car> result = new CarSpecification(params.get(0));
        for(int i=1;i<specs.size();i++){
            result=Specification.where(result).and(specs.get(i));
        }
            return result;
    }
}
