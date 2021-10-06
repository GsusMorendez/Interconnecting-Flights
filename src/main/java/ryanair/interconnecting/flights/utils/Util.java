package ryanair.interconnecting.flights.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

//	public long getMaximunTime(LocalDateTime departure, LocalDateTime arrival) {
//		Date dateDeparture = Date.from(departure.atZone(ZoneId.systemDefault()).toInstant());
//		Date dateArrival = Date.from(arrival.atZone(ZoneId.systemDefault()).toInstant());
//		return dateArrival.getTime() - dateDeparture.getTime();
//	}
//
//	public boolean checkIfNextTripIsPosible(LocalDateTime expectedDeparture, LocalDateTime arrivalFinal) {
//		return expectedDeparture.plusHours(2).isBefore(arrivalFinal);
//	}

}
