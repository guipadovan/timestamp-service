package com.guipadovan.timestampservice.api;

import com.guipadovan.timestampservice.api.response.DateResponse;
import com.guipadovan.timestampservice.api.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class TimestampController {

    @GetMapping("/{date}")
    public ResponseEntity<?> parseDate(@PathVariable String date) {
        LocalDateTime ldt;

        try {
            long unix = Long.parseLong(date);
            ldt = LocalDateTime.ofEpochSecond(unix, 0, ZoneOffset.UTC);
        } catch (NumberFormatException e) {
            try {
                ldt = LocalDate.parse(date).atStartOfDay();
            } catch (Exception ex) {
                ErrorResponse errorResponse = new ErrorResponse("Invalid Date");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
        }

        DateResponse dateResponse = new DateResponse(ldt.toEpochSecond(ZoneOffset.UTC), ldt.toString());

        return ResponseEntity.of(Optional.of(dateResponse));
    }

}
