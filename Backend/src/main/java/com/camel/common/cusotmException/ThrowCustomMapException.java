package com.camel.common.cusotmException;

import com.camel.common.CustomMap;

/*
* ****************************************************************************************
* Title : Custom Exception
* Scope : public
* Class Name : ThrowCustomMapException
* ----------------------------------------------------------------------------------------
* Description : 사용자 정의 Exception
* 
******************************************************************************************/
public class ThrowCustomMapException extends Exception {
    private final CustomMap response;

    public ThrowCustomMapException(String message, CustomMap response, int status){
        super(message);
        this.response = response;
        this.response.put("message",message);
        this.response.put("status",status);
    }

    public CustomMap getResponse() {
        return response;
    }
}
