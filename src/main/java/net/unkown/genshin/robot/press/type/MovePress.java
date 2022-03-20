package net.unkown.genshin.robot.press.type;

import net.unkown.genshin.danmaku.message.type.ChatMessage;
import net.unkown.genshin.logger.PrintLogger;
import net.unkown.genshin.robot.press.Press;
import net.unkown.genshin.robot.press.PressInt;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovePress extends Press implements PressInt {
    private String command; // 初始键名
    private int count; // 初始执行次数

    /**
     * 移动按键模块 负责8向移动
     * 格式/w 或 /www 或 /w*3
     */
    public MovePress() {
        // 下方按键为八向移动
        super(new String[]{"WA", "AW", "WD", "DW", "SA", "AS", "SD", "DS", "W", "S", "A", "D"});
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

        int delay = 500; // 按键放开延迟时间
        List<Integer> keyList = new ArrayList<>(); // 需要按下的键

        switch (command) {
            case "W" -> keyList.add(KeyEvent.VK_W);
            case "S" -> keyList.add(KeyEvent.VK_S);
            case "A" -> keyList.add(KeyEvent.VK_A);
            case "D" -> keyList.add(KeyEvent.VK_D);

            case "WA", "AW" -> {
                keyList.add(KeyEvent.VK_W);
                keyList.add(KeyEvent.VK_A);
            }
            case "WD", "DW" -> {
                keyList.add(KeyEvent.VK_W);
                keyList.add(KeyEvent.VK_D);
            }
            case "SA", "AS" -> {
                keyList.add(KeyEvent.VK_S);
                keyList.add(KeyEvent.VK_A);
            }
            case "SD", "DS" -> {
                keyList.add(KeyEvent.VK_S);
                keyList.add(KeyEvent.VK_D);
            }
        }
        // 执行模拟按键
        if (!keyList.isEmpty()) {
            pressKey(1, delay * count, keyList);
        }
    }
}
