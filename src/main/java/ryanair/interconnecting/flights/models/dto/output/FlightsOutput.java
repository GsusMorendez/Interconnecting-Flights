package ryanair.interconnecting.flights.models.dto.output;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightsOutput implements Serializable{
	private static final long serialVersionUID = -1583993559158543371L;
	private int stops;
	private List<Leg> leg;

}
