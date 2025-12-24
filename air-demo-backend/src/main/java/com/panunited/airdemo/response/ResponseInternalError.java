package com.panunited.airdemo.response;

import com.panunited.airdemo.dto.JsonRequestResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseInternalError extends ResponseEntity<Object> {
    private final static String errorMsg = "An error ocurred at the server while processing your request.";

    public ResponseInternalError() {
        super(new JsonRequestResult(errorMsg), HttpStatus.BAD_REQUEST);
    }
}
