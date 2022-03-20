package net.unkown.genshin.robot.press.type;

import net.unkown.genshin.danmaku.message.type.ChatMessage;
import net.unkown.genshin.logger.PrintLogger;
import net.unkown.genshin.robot.press.Press;
import net.unkown.genshin.robot.press.PressInt;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Locale;

public class SelectPress extends Press implements PressInt {
    private String command; // 初始键名

    /**
     * 选项选择按键模块 支持一到六
     * 格式/一 选择从下往上的第一个选项
     */
    public SelectPress() {
        // 下方按键为选项选择
        super(new String[]{"一", "二", "三", "四", "五", "六", "确"});
    }

    @Override
    public boolean exec(ChatMessage message) {
        String msg = message.getMsg().toUpperCase(Locale.ROOT); // 聊天信息

        // 检测格式后执行按键
        if (isCheck(msg)) {
            PrintLogger.pressLogger(message.getUid() + ":" + message.getName() + " 执行 " + command + "* 1");
            run();
            return true;
        }
        return false;
    }

    private boolean isCheck(String message) {
        if (message.startsWith("/")) {
            int index = message.indexOf("/") + 1;
            command = containsKey(message.substring(index, Math.min(message.length(), index + 1)));

            return command != null;
        }
        return false;
    }

    private void run() {
        int x = 0, y = 0; // 需要按的位置

        // 获取电脑分辨率
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension size = t.getScreenSize();
        // 分辨率缩放比例
        double zoom = (double) 1920 / size.width;

        switch (command) {
            case "一" -> {
                x = 1300;
                y = 800;
            }
            case "二" -> {
                x = 1300;
                y = 725;
            }
            case "三" -> {
                x = 1300;
                y = 650;
            }
            case "四" -> {
                x = 1300;
                y = 575;
            }
            case "五" -> {
                x = 1300;
                y = 500;
            }
            case "六" -> {
                x = 1300;
                y = 425;
            }

            case "确" -> {
                x = 1470;
                y = 1000;
            }
        }
        // 模拟鼠标移动并单击
        mouseMoveKey((int) (x / zoom), (int) (y / zoom), InputEvent.BUTTON1_DOWN_MASK);
    }
}
