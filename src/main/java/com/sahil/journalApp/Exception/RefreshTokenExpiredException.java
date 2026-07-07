package com.sahil.journalApp.Exception;

public class RefreshTokenExpiredException extends RuntimeException{
    public RefreshTokenExpiredException(String msg){
        super(msg);
    }
}
