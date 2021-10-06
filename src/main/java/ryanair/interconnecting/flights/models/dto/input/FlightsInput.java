package ryanair.interconnecting.flights.models.dto.input;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class FlightsInput implements Serializable{
	private static final long serialVersionUID = -3877151151817828966L;
	private int month;
	private List<Days> days;

}
