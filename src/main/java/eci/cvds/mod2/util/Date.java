package eci.cvds.mod2.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Date {
    LocalDate day;
    LocalTime time;
}
