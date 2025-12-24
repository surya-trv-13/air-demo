package com.panunited.airdemo.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class JsonRequestResult {
    private String result;

    public JsonRequestResult() {

    }

    public JsonRequestResult(String result) {
        this.result = result;
    }

}
