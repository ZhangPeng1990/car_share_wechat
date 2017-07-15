package car.top.content360.util;

import top.content360.enums.MsgType;

public class RequestUtil {

	public static String getRemoteHost(javax.servlet.http.HttpServletRequest request){
		Log.log("requesting " + request.getServletPath());
		
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}

	public static void main(String[] args) {
		System.out.println(MsgType.event.name());
	}
}
