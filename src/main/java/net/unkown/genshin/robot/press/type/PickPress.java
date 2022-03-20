package net.unkown.genshin.robot.press.type;

import net.unkown.genshin.danmaku.message.type.ChatMessage;
import net.unkown.genshin.logger.PrintLogger;
import net.unkown.genshin.robot.press.Press;
import net.unkown.genshin.robot.press.PressInt;

import java.awt.event.KeyEvent;
import java.util.Locale;

public class PickPress extends Press implements PressInt {
    private String command; // 初始键名
    private int count; // 初始执行次数

    /**
     * 拾取交互按键模块 支持一次拾取多个
     * 格式/f 或 /f*3 或 /ff 拾取次数根据长度
     */
    public PickPress() {
        // 下方按键为拾取交互
        super(new String[]{"F"});
    }

    @Override
    public boolean exec(ChatMessage message) {
        String msg = message.getMsg().toUpperCase(Locale.ROOT); // 聊天信息

        // 检测格式后执行按键
        if (isCheck(msg)) {
            PrintLogger.pressLogger(message.getUid() + ":" + message.getName() + " 执行 " + command + "*" + count);
            run();
            return true;
        }
        return false;
    }

    private boolean isCheck(String message) {
        if (message.startsWith("/")) {
            command = containsKey(message.substring(message.indexOf("/") + 1, message.contains("*") ? message.lastIndexOf("*") : message.length()));
            if (command != null) {
                if (message.contains("*")) {
                    String temp = message.substring(message.indexOf("*") + 1);
                    if (temp.matches("[0-9]+") && temp.length() < 9) {
                        count = Integer.parseInt(temp);
                    }
                } else {
                    count = appearNumber(message, command);
                }
            }

            return command != null && count > 0;
        }
        return false;
    }

    private void run() {
        count = Math.min(count, 10); // 设置最大执行次数
        int delay = 50; // 按键放开延迟时间

        if ("F".equals(command)) {
            // 执行模拟按键
            pressKey(count, delay, KeyEvent.VK_F);
        }
    }
}
