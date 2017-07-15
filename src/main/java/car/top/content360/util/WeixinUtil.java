package car.top.content360.util;


import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;

import net.sf.json.JSONObject;
import car.top.content360.conf.Confs;
import car.top.content360.exceptions.ValidationFailureException;
import car.top.content360.menu.Button;
import car.top.content360.menu.ClickButton;
import car.top.content360.menu.Menu;
import car.top.content360.menu.ViewButton;
import car.top.content360.po.AccessToken;
public class WeixinUtil {
	
	/**
	 *获取access_token
	 * @return
	 */
	public static AccessToken getAccessToken(){
		AccessToken token = new AccessToken();
		String url = Confs.instance.load(Confs.ACCESS_TOKEN_URL).replace("APPID", Confs.instance.load(Confs.APPID)).replace("APPSECRET", Confs.instance.load(Confs.APPSECRET));
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject != null){
			token.setToken(jsonObject.getString("access_token"));
			token.setExpires_in(jsonObject.getInt("expires_in"));
		}
		
		return token;
	}
	
	public static JSONObject doGetStr(String url){
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				String result = EntityUtils.toString(entity, "UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	public static JSONObject dopostStr(String url, String outStr){
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		JSONObject jsonObject = null;
		
		try {
			httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			
			HttpEntity entity = response.getEntity();
			if(entity != null){
				String result = EntityUtils.toString(entity, "UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	/**
	 * 初始化微信菜单
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Menu initMenu(){
		Log.log("begin init Menu");
		
		Resource resource = new ClassPathResource("Menu.xml");
		File menuXmlFile = null;
		try {
			menuXmlFile = resource.getFile();
		} catch (IOException e) {
			throw new ValidationFailureException("Can't load menu file : Menu.xml");
		}
		
		Document menuDoc = null;
		try {
			menuDoc = XmlUtil.getDocument(menuXmlFile);
		} catch (DocumentException e) {
			throw new ValidationFailureException("Can't load menu file to Document");
		}
		
		Menu menu = new Menu();
		Button[] buttons = new Button[3];
		menu.setButton(buttons);
		
		Element rootElement = menuDoc.getRootElement();
		List<Element> elements = rootElement.elements("Button");
		if(CollectionUtils.isEmpty(elements) || elements.size() != 3){
			throw new ValidationFailureException("The content of Menu.xml is not right.");
		}
		
		
		for (int i = 0; i < 3; i++) {
			Button button = null;
			Element e = elements.get(i);
			if(e.element("type") != null){
				String buttonType = e.element("type").getTextTrim();
				switch (buttonType) {
				case "click":
					button = new ClickButton(e.element("key").getTextTrim());
					break;
					
				case "view":
					button = new ViewButton(e.element("url").getTextTrim());
					break;
					
				default:
					button = new Button();
					break;
				}
				button.setType(e.element("type").getTextTrim());
			}else{
				button = new Button();
			}
			button.setName(e.element("name").getText());
			buttons[i] = button;
			
			//sub buttons
			List<Element> sub_es = e.elements("sub_button");
			if(!CollectionUtils.isEmpty(sub_es)){
				addSubButtons(button, e);
			}
		}
		
		return menu;
	}
	
	@SuppressWarnings("unchecked")
	private static void addSubButtons(Button fatherButton, Element e){
		List<Element> subButtonEs = (List<Element> ) e.elements("sub_button");
		int i = 0;
		Button subButtons[] = new Button[subButtonEs.size()];
		
		for (Element sub_e : subButtonEs) {
			fatherButton.setSub_button(subButtons);
			Button subButton = null;
			if(sub_e.element("type") != null){
				String buttonType = sub_e.element("type").getTextTrim();
				switch (buttonType) {
				case "click":
					subButton = new ClickButton(sub_e.element("key").getTextTrim());
					break;
					
				case "view":
					subButton = new ViewButton(sub_e.element("url").getTextTrim());
					break;
					
				default:
					break;
				}
				subButton.setType(sub_e.element("type").getTextTrim());
				subButton.setName(sub_e.element("name").getText());
				subButtons[i] = subButton;
			}
			i++;
		}
	}
	
	public static int createMenu(String token, String menu){
		int result = 1;
		String url = Confs.instance.load(Confs.CREATE_MENU_URL).replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = dopostStr(url, menu);
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	
	public static void main(String[] args) {
		initMenu();
	}
}
