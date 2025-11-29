package com.panunited.airdemo.utils;

import jakarta.validation.constraints.NotBlank;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    public static final int HOURS_PER_DAY = 24;
    public static final int MINUTES_PER_HOUR = 60;

    public static final LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    public static boolean isBetweenTimeRange(LocalTime time, LocalTime startTime, LocalTime endTime) {
        if (startTime.isBefore(endTime)) {
            return !time.isAfter(endTime) && !time.isBefore(startTime);
        } else {
            return !time.isAfter(endTime) || !time.isBefore(startTime);
        }
    }

    public static LocalDateTime convertCurrentSystemToUtcTimeZone(LocalDateTime systemTime) {
        // get current zone from system clock
        return convertTimeZone(systemTime, Clock.systemDefaultZone().getZone().getId(), "UTC");
    }

    public static LocalDateTime convertTimeZone(LocalDateTime dateTime, String fromZone, String toZone) {
        ZonedDateTime newDateTime = dateTime.atZone(ZoneId.of(fromZone)).withZoneSameInstant(ZoneId.of(toZone));
        return newDateTime.toLocalDateTime();
    }

    public static boolean isWithinRange(LocalDateTime input, LocalDateTime start, LocalDateTime end) {
        return !input.isBefore(start) && !input.isAfter(end);
    }

    public static String convertToLocalDateTimeStringDDMMMYYYHHMM(LocalDateTime localDate) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm");
        return localDate != null ? localDate.format(outputFormatter) : "";
    }
    public static LocalDateTime fromUTCToNewZealandTime(LocalDateTime utcTime) {
        ZonedDateTime zonedUtc = ZonedDateTime.of(utcTime, ZoneId.of("UTC"));
        return zonedUtc.withZoneSameInstant(ZoneId.of("Pacific/Auckland")).toLocalDateTime();
    }

    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now(ZoneOffset.UTC);
    }
}
