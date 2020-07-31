package com.example.hello.controller;


import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;


/**
 * @author zhoukai
 * @date 2020/7/30 16:51
 */
@ServerEndpoint("/connectWebSocket/{userId}")
@Component
public class WebSocketServer {

    private static final Logger LOG = LogManager.getLogger(WebSocketServer.class);
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收userId
     */
    private String userId;

    /**
     * 用户登录时会调用此方法
     *
     * @param session 会话
     * @param userId  用户id
     * @author zhoukai
     * @date 2020/7/31 13:43
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            //加入set中
            webSocketMap.put(userId, this);
        } else {
            //加入set中
            webSocketMap.put(userId, this);
            //在线数加1
            addOnlineCount();
        }

        LOG.info("用户[" + userId + "]已连接,当前在线人数为: " + getOnlineCount());

        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            LOG.error("用户:" + userId + ",网络异常!!!!!!");
        }
    }

    /**
     * 用户退出时会调用此方法
     *
     * @author zhoukai
     * @date 2020/7/31 13:45
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(userId)) {
            //从set中删除
            webSocketMap.remove(userId);
            subOnlineCount();
        }
        LOG.info("用户[" + userId + "]退出,当前在线人数为:" + getOnlineCount());
    }


    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 会话
     * @author zhoukai
     * @date 2020/7/31 13:45
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        LOG.info("用户id:" + userId + ",报文:" + message);
        //可以群发消息
        //消息保存到数据库、redis
        if (StringUtils.isNotBlank(message)) {
            try {
                //解析发送的报文
                JSONObject jsonObject = JSONObject.parseObject(message);
                //追加发送人(防止串改)
                jsonObject.put("fromUserId", this.userId);
                String toUserId = jsonObject.getString("toUserId");
                //传送给对应toUserId用户的webSocket
                if (StringUtils.isNotBlank(toUserId) && webSocketMap.containsKey(toUserId)) {
                    webSocketMap.get(toUserId).sendMessage(jsonObject.getString("message"));
                } else {
                    //用户不在这个服务器上，发送到mysql或者redis
                    LOG.error("请求的userId:" + toUserId + " 不在该服务器上");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param session
     * @param error
     * @author zhoukai
     * @date 2020/7/31 13:45
     */
    @OnError
    public void onError(Session session, Throwable error) {
        LOG.error("用户[" + this.userId + "]错误,原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     *
     * @param message
     * @author zhoukai
     * @date 2020/7/31 13:48
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发消息
     *
     * @param message
     * @author zhoukai
     * @date 2020/7/31 14:55
     */
    public static void sendMessageAll(String message) throws IOException {
        for (WebSocketServer item : webSocketMap.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    /**
     * 发送自定义消息
     *
     * @param message
     * @param userId
     * @author zhoukai
     * @date 2020/7/31 13:48
     */
    public static void sendInfo(String message, @PathParam("userId") String userId) throws IOException {
        LOG.info("发送消息到:" + userId + ",报文:" + message);
        if (StringUtils.isNotBlank(userId) && webSocketMap.containsKey(userId)) {
            webSocketMap.get(userId).sendMessage(message);
        } else {
            LOG.error("用户 " + userId + ",不在线！");
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        onlineCount--;
    }
}
