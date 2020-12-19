package io.git.cddit.login.exception;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class   ApiErrors {

    @Getter     //Constrói automaticamente o método get no momento da compilação
    private List<String> errors;

    public ApiErrors(List<String> errors){
        this.errors = errors;
    }

    public ApiErrors(String message){
        this.errors = Arrays.asList(message);
    }

}
