package ryanair.interconnecting.flights.infraestructure.rest;

import java.util.List;

import ryanair.interconnecting.flights.models.dto.input.FlightsInput;
import ryanair.interconnecting.flights.models.dto.input.RouteFlightsInput;
import ryanair.interconnecting.flights.models.dto.output.Leg;

public interface RestClientRyanAirServices {

	List<RouteFlightsInput> getAllRoutes();

	FlightsInput getFlightsInputByData(Leg leg);

}
