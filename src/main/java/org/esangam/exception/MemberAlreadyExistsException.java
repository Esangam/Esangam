package org.esangam.exception;

public class MemberAlreadyExistsException extends RuntimeException {

    public MemberAlreadyExistsException(String mobileNumber) {
        super("Member with mobile number " + mobileNumber + " already exists");
    }
}
