package car.top.content360.services.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;
import top.content360.entities.MessagePojo;
import top.content360.po.AccessToken;
import top.content360.po.Message;
import top.content360.po.TextMessage;
import top.content360.repositorys.MessagePojoRepository;
import top.content360.services.AccessTokenService;
import top.content360.services.MessageService;
import top.content360.util.CheckUtil;
import top.content360.util.Log;
import top.content360.util.MessageUtil;
import top.content360.util.WeixinUtil;

@Service(MessageService.SERVICE_NAME)
public class MessageServiceImpl implements MessageService{

	@Autowired
	private MessagePojoRepository msessageRepository; 
	
	@Autowired
	private AccessTokenService tokenService;
	
	@Override
	public void handMessage(HttpServletRequest request, HttpServletResponse response) {
		
		// 获取请求方法， GET POST
		String method = request.getMethod();
		if("GET".equals(method)){
			Log.log("GET method running...");
			authorization(request, response);
		}else if("POST".equals(method)){
			Log.log("POST method running...");
			analyzeMessage(request, response);
		}
	}
	
	/**
	 * 一条消息进来时进行分析
	 * @param request
	 * @param response
	 */
	private void analyzeMessage(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> messageMap = MessageUtil.xml2Map(request);
		
		TextMessage message = null;
		
		String msgType = messageMap.get("MsgType");
		Log.log("msgType is : " + msgType);
		// 消息发送者的ID
		String fromUserName = messageMap.get("FromUserName");
		// 本微信号的ID
		String myUserName = messageMap.get("ToUserName");
		
		if("event".equals(msgType)){
			String eventType = messageMap.get("Event");
			// 用户关注了公众号事件
			if("subscribe".equals(eventType)){
				message = new TextMessage();
				message.setToUserName(fromUserName);
				message.setFromUserName(myUserName);
				message.setCreateTime(new Date().getTime());
				message.setMsgType("text");
				message.setContent("欢迎关注本账号!");
				
				//对关注者发送消息
				this.replyMessage(request, response, message);
				this.saveMessage(message);
			}else if("unsubscribe".equals(eventType)){
				// 用户关注了公众号事件
				message = new TextMessage();
				message.setToUserName(myUserName);
				message.setFromUserName(fromUserName);
				message.setCreateTime(new Date().getTime());
				message.setMsgType("text");
				message.setContent("欢迎关注本账号!");
				this.saveMessage(message);
			}
		}else if("text".equals(msgType)){
			
			message = new TextMessage();
			message.setToUserName(fromUserName);
			message.setFromUserName(myUserName);
			message.setCreateTime(new Date().getTime());
			message.setMsgType("text");
			message.setContent("您发送的内容是：" + messageMap.get("Content"));
			
			//对关注者发送消息
			this.replyMessage(request, response, message);
			this.saveMessage(message);
		}
	}
	
	private void saveMessage(Message message) {
		MessagePojo pojo = BeanUtils.toMessagePojo(message);
		msessageRepository.save(pojo);
	}

	private boolean replyMessage(HttpServletRequest request, HttpServletResponse response, Message message) {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		boolean success = true;
		String messageXml = MessageUtil.message2Xml(message);
		
		writer.println(messageXml);
		writer.close();
		return success;
	}

	
	private void authorization(HttpServletRequest request, HttpServletResponse response) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.println(echostr);
		}
	}

	@Override
	public boolean updateMenu(HttpServletRequest request, HttpServletResponse response) {
		
		boolean success = false;
		AccessToken token = tokenService.getToken(request);
		
		String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
		int result = WeixinUtil.createMenu(token.getToken(), menu);
		
		if(result == 0){
			Log.log("微信菜单更新成功");
			success = true;
		}
		
		return success;
	}
}
