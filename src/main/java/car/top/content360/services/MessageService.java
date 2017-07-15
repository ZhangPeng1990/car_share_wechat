package car.top.content360.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MessageService {

	final String SERVICE_NAME = "messageService";
	
	/**
	 * 消息处理， 消息回复，记录等操作
	 * @param request
	 * @param response
	 */
	public void handMessage(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 更新公众号菜单接口，返回true 代表更新成功
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean updateMenu(HttpServletRequest request, HttpServletResponse response);
	
}
