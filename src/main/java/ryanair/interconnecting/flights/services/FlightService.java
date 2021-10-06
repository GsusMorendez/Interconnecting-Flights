package ryanair.interconnecting.flights.services;

import java.util.List;

import ryanair.interconnecting.flights.models.dto.input.RouteFlightsInput;
import ryanair.interconnecting.flights.models.dto.output.FlightsOutput;
import ryanair.interconnecting.flights.models.dto.output.Leg;

public interface FlightService {

	List<FlightsOutput> getPossibleFlights(Leg leg);

	List<RouteFlightsInput> getAvailablesRoutes();

}
