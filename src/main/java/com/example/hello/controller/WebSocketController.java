package com.example.hello.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author zhoukai
 * @date 2020/7/30 16:56
 */
@Api(value = "WebSocketController", description = "WebSocket服务接口")
@RestController
@RequestMapping("/webSocket")
public class WebSocketController {

    @ApiOperation(value = "发送消息给指定用户")
    @RequestMapping(method = RequestMethod.GET, value = "/push/{toUserId}")
    public ResponseEntity<String> pushToWeb(String message, @PathVariable String toUserId) throws IOException {
        WebSocketServer.sendInfo(message, toUserId);
        return ResponseEntity.ok("MSG SEND SUCCESS");
    }

    @ApiOperation(value = "群发消息")
    @RequestMapping(method = RequestMethod.GET, value = "/pushToAll")
    public ResponseEntity<String> pushToWeb(String message) throws IOException {
        WebSocketServer.sendMessageAll(message);
        return ResponseEntity.ok("MSG SEND SUCCESS");
    }
}
