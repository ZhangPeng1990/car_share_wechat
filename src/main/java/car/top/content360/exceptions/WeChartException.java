package car.top.content360.exceptions;

@SuppressWarnings("serial")
public class WeChartException extends RuntimeException {

	public WeChartException(){
		super();
	}
	
	public WeChartException(String message){
		super(message);
	}
	
	public WeChartException(String message, Throwable cause){
		super(message, cause);
	}
}
