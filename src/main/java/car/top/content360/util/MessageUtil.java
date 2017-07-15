package car.top.content360.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.thoughtworks.xstream.XStream;

import car.top.content360.po.Message;

public class MessageUtil {

	public static Map<String, String> xml2Map(HttpServletRequest request){
		Map<String, String> map = new HashMap<String, String>();
		
		try {
			Document doc = XmlUtil.getDocument(request.getInputStream());
			if(doc != null){
				Element root = doc.getRootElement();
				@SuppressWarnings("unchecked")
				List<Element> elements = root.elements();
				
				for (Element element : elements) {
					map.put(element.getName(), element.getText());
				}
			}
			return map;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String message2Xml(Message message){
		XStream xstream = new XStream();
		xstream.alias("xml", message.getClass());
		return xstream.toXML(message);
	}
}
