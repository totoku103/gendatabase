package me.totoku103.gendatabase.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ImcMtMsg {
    private final String mtType;
    private final String status;
    private final String priority;
    private final LocalDateTime reservedDate;
    private final String phoneNumber;
    private final String countryCode;
    private final String callback;
    private final String message;

    public ImcMtMsg(String mtType, String status, String priority, LocalDateTime reservedDate, String phoneNumber, String countryCode, String callback, String message) {
        this.mtType = mtType;
        this.status = status;
        this.priority = priority;
        this.reservedDate = reservedDate;
        this.phoneNumber = phoneNumber;
        this.countryCode = countryCode;
        this.callback = callback;
        this.message = message;
    }

    public String getReservedDate() {
        return reservedDate.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
