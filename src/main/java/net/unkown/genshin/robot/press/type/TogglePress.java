package net.unkown.genshin.robot.press.type;

import net.unkown.genshin.danmaku.message.type.ChatMessage;
import net.unkown.genshin.logger.PrintLogger;
import net.unkown.genshin.robot.press.Press;
import net.unkown.genshin.robot.press.PressInt;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TogglePress extends Press implements PressInt {
    private String command; // 初始键名

    /**
     * 切换角色按键模块 支持1-5
     * 格式/1 切换第一名角色...
     */
    public TogglePress() {
        // 下方按键为切换角色
        super(new String[]{"1", "2", "3", "4", "5"});
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
            command = containsKey(message.substring(index, Math.min(message.length(), index + maxKeyLength())));

            return command != null;
        }
        return false;
    }

    private void run() {
        int delay = 100; // 按键放开延迟时间
        List<Integer> keyList = new ArrayList<>(); // 需要按下的键

        switch (command) {
            case "1" -> keyList.add(KeyEvent.VK_1);
            case "2" -> keyList.add(KeyEvent.VK_2);
            case "3" -> keyList.add(KeyEvent.VK_3);
            case "4" -> keyList.add(KeyEvent.VK_4);
            case "5" -> keyList.add(KeyEvent.VK_5);
        }
        // 执行模拟按键
        if (!keyList.isEmpty()) {
            pressKey(1, delay, keyList);
        }
    }
}
