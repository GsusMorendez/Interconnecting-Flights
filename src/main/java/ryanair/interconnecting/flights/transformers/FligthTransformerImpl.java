package ryanair.interconnecting.flights.transformers;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ryanair.interconnecting.flights.errors.CodeExceptionStore;
import ryanair.interconnecting.flights.errors.TransformerFlightException;
import ryanair.interconnecting.flights.models.dto.input.Flights;
import ryanair.interconnecting.flights.models.dto.output.FlightsOutput;
import ryanair.interconnecting.flights.models.dto.output.Leg;
import ryanair.interconnecting.flights.utils.Util;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RequiredArgsConstructor
@Component
public class FligthTransformerImpl implements FligthTransformer {

	private final Util util;

	@Override
	public Leg inputObjectToLeg(String departure, String arrival, String departureDataTime, String arrivalDateTime) {

		try {
			return new Leg(departure, arrival, LocalDateTime.parse(departureDataTime),
					LocalDateTime.parse(arrivalDateTime));
		} catch (Exception e) {
			throw new TransformerFlightException(CodeExceptionStore.DATA_TIME_FORMAT_ERROR);
		}

	}

	@Override
	public FlightsOutput flightToFlightsOutput(Flights flights, Leg legInput, int stopts) {
		var leg = new Leg();
		leg.setDepartureAirport(legInput.getDepartureAirport());
		leg.setArrivalAirport(legInput.getArrivalAirport());
		leg.setDepartureDateTime(
				util.turnInToLocalDateTime(flights.getDepartureTime(), legInput.getDepartureDateTime()));
		leg.setArrivalDateTime(util.turnInToLocalDateTime(flights.getArrivalTime(), legInput.getArrivalDateTime()));
		var flightsOutput = new FlightsOutput();
		flightsOutput.setStops(stopts);
		flightsOutput.setLeg(new ArrayList<>());
		flightsOutput.getLeg().add(leg);
		return flightsOutput;
	}

	@Override
	public Leg flightToLeg(Flights flights, Leg legInput) {
		var leg = new Leg();
		leg.setDepartureAirport(legInput.getDepartureAirport());
		leg.setArrivalAirport(legInput.getArrivalAirport());
		leg.setDepartureDateTime(
				util.turnInToLocalDateTime(flights.getDepartureTime(), legInput.getDepartureDateTime()));
		leg.setArrivalDateTime(util.turnInToLocalDateTime(flights.getArrivalTime(), legInput.getArrivalDateTime()));
		return leg;
	}

}
