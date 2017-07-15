package car.top.content360.exceptions;

@SuppressWarnings("serial")
public class ValidationFailureException extends WeChartException {

	public ValidationFailureException(String message){
        super(message);
    }
    
    public ValidationFailureException(String message, Throwable cause){
        super(message, cause);
    }
    
}
