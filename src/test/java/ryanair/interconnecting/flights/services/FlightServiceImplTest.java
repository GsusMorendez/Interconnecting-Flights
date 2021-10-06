package ryanair.interconnecting.flights.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import ryanair.interconnecting.flights.infraestructure.rest.RestClientRyanAirServicesImpl;
import ryanair.interconnecting.flights.models.dto.input.Days;
import ryanair.interconnecting.flights.models.dto.input.Flights;
import ryanair.interconnecting.flights.models.dto.input.FlightsInput;
import ryanair.interconnecting.flights.models.dto.input.RouteFlightsInput;
import ryanair.interconnecting.flights.models.dto.output.FlightsOutput;
import ryanair.interconnecting.flights.models.dto.output.Leg;
import ryanair.interconnecting.flights.transformers.FligthTransformerImpl;
import ryanair.interconnecting.flights.utils.Util;

@ExtendWith(MockitoExtension.class)
class FlightServiceImplTest {

	@InjectMocks
	FlightServiceImpl flightService;

	@Mock
	RestClientRyanAirServicesImpl restClientRyanAirServices;

	@Mock
	FligthTransformerImpl fligthTransformer;

	@Mock
	Util util;

	@Mock
	RestTemplate restTemplate;

//	@Test
//	void testGetPossibleFlights() {
//
//		Leg leg = new Leg("DUB", "WRO", LocalDateTime.of(2021, 10, 16, 01, 00), LocalDateTime.of(2021, 10, 16, 23, 00));
//		
//
//		FlightsInput flightsInput = new FlightsInput();
//		List<Days> listDays = new ArrayList<>();
//		Days days = new Days();
//		days.setDay(16);
//		listDays.add(days);
//		List<Flights> listFlights = new ArrayList<>();
//		Flights flights = new Flights("FR", "1234", "10:00", "15:0");
//		listFlights.add(flights);
//		days.setFlights(listFlights);
//		flightsInput.setDays(listDays);
//		flightsInput.setMonth(10);
//
//		when(restClientRyanAirServices.getFlightsInputByData(Mockito.any())).thenReturn(flightsInput);
//		when(util.turnInToLocalDateTime(Mockito.any(), Mockito.any()))
//				.thenReturn(LocalDateTime.of(2021, 10, 16, 10, 00));
//		when(fligthTransformer.flightToLeg(Mockito.any(), Mockito.any())).thenReturn(leg);
//
//		List<FlightsOutput> result = flightService.getPossibleFlights(leg);
//
//		assertEquals(0, 0);
//
//		// when(flightService.getAvailablesRoutes()).thenReturn(new A);
//
//	}

	@Test
	void testGenerateMatches() {
		Leg leg = new Leg("DUB", "ALC", LocalDateTime.of(2021, 10, 16, 01, 00), LocalDateTime.of(2021, 10, 16, 23, 00));
		List<RouteFlightsInput> routesList = new ArrayList<>();
		RouteFlightsInput routeFlightsInput = new RouteFlightsInput();
		routeFlightsInput.setAirportFrom("DUB");
		routeFlightsInput.setAirportTo("WRO");
		routeFlightsInput.setCarrierCode("FR");
		routeFlightsInput.setConnectingAirport(null);
		routeFlightsInput.setOperator("RYANAIR");
		routeFlightsInput.setGroup("GENERIC");
		RouteFlightsInput routeFlightsInputTwo = new RouteFlightsInput();
		routeFlightsInputTwo.setAirportFrom("WRO");
		routeFlightsInputTwo.setAirportTo("ALC");
		routeFlightsInputTwo.setCarrierCode("FR");
		routeFlightsInputTwo.setConnectingAirport(null);
		routeFlightsInputTwo.setOperator("RYANAIR");
		routeFlightsInputTwo.setGroup("GENERIC");
		routesList.add(routeFlightsInput);
		routesList.add(routeFlightsInputTwo);

		List<RouteFlightsInput> result = flightService.generateMatches(leg, routesList);
		assertEquals(routesList.get(0), result.get(0));

	}

	@Test
	void testDirectFlight() {

		List<RouteFlightsInput> availablesRoutes = new ArrayList<>();
		Leg leg = new Leg("DUB", "WRO", LocalDateTime.of(2021, 10, 16, 01, 00), LocalDateTime.of(2021, 10, 16, 23, 00));
		List<Leg> legs = new ArrayList<>();
		legs.add(leg);
		FlightsOutput flightsOutput = new FlightsOutput(0, legs);
		FlightsInput flightsInput = new FlightsInput();
		List<Days> listDays = new ArrayList<>();
		Days days = new Days();
		days.setDay(16);
		listDays.add(days);
		List<Flights> listFlights = new ArrayList<>();
		Flights flights = new Flights("FR", "1234", "10:00", "15:0");
		listFlights.add(flights);
		days.setFlights(listFlights);
		flightsInput.setDays(listDays);
		flightsInput.setMonth(10);

		when(restClientRyanAirServices.getFlightsInputByData(Mockito.any())).thenReturn(flightsInput);
		when(util.turnInToLocalDateTime(Mockito.any(), Mockito.any()))
				.thenReturn(LocalDateTime.of(2021, 10, 16, 10, 00));
		when(fligthTransformer.flightToLeg(Mockito.any(), Mockito.any())).thenReturn(leg);
		FlightsOutput result = flightService.directFlight(leg);
		assertEquals(flightsOutput.getLeg().get(0), result.getLeg().get(0));

	}

