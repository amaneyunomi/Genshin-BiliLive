package net.unkown.genshin.robot.press.type;

import net.unkown.genshin.danmaku.message.type.ChatMessage;
import net.unkown.genshin.logger.PrintLogger;
import net.unkown.genshin.robot.press.Press;
import net.unkown.genshin.robot.press.PressInt;

import java.awt.event.KeyEvent;
import java.util.Locale;

public class FlyPress extends Press implements PressInt {
    private String command; // 初始键名

    /**
     * 飞行按键模块
     * 格式/飞 自动向前跳下并展开风之翼
     */
    public FlyPress() {
        // 下方按键为飞行
        super(new String[]{"飞"});
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
        if ("飞".equals(command)) {
            // 执行模拟按键
            pressKey(1, 100, KeyEvent.VK_W);
            pressKey(1, 300, KeyEvent.VK_SPACE);
            pressKey(1, 100, KeyEvent.VK_SPACE);
        }

    }
}
