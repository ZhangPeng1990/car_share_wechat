package car.top.content360.services;

import javax.servlet.http.HttpServletRequest;

import top.content360.po.AccessToken;

public interface AccessTokenService {

	final String SERVICE_NAME = "accessTokenService";
	
	/**
	 * 获取微信公众号access_token 并进行保存，如果发现本地存在有效的access_token则直接返回
	 * @param request
	 * @return
	 */
	public AccessToken getToken(HttpServletRequest request);
}
