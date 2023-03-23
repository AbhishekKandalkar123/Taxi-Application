package com.springboot.assignment.taxiapplication.model.search;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchCriteria {
    private String filterKey;
    private String operation;
    private Object value;

    public SearchCriteria(String filterKey, String operation, Object value) {
        this.filterKey = filterKey;
        this.operation = operation;
        this.value = value;
    }
}
