package org.example.cardservice.exception;

public class CustomNotfoundException extends  RuntimeException{
    public CustomNotfoundException(String message){
        super(message);
    }
}
