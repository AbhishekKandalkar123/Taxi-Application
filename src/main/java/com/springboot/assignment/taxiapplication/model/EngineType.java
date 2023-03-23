package com.springboot.assignment.taxiapplication.model;

public enum EngineType {
        ELECTRIC("Electric"),
        GAS("Gas"),
        FUEL("Fuel");

        public static EngineType getEngineType(String name){
                for(EngineType c : EngineType.values()) {
                        if(c.getName().equalsIgnoreCase(name)){
                                return c;
                        }
                }
                return null;
        }

        private String name;
        private EngineType(String name){
                this.name = name;
        }

        public String getName(){
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }
}

