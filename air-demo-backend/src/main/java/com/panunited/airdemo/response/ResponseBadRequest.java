package com.panunited.airdemo.response;


import com.panunited.airdemo.dto.JsonRequestResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBadRequest extends ResponseEntity<Object> {
    private final static String errorMsg = "Request is invalid and cannot be processed.";

    public ResponseBadRequest() {
        super(new JsonRequestResult(errorMsg), HttpStatus.BAD_REQUEST);
    }

    public ResponseBadRequest(String from) {
        super(new JsonRequestResult("Please click Discharge Start Time and Complete Time."), HttpStatus.BAD_REQUEST);
    }
}
