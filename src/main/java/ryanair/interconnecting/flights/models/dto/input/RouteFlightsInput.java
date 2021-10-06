package ryanair.interconnecting.flights.models.dto.input;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RouteFlightsInput implements Serializable {
	private static final long serialVersionUID = -6444872770451246903L;
	private String airportFrom;
	private String airportTo;
	private String connectingAirport;
	private Boolean newRoute;
	private Boolean seasonalRoute;
	private String operator;
	private String carrierCode;
	private String group;
	private List<String> similarArrivalAirportCodes;
	private List<String> tags;

}
