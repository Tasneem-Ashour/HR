package com.project.HR.Exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class IdNotFound extends  RuntimeException{
    Integer id;

    public IdNotFound(Integer id) {
        super(String.format("%s Id does not Exist",id));
        this.id = id;
    }
}
