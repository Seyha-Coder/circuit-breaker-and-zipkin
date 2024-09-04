package org.example.studentservice.exception;

public class CustomNotfoundException extends  RuntimeException{
    public CustomNotfoundException(String message){
        super(message);
    }
}
