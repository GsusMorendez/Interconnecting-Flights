package ryanair.interconnecting.flights.models.dto.input;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Days implements Serializable {
	private static final long serialVersionUID = 7405705671034649414L;
	private int day;
	private List<Flights> flights;

}
