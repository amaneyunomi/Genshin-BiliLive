package net.unkown.genshin.robot.press;

import net.unkown.genshin.robot.KeyRobot;
import net.unkown.genshin.utils.DirectInput;
import net.unkown.genshin.utils.Utils;

import java.awt.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Press {
    private final String[] keyWord; // 按键表

    /**
     * 所有按键的父类
     * 用于存放关于按键的方法
     *
     * @param keyWord 按键表
     */
    public Press(String[] keyWord) {
        this.keyWord = keyWord;
    }

    /**
     * 获取指定字符串出现的次数
     *
     * @param srcText  源字符串
     * @param findText 要查找的字符串
     * @return 要查找的字符串出现的次数
     */
    public int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }

    /**
     * 通过按键表查找指令的键值
     * 可以识别多个键值的命令
     *
     * @param command 需要查找键值的按键
     * @return 对应的键值
     */
    public String containsKey(String command) {
        for (String s : keyWord) {
            if (command.contains(s)) {
                return s;
            }
        }
        return null;
    }

    /**
     * 鼠标某键按下后移动鼠标
     *
     * @param count 需要批量执行的次数
     * @param x     需要往左移动的距离px
     * @param y     需要往右移动的距离px
     * @param key   需要按下的鼠标键
     */
    public void mouseDragMove(int count, int x, int y, int key) {
        Robot robot = KeyRobot.getRobot();

        robot.mousePress(key);

        for (int i = 0; i < count; i++) {
            Point p = MouseInfo.getPointerInfo().getLocation(); // 鼠标位置
            robot.mouseMove(p.x + x, p.y + y);
            robot.delay(5);
        }

        robot.mouseRelease(key);
    }

    /**
     * 移动鼠标后按下某键
     *
     * @param x   移动到X位置
     * @param y   移动到Y位置
     * @param key 需要按下的鼠标键
     */
    public void mouseMoveKey(int x, int y, int key) {
        Robot robot = KeyRobot.getRobot();

        robot.mouseMove(x, y);

        robot.mousePress(key);
        robot.delay(10);
        robot.mouseRelease(key);
    }

    /**
     * 模拟鼠标滑动滚轮
     *
     * @param count    需要批量执行的次数
     * @param wheelAmt 滑动的方向以及幅度
     */
    public void mouseWheel(int count, int wheelAmt) {
        Robot robot = KeyRobot.getRobot();

        for (int i = 0; i < count; i++) {
            robot.mouseWheel(wheelAmt);
            robot.delay(10);
        }
    }

    /**
     * 模拟鼠标移动到中心位置
     */
    public void mouseCenter() {
        Robot robot = KeyRobot.getRobot();

        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension size = t.getScreenSize();

        robot.mouseMove(size.width / 2, size.height / 2);
    }

    /**
     * 模拟鼠标移动到某个坐标
     *
     * @param count 需要批量执行的次数
     * @param x     需要往左移动的距离px
     * @param y     需要往右移动的距离px
     */
    public void mouseMove(int count, int x, int y) {
        for (int i = 0; i < count; i++) {
            DirectInput.mouseMove(x, y);
            Utils.sleep(1);
        }
    }


    /**
     * 模拟鼠标按下某个键后延迟一会再松开
     *
     * @param count   需要批量执行的次数
     * @param delay   按下后延迟的时间
     * @param keyList 需要同时按下或松开的键
     */
    public void pressMouse(int count, int delay, List<Integer> keyList) {
        Robot robot = KeyRobot.getRobot();

        for (int i = 0; i < count; i++) {
            for (int k : keyList) {
                robot.mousePress(k);
                robot.delay(10);
            }
            robot.delay(delay);
            for (int k : keyList) {
                robot.mouseRelease(k);
                robot.delay(10);
            }
            robot.delay(10);
        }
    }

    /**
     * 模拟鼠标按下某个键后延迟一会再松开
     *
     * @param count   需要批量执行的次数
     * @param delay   按下后延迟的时间
     * @param keyList 需要同时按下或松开的键
     */
    public void pressMouse(int count, int delay, int... keyList) {
        Robot robot = KeyRobot.getRobot();

        for (int i = 0; i < count; i++) {
            for (int k : keyList) {
                robot.mousePress(k);
                robot.delay(10);
            }
            robot.delay(delay);
            for (int k : keyList) {
                robot.mouseRelease(k);
                robot.delay(10);
            }
            robot.delay(10);
        }
    }

    /**
     * 模拟键盘按下某个键后延迟一会再松开
     *
     * @param count   需要批量执行的次数
     * @param delay   按下后延迟的时间
     * @param keyList 需要同时按下或松开的键
     */
    public void pressKey(int count, int delay, List<Integer> keyList) {
        Robot robot = KeyRobot.getRobot();

        for (int i = 0; i < count; i++) {
            for (int k : keyList) {
                robot.keyPress(k);
                robot.delay(10);
            }
            robot.delay(delay);
            for (int k : keyList) {
                robot.keyRelease(k);
                robot.delay(10);
            }
            robot.delay(10);
        }
    }

    /**
     * 模拟键盘按下某个键后延迟一会再松开
     *
     * @param count   需要批量执行的次数
     * @param delay   按下后延迟的时间
     * @param keyList 需要同时按下或松开的键
     */
    public void pressKey(int count, int delay, int... keyList) {
        Robot robot = KeyRobot.getRobot();

        for (int i = 0; i < count; i++) {
            for (int k : keyList) {
                robot.keyPress(k);
                robot.delay(10);
            }
            robot.delay(delay);
            for (int k : keyList) {
                robot.keyRelease(k);
                robot.delay(10);
            }
            robot.delay(10);
        }
    }

    /**
     * 取出键表中最大的键值长度
     * @return 最大键值的长度
     */
    public int maxKeyLength() {
        int maxLength = 0;

        for (String key : keyWord) {
            if (key.length() > maxLength){
                maxLength = key.length();
            }
        }
        return maxLength;
    }
}
