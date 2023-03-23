package com.springboot.assignment.taxiapplication.model.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarSearchDto {
    private List<SearchCriteria> searchCriteriaList;
}
