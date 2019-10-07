package com.parkinglot.exceptions;

public class ParkingException extends Exception {

    private int errorCode=500;

    public ParkingException(String message, Throwable throwable)
    {
        super(message, throwable);
    }

    public ParkingException(String message)
    {
        super(message);
    }

    public ParkingException(Throwable throwable)
    {
        super(throwable);
    }

    public ParkingException(String message, int errorCode)
    {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
