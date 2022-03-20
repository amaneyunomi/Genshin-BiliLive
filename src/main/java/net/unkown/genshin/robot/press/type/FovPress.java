package net.unkown.genshin.robot.press.type;

import net.unkown.genshin.danmaku.message.type.ChatMessage;
import net.unkown.genshin.logger.PrintLogger;
import net.unkown.genshin.robot.press.Press;
import net.unkown.genshin.robot.press.PressInt;

import java.awt.event.InputEvent;
import java.util.Locale;

public class FovPress extends Press implements PressInt {
    private String command; // 初始键名
    private int count; // 初始执行次数

    /**
     * 视角改变按键模块 负责移动鼠标 支持八向移动
     * 格式/l 向左 或 /r 向右 或 /u 向上 或 /g 向下
     * /大 滚轮向上 /小 滚轮向下 支持多次
     */
    public FovPress() {
        // 下方按键为视角改变
        super(new String[]{"拖UL", "拖LU", "拖UR", "拖RU", "拖GL", "拖LG", "拖GR", "拖RG", "拖L", "拖R", "拖U", "拖G", "LL", "RR", "UU", "GG", "UL", "LU", "UR", "RU", "GL", "LG", "GR", "RG", "L", "R", "U", "G", "居中", "放大", "缩小"});
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
        switch (command) {
            case "L", "R", "U", "G", "UL", "LU", "UR", "RU", "GL", "LG", "GR", "RG" -> {
                count = Math.min(count, 10); // 设置最大执行次数

                int range = count * 10; // 循环次数
                int speed = 5; // 移动的距离

                switch (command) {
                    case "L" -> mouseMove(range, -speed, 0);
                    case "R" -> mouseMove(range, speed, 0);
                    case "U" -> mouseMove(range, 0, -speed);
                    case "G" -> mouseMove(range, 0, speed);

                    case "UL", "LU" -> mouseMove(range, -speed, -speed);
                    case "UR", "RU" -> mouseMove(range, speed, -speed);
                    case "GL", "LG" -> mouseMove(range, -speed, speed);
                    case "GR", "RG" -> mouseMove(range, speed, speed);
                }
            }

            case "拖L", "拖R", "拖U", "拖G", "拖UL", "拖LU", "拖UR", "拖RU", "拖GL", "拖LG", "拖GR", "拖RG" -> {
                count = Math.min(count, 5); // 设置最大执行次数

                int range = count * 10; // 循环次数
                int speed = 15; // 移动的距离
                int key = InputEvent.BUTTON1_DOWN_MASK; // 键码

                switch (command) {
                    case "拖L" -> mouseDragMove(range, speed, 0, key);
                    case "拖R" -> mouseDragMove(range, -speed, 0, key);
                    case "拖U" -> mouseDragMove(range, 0, speed, key);
                    case "拖G" -> mouseDragMove(range, 0, -speed, key);

                    case "拖UL", "拖LU" -> mouseDragMove(range, speed, speed, key);
                    case "拖UR", "拖RU" -> mouseDragMove(range, -speed, speed, key);
                    case "拖GL", "拖LG" -> mouseDragMove(range, speed, -speed, key);
                    case "拖GR", "拖RG" -> mouseDragMove(range, -speed, -speed, key);
                }
            }

            case "LL", "RR", "UU", "GG" -> {
                count = Math.min(count, 3); // 设置最大执行次数

                int range = count * 10; // 循环次数
                int speed = 100; // 移动的距离

                switch (command) {
                    case "LL" -> mouseMove(range, -speed, 0);
                    case "RR" -> mouseMove(range, speed, 0);
                    case "UU" -> mouseMove(range, 0, -speed);
                    case "GG" -> mouseMove(range, 0, speed);
                }
            }

            case "居中" -> {
                count = Math.min(count, 1); // 设置最大执行次数

                mouseCenter(); // 居中鼠标
            }

            case "放大", "缩小" -> {
                count = Math.min(count, 10); // 设置最大执行次数

                int range = count * 10; // 缩放的距离

                switch (command) {
                    case "放大" -> mouseWheel(range, -1);
                    case "缩小" -> mouseWheel(range, 1);
                }
            }

        }
    }
}
