package ryanair.interconnecting.flights.models.dto.input;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Flights implements Serializable {

	private static final long serialVersionUID = 7178011273224481635L;
	private String carrierCode;
	private String number;
	private String departureTime;
	private String arrivalTime;

}
