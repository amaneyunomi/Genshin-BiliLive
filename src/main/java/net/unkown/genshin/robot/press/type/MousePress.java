package net.unkown.genshin.robot.press.type;

import net.unkown.genshin.danmaku.message.type.ChatMessage;
import net.unkown.genshin.logger.PrintLogger;
import net.unkown.genshin.robot.press.Press;
import net.unkown.genshin.robot.press.PressInt;

import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MousePress extends Press implements PressInt {
    private String command; // 初始键名
    private int count; // 初始执行次数

    /**
     * 鼠标点击按键模块 支持攻击/选取/冲刺
     * 格式/o 鼠标左键单击 或 /p 鼠标右键单击
     */
    public MousePress() {
        // 下方按键为鼠标单击
        super(new String[]{"OP", "O", "P"});
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

            return command != null;
        }
        return false;
    }

    private void run() {
        int delay = 300; // 按键放开延迟时间
        List<Integer> keyList = new ArrayList<>(); // 需要按下的键

        switch (command) {
            case "O" -> {
                count = Math.min(count, 5); // 设置最大执行次数
                // 需要执行的按键
                keyList.add(InputEvent.BUTTON1_DOWN_MASK);
            }
            case "P" -> {
                count = Math.min(count, 1); // 设置最大执行次数
                // 需要执行的按键
                keyList.add(InputEvent.BUTTON3_DOWN_MASK);
            }
            case "OP" -> {
                count = Math.min(count, 3); // 设置最大执行次数
                delay = 500; // 设置按键放开延迟时间
                // 需要执行的按键
                keyList.add(InputEvent.BUTTON1_DOWN_MASK);
            }
        }
        // 执行模拟按键
        if (!keyList.isEmpty()) {
            pressMouse(count, delay, keyList);
        }
    }
}
