package car.top.content360.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class XmlUtil {

	public static Document getDocument(InputStream inputStream) throws DocumentException{
		SAXReader reader = new SAXReader();
		Document doc = reader.read(inputStream);
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	public static Document getDocument(File xmlFile) throws DocumentException{
		SAXReader reader = new SAXReader();
		Document doc = reader.read(xmlFile);
		return doc;
	}
}