	@Test
	void testGetAvailablesRoutes() {

		List<RouteFlightsInput> routesList = new ArrayList<>();
		RouteFlightsInput routeFlightsInput = new RouteFlightsInput();
		routeFlightsInput.setAirportFrom("DUB");
		routeFlightsInput.setAirportTo("WRO");
		routeFlightsInput.setCarrierCode("FR");
		routeFlightsInput.setConnectingAirport(null);
		routeFlightsInput.setOperator("RYANAIR");
		routeFlightsInput.setGroup("GENERIC");
		routesList.add(routeFlightsInput);
		when(restClientRyanAirServices.getAllRoutes()).thenReturn(routesList);
		List<RouteFlightsInput> result = flightService.getAvailablesRoutes();
		assertEquals(routesList.size(), result.size());

	}

	@Test
	void testGetFlightsByDatetime() {
		List<Days> listDays = new ArrayList<>();
		Days days = new Days();
		days.setDay(16);
		List<Flights> listFlights = new ArrayList<>();
		Flights flights = new Flights("FR", "1234", "01:00", "20:00");
		listFlights.add(flights);
		days.setFlights(listFlights);
		listDays.add(days);
		when(util.turnInToLocalDateTime(Mockito.anyString(), Mockito.any()))
				.thenReturn(LocalDateTime.of(2021, 10, 16, 01, 00));
		List<Flights> result = flightService.getFlightsByDatetime(listDays, LocalDateTime.of(2021, 10, 16, 01, 00),
				LocalDateTime.of(2021, 10, 16, 23, 00), 0);
		assertEquals(listFlights, result);

	}

	@Test
	void testFlightsWithOneStopNoMatch() {

		Leg leg = new Leg("DUB", "ALC", LocalDateTime.of(2021, 10, 16, 01, 00), LocalDateTime.of(2021, 10, 16, 23, 00));
		Leg leg2 = new Leg("DUB", "WRO", LocalDateTime.of(2021, 10, 16, 01, 00),
				LocalDateTime.of(2021, 10, 16, 02, 00));
		Leg leg3 = new Leg("WRO", "ALC", LocalDateTime.of(2021, 10, 16, 05, 00),
				LocalDateTime.of(2021, 10, 16, 10, 00));
		List<Leg> legs = new ArrayList<>();
		legs.add(leg);
		legs.add(leg2);
		legs.add(leg3);

		FlightsOutput flightsOutput = new FlightsOutput(0, legs);
		FlightsInput flightsInput = new FlightsInput();
		List<Days> listDays = new ArrayList<>();
		Days days = new Days();
		days.setDay(16);
		listDays.add(days);
		List<Flights> listFlights = new ArrayList<>();
		Flights flights = new Flights("FR", "1234", "01:00", "02:00");
		Flights flights2 = new Flights("FR", "1234", "05:00", "06:00");
		Flights flights3 = new Flights("FR", "1234", "08:00", "09:00");

		listFlights.add(flights);
		listFlights.add(flights2);
		listFlights.add(flights3);

		days.setFlights(listFlights);
		flightsInput.setDays(listDays);
		flightsInput.setMonth(10);

		List<RouteFlightsInput> routesList = new ArrayList<>();
		RouteFlightsInput routeFlightsInput = new RouteFlightsInput();
		routeFlightsInput.setAirportFrom("DUB");
		routeFlightsInput.setAirportTo("WRO");
		routeFlightsInput.setCarrierCode("FR");
		routeFlightsInput.setConnectingAirport(null);
		routeFlightsInput.setOperator("RYANAIR");
		routeFlightsInput.setGroup("GENERIC");
		RouteFlightsInput routeFlightsInputTwo = new RouteFlightsInput();
		routeFlightsInputTwo.setAirportFrom("WRO");
		routeFlightsInputTwo.setAirportTo("ALC");
		routeFlightsInputTwo.setCarrierCode("FR");
		routeFlightsInputTwo.setConnectingAirport(null);
		routeFlightsInputTwo.setOperator("RYANAIR");
		routeFlightsInputTwo.setGroup("GENERIC");
		routesList.add(routeFlightsInput);
		routesList.add(routeFlightsInputTwo);

		when(restClientRyanAirServices.getFlightsInputByData(Mockito.any())).thenReturn(flightsInput);
		when(util.turnInToLocalDateTime(Mockito.any(), Mockito.any()))
				.thenReturn(LocalDateTime.of(2021, 10, 16, 01, 00), LocalDateTime.of(2021, 10, 16, 05, 00));
		when(fligthTransformer.flightToLeg(Mockito.any(), Mockito.any())).thenReturn(leg);
		FlightsOutput result = flightService.flightsWithOneStop(routesList, leg);
		assertNull(result.getLeg());

	}

	@Test
	void testCheckDateAndDestination() {
		Leg leg = new Leg("DUB", "ALC", LocalDateTime.of(2021, 10, 16, 01, 00), LocalDateTime.of(2021, 10, 16, 23, 00));
		FlightsInput flightsInput = new FlightsInput();
		List<Days> listDays = new ArrayList<>();
		Days days = new Days();
		days.setDay(16);
		List<Flights> listFlights = new ArrayList<>();
		Flights flights = new Flights("FR", "1234", "05:00", "07:00");
		listFlights.add(flights);
		days.setFlights(listFlights);
		listDays.add(days);
		flightsInput.setDays(listDays);
		flightsInput.setMonth(10);

		when(restClientRyanAirServices.getFlightsInputByData(Mockito.any())).thenReturn(flightsInput);
		when(util.turnInToLocalDateTime(Mockito.any(), Mockito.any()))
				.thenReturn(LocalDateTime.of(2021, 10, 16, 10, 00));
		when(fligthTransformer.flightToLeg(Mockito.any(), Mockito.any())).thenReturn(leg);
		List<Leg> result = flightService.checkDateAndDestination(leg, 0);

		assertEquals(leg, result.get(0));

	}

}
