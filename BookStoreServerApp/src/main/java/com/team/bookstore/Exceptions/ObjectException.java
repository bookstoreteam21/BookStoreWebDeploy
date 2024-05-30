package com.team.bookstore.Exceptions;

import com.team.bookstore.Enums.ErrorCodes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjectException extends  RuntimeException{
    public ObjectException(Object o,ErrorCodes errorCodes){
        super(o.toString() + errorCodes.getMessage());
        this.errorCodes=errorCodes;
    }
    private ErrorCodes errorCodes;
}
