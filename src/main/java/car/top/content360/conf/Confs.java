package car.top.content360.conf;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import top.content360.exceptions.SystemException;

public class Confs {
	
	public static Confs instance = new Confs();
	private final String confsFile = "system_conf.properties";
	
	private Confs(){
		Resource resource = new ClassPathResource(confsFile);
		props = new Properties();
		try {
			props.load(resource.getInputStream());
		} catch (IOException e) {
			throw new SystemException("can not load config file : " + this.confsFile);
		}  
	}

	private Properties props;
	 
	public static final String APPID = "wechat.appid";
	public static final String APPSECRET = "wechat.appsecret";
	public static final String TOKEN = "wechat.token";
	
	public static final String CREATE_MENU_URL = "wechat.create_menu_url";
	public static final String ACCESS_TOKEN_URL = "wechat.access_token_url";
	
	@Bean
	public String load(String key){
		if(null == props){
			throw new SystemException("can not load config file : " + this.confsFile);
		}
		return props.getProperty(key);
	}
}
