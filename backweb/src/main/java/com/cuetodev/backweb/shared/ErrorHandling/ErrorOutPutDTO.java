package com.cuetodev.backweb.shared.ErrorHandling;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class ErrorOutPutDTO extends RuntimeException {
    private Integer httpCode;
    private String msgError;
    private String type;
    private Date date;

    public ErrorOutPutDTO(Integer httpCode, String msgError, String type) {
        this.httpCode = httpCode;
        this.msgError = msgError;
        this.type = type; // Fatal, Warning, Info
        this.date = java.sql.Date.valueOf(LocalDate.now()); // Transform LocalDate to Date
    }
}
