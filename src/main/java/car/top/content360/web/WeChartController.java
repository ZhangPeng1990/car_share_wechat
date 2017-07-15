package car.top.content360.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.content360.services.AccessTokenService;
import top.content360.services.MessageService;
import top.content360.util.Log;
import top.content360.util.RequestUtil;

@Controller
@RequestMapping("/weixin")
public class WeChartController extends BaseController{

	@Autowired
	MessageService messageService;
	
	@Autowired
	AccessTokenService tokenService;
	
	@RequestMapping("/io")
	public String io(HttpServletRequest request, HttpServletResponse response) {

		Log.log("request from : " + RequestUtil.getRemoteHost(request));
		Log.log("query uri is : " + request.getQueryString());
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		
		messageService.handMessage(request, response);
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/updateMenu")
	public String updateMenu(HttpServletRequest request, HttpServletResponse response) {

		Log.log("request from : " + RequestUtil.getRemoteHost(request));
		Log.log("query uri is : " + request.getQueryString());
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		
		boolean result = messageService.updateMenu(request, response);
		
		return result ? "update success" : "update failed";
	}
}
