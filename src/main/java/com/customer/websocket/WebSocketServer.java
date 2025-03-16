package com.customer.websocket;

import com.alibaba.fastjson.JSON;
import com.customer.entity.Chat;
import com.customer.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket服务
 */
@Component
@ServerEndpoint("/ws/{userId}/{role}")
public class WebSocketServer {

    private static ChatService chatService;
    
    @Autowired
    public void setChatService(ChatService chatService) {
        WebSocketServer.chatService = chatService;
    }

    // 存放会话对象，key是用户ID，value是会话对象
    private static Map<String, Session> userSessions = new ConcurrentHashMap<>();
    
    // 存放客服会话对象，key是客服ID，value是会话对象
    private static Map<String, Session> customerSessions = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId, @PathParam("role") String role) {
        if ("customer".equals(role)) {
            customerSessions.put(userId, session);
        } else {
            userSessions.put(userId, session);
        }
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, @PathParam("userId") String userId, @PathParam("role") String role) {
        // 解析消息
        Chat chat = JSON.parseObject(message, Chat.class);
        
        // 保存消息到数据库
        chatService.saveChat(chat);
        
        // 转发消息
        if ("customer".equals(role)) {
            // 客服发送给用户
            sendToUser(chat.getCustomerId().toString(), message);
        } else {
            // 用户发送给客服
            sendToCustomer(chat.getCustomerId().toString(), message);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("userId") String userId, @PathParam("role") String role) {
        if ("customer".equals(role)) {
            customerSessions.remove(userId);
        } else {
            userSessions.remove(userId);
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 发送消息给用户
     */
    public void sendToUser(String userId, String message) {
        Session session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送消息给客服
     */
    public void sendToCustomer(String customerId, String message) {
        Session session = customerSessions.get(customerId);
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
