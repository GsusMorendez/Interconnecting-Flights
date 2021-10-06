package ryanair.interconnecting.flights.infraestructure.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import ryanair.interconnecting.flights.models.dto.input.FlightsInput;
import ryanair.interconnecting.flights.models.dto.input.RouteFlightsInput;
import ryanair.interconnecting.flights.models.dto.output.Leg;

@RequiredArgsConstructor
@Component
public class RestClientRyanAirServicesImpl implements RestClientRyanAirServices {
	private final RestTemplate restTemplate;

	private final String URL_ROUTES = "https://services-api.ryanair.com/locate/3/routes";
	private final String URL_FLIGHTS = "https://timtbl-api.ryanair.com/3/schedules/{departure}/{arrival}/years/{year}/months/{month}";

	@Override
	public List<RouteFlightsInput> getAllRoutes() {
		var allRoutes = restTemplate
				.exchange(URL_ROUTES, HttpMethod.GET, null, new ParameterizedTypeReference<List<RouteFlightsInput>>() {
				}).getBody();
		return allRoutes != null ? allRoutes : new ArrayList<>();

	}

	@Override
	public FlightsInput getFlightsInputByData(Leg leg) {
		Map<String, String> vars = new HashMap<>();
		vars.put("departure", leg.getDepartureAirport());
		vars.put("arrival", leg.getArrivalAirport());
		vars.put("year", Integer.toString(leg.getDepartureDateTime().getYear()));
		vars.put("month", Integer.toString(leg.getDepartureDateTime().getMonthValue()));

		return restTemplate.exchange(URL_FLIGHTS, HttpMethod.GET, null, new ParameterizedTypeReference<FlightsInput>() {
		}, vars).getBody();

	}

}
