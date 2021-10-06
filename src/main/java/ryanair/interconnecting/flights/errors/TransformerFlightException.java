package ryanair.interconnecting.flights.errors;



public class TransformerFlightException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	  public TransformerFlightException(CodeExceptionStore msg) {
	        super(msg.getCode());
	    }

}
