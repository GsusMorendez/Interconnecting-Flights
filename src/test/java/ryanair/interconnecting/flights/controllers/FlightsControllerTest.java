package ryanair.interconnecting.flights.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ryanair.interconnecting.flights.models.dto.output.FlightsOutput;
import ryanair.interconnecting.flights.models.dto.output.Leg;
import ryanair.interconnecting.flights.services.FlightService;
import ryanair.interconnecting.flights.transformers.FligthTransformer;

@ExtendWith(MockitoExtension.class)
class FlightsControllerTest {

	@InjectMocks
	FlightsController flightsController;
	@Mock
	FlightService flightService;
	@Mock
	FligthTransformer fligthTransformer;

	@Test
	void testGetRightFlights() {

		Leg leg = new Leg("DUB", "WRO", LocalDateTime.of(2021, 10, 16, 01, 00), LocalDateTime.of(2021, 10, 16, 23, 00));
		List<Leg> legs = new ArrayList<>();
		legs.add(leg);
		FlightsOutput flightsOutput = new FlightsOutput(0, legs);
		List<FlightsOutput> listResult = new ArrayList<>();
		listResult.add(flightsOutput);

		when(fligthTransformer.inputObjectToLeg(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(leg);

		when(flightService.getPossibleFlights(leg)).thenReturn(listResult);

		ResponseEntity<List<FlightsOutput>> responseEntity = flightsController.getRightFlights("DUB",
				"WRO", "2021-10-16T01:00", "2021-10-16T023:00");

		assertNotNull(responseEntity.getBody());
		assertEquals(responseEntity.getBody(), listResult);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

}
