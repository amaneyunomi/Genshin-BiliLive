package net.unkown.genshin.danmaku.service;

import com.alibaba.fastjson.JSONException;
import net.unkown.genshin.logger.PrintLogger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.zip.DataFormatException;

public class WebSocket extends WebSocketClient {

    /**
     * 通过 WebSocket 获取直播弹幕之类的数据
     *
     * @param serverURI 目标服务器地址 wss://
     */
    public WebSocket(String serverURI) {
        super(URI.create(serverURI));
    }

    @Override
    public void onOpen(ServerHandshake shake) {
        PrintLogger.connectLogger("已成功连接B站直播间!");
    }

    @Override
    public void onMessage(ByteBuffer message) {
        try {
            MessageHandle.message(message);
        } catch (DataFormatException e) {
            e.printStackTrace();
        } catch (JSONException ignored) {
        }
    }

    @Override
    public void onClose(int paramInt, String paramString, boolean paramBoolean) {
        PrintLogger.connectLogger("已断开B站直播间的连接!");
        new Thread(() -> {
            try {
                reconnectBlocking(); // 自动重连
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onMessage(String message) {
    }
}
