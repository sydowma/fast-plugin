package com.github.sydowma.fast.development.services;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ToolCallResult {

    Integer index;
    String id;

    String type;

    FunctionCallResult function;

    public ToolCallResult() {
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FunctionCallResult getFunction() {
        return function;
    }

    public void setFunction(FunctionCallResult function) {
        this.function = function;
    }
}
