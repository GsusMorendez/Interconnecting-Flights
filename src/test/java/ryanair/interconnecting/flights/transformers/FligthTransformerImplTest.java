package ryanair.interconnecting.flights.transformers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ryanair.interconnecting.flights.models.dto.input.Flights;
import ryanair.interconnecting.flights.models.dto.output.FlightsOutput;
import ryanair.interconnecting.flights.models.dto.output.Leg;
import ryanair.interconnecting.flights.utils.Util;

@ExtendWith(MockitoExtension.class)
class FligthTransformerImplTest {

	@InjectMocks
	FligthTransformerImpl fligthTransformerImpl;
	@Mock
	Util util;

	@Test
	void testInputObjectToLeg() {

		Leg leg = fligthTransformerImpl.inputObjectToLeg("DUB", "WRO", "2021-10-16T01:00", "2021-10-16T23:00");
		assertEquals("DUB", leg.getDepartureAirport());
		assertEquals("WRO", leg.getArrivalAirport());
		assertEquals(LocalDateTime.of(2021, 10, 16, 01, 00), leg.getDepartureDateTime());
		assertEquals(LocalDateTime.of(2021, 10, 16, 23, 00), leg.getArrivalDateTime());

	}

	@Test
	void testGlightToFlightsOutput() {
		Flights flights = new Flights("FR", "1234", "20:00", "23:50");
		Leg leg = new Leg("DUB", "WRO", LocalDateTime.of(2021, 10, 16, 01, 00), LocalDateTime.of(2021, 10, 16, 01, 00));
		when(util.turnInToLocalDateTime(Mockito.anyString(), Mockito.any()))
				.thenReturn(LocalDateTime.of(2021, 10, 16, 01, 00));
		FlightsOutput flightsOutput = fligthTransformerImpl.flightToFlightsOutput(flights, leg, 0);

		assertEquals(0, flightsOutput.getStops());
		assertEquals(1, flightsOutput.getLeg().size());
		assertEquals("DUB", flightsOutput.getLeg().get(0).getDepartureAirport());
		assertEquals("WRO", flightsOutput.getLeg().get(0).getArrivalAirport());
		assertEquals(LocalDateTime.of(2021, 10, 16, 01, 00), flightsOutput.getLeg().get(0).getDepartureDateTime());
		assertEquals(LocalDateTime.of(2021, 10, 16, 01, 00), flightsOutput.getLeg().get(0).getArrivalDateTime());

	}

	@Test
	void testFlightToLeg() {
		Flights flights = new Flights("FR", "1234", "01:00", "01:00");
		Leg leg = new Leg("DUB", "WRO", LocalDateTime.of(2021, 10, 16, 01, 00), LocalDateTime.of(2021, 10, 16, 01, 00));
		when(util.turnInToLocalDateTime(Mockito.anyString(), Mockito.any()))
				.thenReturn(LocalDateTime.of(2021, 10, 16, 01, 00));
		Leg flightsTransformered = fligthTransformerImpl.flightToLeg(flights, leg);

		assertEquals("DUB", flightsTransformered.getDepartureAirport());
		assertEquals("WRO", flightsTransformered.getArrivalAirport());
		assertEquals(LocalDateTime.of(2021, 10, 16, 01, 00), leg.getDepartureDateTime());
		assertEquals(LocalDateTime.of(2021, 10, 16, 01, 00), leg.getArrivalDateTime());

	}

}
