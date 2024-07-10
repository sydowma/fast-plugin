package com.github.sydowma.fast.development.services;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FunctionCallResult {

    String name;

    String arguments;
}
