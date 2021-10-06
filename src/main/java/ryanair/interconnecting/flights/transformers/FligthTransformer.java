package ryanair.interconnecting.flights.transformers;

import ryanair.interconnecting.flights.models.dto.input.Flights;
import ryanair.interconnecting.flights.models.dto.output.FlightsOutput;
import ryanair.interconnecting.flights.models.dto.output.Leg;

public interface FligthTransformer {
	Leg inputObjectToLeg(String departure, String arrival, String departureDataTime, String arrivalDateTime);
	FlightsOutput flightToFlightsOutput(Flights flights, Leg leg, int stopts);
	Leg flightToLeg(Flights flights, Leg leg);


}
