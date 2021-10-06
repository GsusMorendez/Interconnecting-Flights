package ryanair.interconnecting.flights.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class Util {

	public LocalDateTime turnInToLocalDateTime(String time, LocalDateTime localDateTime) {
		var dayOfMonth = localDateTime.getDayOfMonth() < 10 ? "0" + localDateTime.getDayOfMonth()
				: localDateTime.getDayOfMonth();
		String fullTime = localDateTime.getYear() + "-" + localDateTime.getMonthValue() + "-" + dayOfMonth + " "
				+ time.substring(0, 2) + ":" + time.substring(3, time.length());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return LocalDateTime.parse(fullTime, formatter);

	}

}
