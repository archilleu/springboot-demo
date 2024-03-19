package com.example.common.base.controller;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;


@Data
@EqualsAndHashCode
public class TestModel {

    @NotNull
    private String s;
    @NotNull
    private Integer i;

    public TestModel(String s, Integer i) {
        this.s = s;
        this.i = i;
    }
}
