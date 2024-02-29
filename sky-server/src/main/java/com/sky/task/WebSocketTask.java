package com.sky.task;


import com.sky.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author liudo
 * @version 1.0
 * @project sky-take-out
 * @date 2023/10/8 12:07:23
 */

public class WebSocketTask {


    private WebSocketServer webSocketServer;

    /**
     * 每5s中向客户端发送消息
     */
    @Scheduled(cron = "0/5 * * * * ? ")
    public void testWebSocket() {
        webSocketServer.sendToAllClient("哈哈哈哈");
    }
}
