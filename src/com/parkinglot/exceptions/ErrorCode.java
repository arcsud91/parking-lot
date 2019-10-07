/**
 *
 */
package com.parkinglot.exceptions;

public enum ErrorCode {
    PARKING_NOT_EXIST_ERROR(404, "Sorry, Car Parking Does not Exist"),
	VEHICLE_NOT_EXIST(404, "Vehicle with {plateNumber} does not exists"),
	VEHICLE_ALREADY_EXIST(409, "Vehicle with {plateNumber} already exists"),
	PARKING_FULL(400, "No parking slots available"),
	SLOT_ALREADY_VACANT(400, "Slot {slotNumber} already vacant."),
	PROCESSING_ERROR(500, "Technical Error Occurred");

    private String message;
    private int code;

    ErrorCode(int code, String message) {
        this.message = message;
		this.code = code;
    }

    public String getMessage() {
        return message;
    }

	public int getCode() {
		return code;
	}
}
