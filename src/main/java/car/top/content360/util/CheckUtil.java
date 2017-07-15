package car.top.content360.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import car.top.content360.conf.Confs;

public class CheckUtil {

	public static boolean checkSignature(String signature, String timestamp, String nonce){
		
		String[] arr = new String[]{Confs.instance.load(Confs.TOKEN), timestamp, nonce};
		
		Arrays.sort(arr);
		
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		
		String temp = getSha1(content.toString());
		
		return temp.equals(signature);
	}
	
	public static String getSha1(String str){
	    if (null == str || 0 == str.length()){
	        return null;
	    }
	    char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
	            'a', 'b', 'c', 'd', 'e', 'f'};
	    try {
	        MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
	        mdTemp.update(str.getBytes("UTF-8"));
	         
	        byte[] md = mdTemp.digest();
	        int j = md.length;
	        char[] buf = new char[j * 2];
	        int k = 0;
	        for (int i = 0; i < j; i++) {
	            byte byte0 = md[i];
	            buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
	            buf[k++] = hexDigits[byte0 & 0xf];
	        }
	        return new String(buf);
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
		return null;
	}

}
