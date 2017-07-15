package car.top.content360.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

	private static final boolean log = true;
	private static final boolean withTime = true;
	
	public static void log(String str){
		if(log){
			if(withTime){
				System.out.println(getTimeStr() + str);
			}else{
				System.out.println(str);
			}
		}
	}
	
	public static String getTimeStr(){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String dateString = formatter.format(currentTime);
	    return dateString + "-----";
	}
}
