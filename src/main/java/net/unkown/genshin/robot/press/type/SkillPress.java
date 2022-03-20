package net.unkown.genshin.robot.press.type;

import net.unkown.genshin.danmaku.message.type.ChatMessage;
import net.unkown.genshin.logger.PrintLogger;
import net.unkown.genshin.robot.press.Press;
import net.unkown.genshin.robot.press.PressInt;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SkillPress extends Press implements PressInt {
    private String command; // 初始键名

    /**
     * 元素战技/爆发等按键模块 支持短按和长按
     * 格式/e 战技短按 或 /ee 战技长按 或 /q 爆发短按
     */
    public SkillPress() {
        // 下方按键为元素战技/爆发以及其他
        super(new String[]{"EE", "E", "1Q", "2Q", "3Q", "4Q", "5Q", "Q", "X", "Z", "T", "V", "B", "C", "M", "J", "跳", "瞄", "元素视野", "中断挑战", "队伍配置", "好友", "返回", "回车", "切换", "冒险之证", "多人游戏", "祈愿", "纪行", "活动", "洞天宝鉴", "摆设"});
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
            case "E" -> keyList.add(KeyEvent.VK_E);
            case "Q" -> keyList.add(KeyEvent.VK_Q);

            case "1Q" -> {
                keyList.add(KeyEvent.VK_ALT);
                keyList.add(KeyEvent.VK_1);
            }
            case "2Q" -> {
                keyList.add(KeyEvent.VK_ALT);
                keyList.add(KeyEvent.VK_2);
            }
            case "3Q" -> {
                keyList.add(KeyEvent.VK_ALT);
                keyList.add(KeyEvent.VK_3);
            }
            case "4Q" -> {
                keyList.add(KeyEvent.VK_ALT);
                keyList.add(KeyEvent.VK_4);
            }
            case "5Q" -> {
                keyList.add(KeyEvent.VK_ALT);
                keyList.add(KeyEvent.VK_5);
            }

            case "EE" -> {
                delay = 1000;
                keyList.add(KeyEvent.VK_E);
            }

            case "X" -> keyList.add(KeyEvent.VK_X);
            case "Z" -> keyList.add(KeyEvent.VK_Z);
            case "T" -> keyList.add(KeyEvent.VK_T);
            case "V" -> keyList.add(KeyEvent.VK_V);
            case "B" -> keyList.add(KeyEvent.VK_B);
            case "C" -> keyList.add(KeyEvent.VK_C);
            case "M" -> keyList.add(KeyEvent.VK_M);
            case "J" -> keyList.add(KeyEvent.VK_J);

            case "跳" -> keyList.add(KeyEvent.VK_SPACE);
            case "瞄" -> keyList.add(KeyEvent.VK_R);
            case "元素视野" -> pressMouse(1, 5000, InputEvent.BUTTON2_DOWN_MASK);
            case "中断挑战" -> keyList.add(KeyEvent.VK_P);
            case "队伍配置" -> keyList.add(KeyEvent.VK_L);
            case "好友" -> keyList.add(KeyEvent.VK_O);

            case "返回" -> keyList.add(KeyEvent.VK_ESCAPE);
            case "回车" -> keyList.add(KeyEvent.VK_ENTER);

            case "切换" -> keyList.add(KeyEvent.VK_CONTROL);

            case "冒险之证" -> keyList.add(KeyEvent.VK_F1);
            case "多人游戏" -> keyList.add(KeyEvent.VK_F1);
            case "祈愿" -> keyList.add(KeyEvent.VK_F3);
            case "纪行" -> keyList.add(KeyEvent.VK_F4);
            case "活动" -> keyList.add(KeyEvent.VK_F5);
            case "洞天宝鉴" -> keyList.add(KeyEvent.VK_F6);
            case "摆设" -> keyList.add(KeyEvent.VK_F7);
        }
        // 执行模拟按键
        if (!keyList.isEmpty()) {
            pressKey(1, delay, keyList);
        }
    }
}
