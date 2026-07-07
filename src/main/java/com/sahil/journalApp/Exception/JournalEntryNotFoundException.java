package com.sahil.journalApp.Exception;

public class JournalEntryNotFoundException extends RuntimeException{

    public JournalEntryNotFoundException(String msg){
        super(msg);
    }
}
