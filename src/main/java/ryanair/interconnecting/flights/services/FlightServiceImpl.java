package ryanair.interconnecting.flights.services;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ryanair.interconnecting.flights.infraestructure.rest.RestClientRyanAirServices;
import ryanair.interconnecting.flights.models.dto.input.Days;
import ryanair.interconnecting.flights.models.dto.input.Flights;
import ryanair.interconnecting.flights.models.dto.input.RouteFlightsInput;
import ryanair.interconnecting.flights.models.dto.output.FlightsOutput;
import ryanair.interconnecting.flights.models.dto.output.Leg;
import ryanair.interconnecting.flights.transformers.FligthTransformer;
import ryanair.interconnecting.flights.utils.Util;

@RequiredArgsConstructor
@Service
public class FlightServiceImpl implements FlightService {

	private final RestClientRyanAirServices restClientRyanAirServices;
	private final FligthTransformer fligthTransformer;
	private final Util util;
	private final int SCALE_TIME = 2;

	@Override
	public List<FlightsOutput> getPossibleFlights(Leg leg) {
		List<FlightsOutput> result = new ArrayList<>();
		List<RouteFlightsInput> availablesRoutes = getAvailablesRoutes();

		var directFligth = directFlight(leg);
		var flightWithOneStop = flightsWithOneStop(generateMatches(leg, availablesRoutes), leg);

		if (directFligth != null) {
			result.add(directFligth);
		}

		if (flightWithOneStop.getLeg() != null) {
			result.add(flightWithOneStop);
		}

		return result;

	}

	@Override
	public List<RouteFlightsInput> getAvailablesRoutes() {
		return restClientRyanAirServices.getAllRoutes().stream()
				.filter(r -> r.getConnectingAirport() == null && "RYANAIR".equals(r.getOperator()))
				.collect(Collectors.toList());
	}

	public FlightsOutput flightsWithOneStop(List<RouteFlightsInput> routesList, Leg leg) {

		FlightsOutput flightsOutputResult = new FlightsOutput();
		for (int i = 0; i < routesList.size(); i++) {

			var firstLeg = checkDateAndDestination(
					new Leg(routesList.get(i).getAirportFrom(), routesList.get(i).getAirportTo(),
							leg.getDepartureDateTime(), leg.getArrivalDateTime().minusHours(SCALE_TIME)),
					SCALE_TIME);

			if (!firstLeg.isEmpty()) {

				var connectedLeg = checkDateAndDestination(
						new Leg(firstLeg.get(0).getArrivalAirport(), leg.getArrivalAirport(),
								firstLeg.get(0).getArrivalDateTime().plusHours(SCALE_TIME), leg.getArrivalDateTime()),
						0);

				if (!connectedLeg.isEmpty()) {
					var legsInterconected = new ArrayList<Leg>();
					legsInterconected.add(firstLeg.get(0));
					legsInterconected.add(connectedLeg.get(0));
					flightsOutputResult.setLeg(legsInterconected);
					flightsOutputResult.setStops(1);
				}

			}

		}

		return flightsOutputResult;

	}

	public FlightsOutput directFlight(Leg leg) {
		var directLeg = checkDateAndDestination(leg, 0);
		if (directLeg != null && !directLeg.isEmpty()) {
			return new FlightsOutput(0, directLeg);
		}
		return null;
	}

	public List<RouteFlightsInput> generateMatches(Leg leg, List<RouteFlightsInput> availablesRoutes) {
		var departureMatch = availablesRoutes.stream().filter(f -> f.getAirportFrom().equals(leg.getDepartureAirport()))
				.collect(Collectors.toList());
		var arrivalMatch = availablesRoutes.stream().filter(f -> f.getAirportTo().equals(leg.getArrivalAirport()))
				.collect(Collectors.toList());
		return departureMatch.stream()
				.filter(f -> arrivalMatch.stream().anyMatch(r -> r.getAirportFrom().equals(f.getAirportTo())))
				.collect(Collectors.toList());
	}

	public List<Flights> getFlightsByDatetime(List<Days> days, LocalDateTime departureTime, LocalDateTime arrivalTime,
			int scaleTime) {
		var matchTripDatetime = days.stream().filter(d -> d.getDay() == departureTime.getDayOfMonth())
				.collect(Collectors.toList());

		if (!matchTripDatetime.isEmpty()) {
			return matchTripDatetime.get(0).getFlights().stream()
					.filter(f -> util.turnInToLocalDateTime(f.getDepartureTime(), departureTime).isAfter(departureTime)
							|| util.turnInToLocalDateTime(f.getDepartureTime(), departureTime).isEqual(departureTime)

					)
					.filter(f -> util.turnInToLocalDateTime(f.getArrivalTime(), arrivalTime)
							.isBefore(arrivalTime.minusHours(scaleTime))
							|| util.turnInToLocalDateTime(f.getArrivalTime(), arrivalTime)
									.isEqual(arrivalTime.minusHours(scaleTime)))
					.collect(Collectors.toList());
		}
		return new ArrayList<>();

	}

	public List<Leg> checkDateAndDestination(Leg leg, int scaleTime) {
		var days = restClientRyanAirServices.getFlightsInputByData(leg).getDays();
		return getFlightsByDatetime(days, leg.getDepartureDateTime(), leg.getArrivalDateTime(), scaleTime).stream()
				.map(m -> fligthTransformer.flightToLeg(m, leg)).collect(Collectors.toList());
	}

}
