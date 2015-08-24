package com.dbframe.exception;


public class UnsupportedOperationException extends RuntimeException{
    
    private static final long serialVersionUID = 3847668165064463669L;

    public UnsupportedOperationException() {
        super();
    }
    
    public UnsupportedOperationException(String message) {
        super(message);
    }
    
    public UnsupportedOperationException(String message, Throwable cause) {
        super(message,cause);
    }
    
    public UnsupportedOperationException(Throwable cause) {
        super(cause);
    }
    
}
