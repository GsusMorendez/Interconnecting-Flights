package ryanair.interconnecting.flights.models.dto.output;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Leg implements Serializable{
	private static final long serialVersionUID = -747996049612979099L;
	private String departureAirport;
	private String arrivalAirport;
	private LocalDateTime departureDateTime;
	private LocalDateTime arrivalDateTime;

}
