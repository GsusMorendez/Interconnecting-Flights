package ryanair.interconnecting.flights.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import ryanair.interconnecting.flights.infraestructure.rest.RestClientRyanAirServicesImpl;
import ryanair.interconnecting.flights.models.dto.input.Days;
import ryanair.interconnecting.flights.models.dto.input.Flights;
import ryanair.interconnecting.flights.models.dto.input.FlightsInput;
import ryanair.interconnecting.flights.models.dto.input.RouteFlightsInput;
import ryanair.interconnecting.flights.models.dto.output.Leg;

@ExtendWith(MockitoExtension.class)
class RestClientRyanAirServicesImplTest {

	@InjectMocks
	RestClientRyanAirServicesImpl restClientRyanAirServices;
	@Mock
	RestTemplate restTemplate;

	private final String URL_ROUTES = "https://services-api.ryanair.com/locate/3/routes";
	private final String URL_FLIGHTS = "https://timtbl-api.ryanair.com/3/schedules/{departure}/{arrival}/years/{year}/months/{month}";

	@Test
	void testGetAllRoutes() {

		List<RouteFlightsInput> routesList = new ArrayList<>();
		RouteFlightsInput routeFlightsInput = new RouteFlightsInput();
		routeFlightsInput.setAirportFrom("DUB");
		routeFlightsInput.setAirportTo("WRO");
		routeFlightsInput.setCarrierCode("FR");
		routeFlightsInput.setConnectingAirport(null);
		routeFlightsInput.setOperator("RYANAIR");
		routeFlightsInput.setGroup("GENERIC");
		routesList.add(routeFlightsInput);
		when(restTemplate.exchange(URL_ROUTES, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<RouteFlightsInput>>() {
				})).thenReturn(ResponseEntity.of(Optional.of(routesList)));
		List<RouteFlightsInput> resulList = restClientRyanAirServices.getAllRoutes();
		assertEquals(routesList, resulList);

	}

	@Test
	void testGetFlightsInputByData() {
		Leg leg = new Leg("DUB", "WRO", LocalDateTime.of(2021, 10, 16, 01, 00), LocalDateTime.of(2021, 10, 16, 23, 00));
		FlightsInput flightsInput = new FlightsInput();
		List<Days> listDays = new ArrayList<>();
		Days days = new Days();
		days.setDay(1);
		List<Flights> listFlights = new ArrayList<>();
		Flights flights = new Flights("FR", "1234", "10:00", "15:0");
		listFlights.add(flights);
		days.setFlights(listFlights);
		flightsInput.setDays(listDays);
		flightsInput.setMonth(2);
		Map<String, String> vars = new HashMap<>();
		vars.put("departure", leg.getDepartureAirport());
		vars.put("arrival", leg.getArrivalAirport());
		vars.put("year", Integer.toString(leg.getDepartureDateTime().getYear()));
		vars.put("month", Integer.toString(leg.getDepartureDateTime().getMonthValue()));
		when(restTemplate.exchange(URL_FLIGHTS, HttpMethod.GET, null, new ParameterizedTypeReference<FlightsInput>() {
		}, vars)).thenReturn(ResponseEntity.of(Optional.of(flightsInput)));
		FlightsInput result = restClientRyanAirServices.getFlightsInputByData(leg);
		assertEquals(flightsInput, result);

	}

}
