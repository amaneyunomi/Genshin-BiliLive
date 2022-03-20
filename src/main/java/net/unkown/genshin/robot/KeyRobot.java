package net.unkown.genshin.robot;

import net.unkown.genshin.danmaku.message.type.ChatMessage;
import net.unkown.genshin.robot.press.PressInt;
import net.unkown.genshin.robot.press.type.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class KeyRobot {
    private static Robot robot; // 模拟键鼠
    private static List<PressInt> pressList; // 存储按键

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        initPress(); // 初始化按键
    }

    private static void initPress() {
        pressList = new ArrayList<>();
        // 需要判断的按键
        pressList.add(new MovePress()); // 移动
        pressList.add(new SkillPress()); // 元素战技/爆发
        pressList.add(new PickPress()); // 拾取交互
        pressList.add(new TogglePress()); // 切换角色
        pressList.add(new MousePress()); // 鼠标点击
        pressList.add(new FovPress()); // 视角改变
        pressList.add(new SelectPress()); // 选项选择
        pressList.add(new FlyPress()); // 飞行
    }

    public static void inputMessage(ChatMessage message) {
        // 匹配每个按键规则
        for (PressInt press : pressList) {
            if (press.exec(message)) {
                break;
            }
        }

    }

    public static Robot getRobot() {
        return robot;
    }
}
