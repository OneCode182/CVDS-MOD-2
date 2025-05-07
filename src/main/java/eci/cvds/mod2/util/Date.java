package eci.cvds.mod2.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAmount;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Date {
    LocalDate day;
    LocalTime time;

    public static boolean checkValidDate(Date date) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        return date.getDay().isEqual(today) &&
                date.getTime().isAfter(now.plusHours(2));
    }
}
