package com.cxl.exception;

import org.omg.SendingContext.RunTime;

public class CxlException extends RuntimeException {
    public CxlException(String message) {
        super(message);
    }
}
