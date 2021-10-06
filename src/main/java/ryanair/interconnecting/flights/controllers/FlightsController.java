package ryanair.interconnecting.flights.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ryanair.interconnecting.flights.models.dto.output.FlightsOutput;
import ryanair.interconnecting.flights.models.dto.output.Leg;
import ryanair.interconnecting.flights.services.FlightService;
import ryanair.interconnecting.flights.transformers.FligthTransformer;

@RequiredArgsConstructor
@RestController
@RequestMapping("ryanair/interconnections")
public class FlightsController {

	private final FlightService flightService;
	private final FligthTransformer fligthTransformer;

	@GetMapping
	public ResponseEntity<List<FlightsOutput>> getRightFlights(@RequestParam(required = true) String departure,
			@RequestParam(required = true) String arrival, @RequestParam(required = true) String departureDateTime,
			@RequestParam(required = true) String arrivalDateTime) {
		Leg currectFlight = fligthTransformer.inputObjectToLeg(departure, arrival, departureDateTime,
				arrivalDateTime);
		var bodyResponse = flightService.getPossibleFlights(currectFlight);	
		return bodyResponse.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(bodyResponse);
	}

}
