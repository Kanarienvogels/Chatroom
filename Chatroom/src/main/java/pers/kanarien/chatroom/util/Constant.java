package pers.kanarien.chatroom.util;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import pers.kanarien.chatroom.model.po.GroupInfo;
import pers.kanarien.chatroom.model.po.UserInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述: 全局常量
 *      1. USER_TOKEN 用户认证的键，用来匹配http session中的对应userId；
 *      2. webSocketServerHandshaker表，用channelId为键，存放握手实例。用来响应CloseWebSocketFrame的请求；
 *      3. onlineUser表，用userId为键，存放在线的客户端连接上下文；
 *      4. groupInfo表，用groupId为键，存放群信息；
 *      5. userInfo表，用username为键，存放用户信息。
 * @author Kanarien 
 * @version 1.0
 * @date 2018年5月18日 下午9:17:35
 */
public class Constant {

    public static final String USER_TOKEN = "userId";
    
    public static Map<String, WebSocketServerHandshaker> webSocketHandshakerMap = 
            new ConcurrentHashMap<String, WebSocketServerHandshaker>();
    
	public static Map<String, ChannelHandlerContext> onlineUserMap = 
	        new ConcurrentHashMap<String, ChannelHandlerContext>();

	public static Map<String, GroupInfo> groupInfoMap = 
	        new ConcurrentHashMap<String, GroupInfo>();
	
	public static Map<String, UserInfo> userInfoMap = 
	        new HashMap<String, UserInfo>();
}
