package com.springboot.assignment.taxiapplication.model.search;

public enum EnumSearchOperation {
    EQUAL,
    NOT_EQUAL;

    public static final String[] SIMPLE_OPERATION_SET = {
            "eq", "ne",
    };

    public static EnumSearchOperation getSimpleOperation(final String input) {
        switch (input){
            case "eq": return EQUAL;
            case "ne": return NOT_EQUAL;

            default: return null;
        }
    }
}
