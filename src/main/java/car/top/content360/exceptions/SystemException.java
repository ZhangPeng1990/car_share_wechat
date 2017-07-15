package car.top.content360.exceptions;

@SuppressWarnings("serial")
public class SystemException extends WeChartException {

	public SystemException(String message){
        super(message);
    }
    
    public SystemException(String message, Throwable cause){
        super(message, cause);
    }
    
}
