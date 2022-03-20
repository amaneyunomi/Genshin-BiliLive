package net.unkown.genshin.danmaku.service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import net.unkown.genshin.danmaku.message.type.ChatMessage;
import net.unkown.genshin.logger.PrintLogger;
import net.unkown.genshin.robot.KeyRobot;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class MessageHandle {

    /**
     * @param message B站返回的字节集类型的数据
     * @throws DataFormatException 数据格式错误
     * @throws JSONException       屏蔽无效字符串转换JSON的错误
     */
    public static void message(ByteBuffer message) throws DataFormatException, JSONException {
        List<String> s = messageToJson(message);
        for (String s1 : s) {
            selectMsgType(s1);
        }

    }

    /**
     * 通过类型名确定消息的种类
     *
     * @param message 需要判断的消息
     * @throws JSONException 屏蔽无效字符串转换JSON的错误
     */
    private static void selectMsgType(String message) throws JSONException {
        JSONObject msgArray = JSONObject.parseObject(message);

        // 输出Debug信息
        PrintLogger.traceLogger(message);

        if (!msgArray.containsKey("cmd")) {
            return;
        }

        switch (msgArray.getString("cmd")) {
            case "DANMU_MSG" -> {
                ChatMessage chatMsg = new ChatMessage(message);

                // 输出Chat信息
                PrintLogger.chatLogger(chatMsg.getName() + ": " + chatMsg.getMsg());

                // 执行模拟键鼠判断
                KeyRobot.inputMessage(chatMsg);
            }
        }
    }

    /**
     * @param message 如果是 message 是弹幕类型，则需要解压拆分
     * @return List<message>
     * @throws DataFormatException DataFormatException
     */
    private static List<String> messageToJson(ByteBuffer message) throws DataFormatException {
        byte[] messageBytes = message.array();
        byte[] mainMessageBytes = Arrays
                .copyOfRange(messageBytes, 16, messageBytes.length);

        if (messageBytes[16] != 120) {
            return List.of(new String(mainMessageBytes, StandardCharsets.UTF_8));
        }

        // 解压缩弹幕信息
        byte[] newByte = new byte[1024 * 5];
        Inflater inflater = new Inflater();
        inflater.setInput(mainMessageBytes);
        newByte = Arrays.copyOfRange(newByte, 16, inflater.inflate(newByte));
        return splitStringToJson(new String(newByte, StandardCharsets.UTF_8));
    }

    /**
     * @param str 包含多条 message 的字符串
     * @return List<message>
     */
    private static List<String> splitStringToJson(String str) {
        List<String> result = new ArrayList<>();
        for (int i = 1, count = 1; i < str.length(); i++) {

            if (str.charAt(i) == '{') {
                count++;
            } else if (str.charAt(i) == '}') {
                count--;
            }

            if (count == 0) {
                result.add(str.substring(0, i + 1));
                int nextIndex = str.indexOf("{", i + 1);
                if (nextIndex != -1) {
                    result.addAll(splitStringToJson(str.substring(nextIndex)));
                }
                return result;
            }
        }
        return result;
    }
}
