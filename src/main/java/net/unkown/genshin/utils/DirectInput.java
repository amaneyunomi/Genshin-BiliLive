package net.unkown.genshin.utils;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

/**
 * This class can help you to simulate key or mouse events using DirectInput
 * class require jna-platform-5.8.0
 */

@SuppressWarnings("unused")
public class DirectInput {
    private static final String KEYBOARD_INPUT = "ki";
    private static final String MOUSE_INPUT = "mi";
    public static final int KEY_EVENT_KEY_DOWN = 0;
    public static final int KEY_EVENT_KEY_UP = 2;
    public static final int MOUSE_EVENT_MOVE = 1;
    public static final int MOUSE_EVENT_LEFT_DOWN = 2;
    public static final int MOUSE_EVENT_LEFT_UP = 4;
    public static final int MOUSE_EVENT_MIDDLE_DOWN = 32;
    public static final int MOUSE_EVENT_MIDDLE_UP = 64;
    public static final int MOUSE_EVENT_RIGHT_DOWN = 8;
    public static final int MOUSE_EVENT_RIGHT_UP = 16;

    /**
     * Check if specific key is pressed
     *
     * @param keyCode java.awt.event.KeyEvent
     * @return true if key is pressed
     */
    public static boolean isKeyPressed(int keyCode) {
        return (0x1 & (User32.INSTANCE.GetAsyncKeyState(keyCode) >> (Short.SIZE - 1))) != 0;
    }

    /**
     * Press specific key
     *
     * @param keyCode java.awt.event.KeyEvent
     */
    public static void pressKey(int keyCode) {
        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        input.input.setType(KEYBOARD_INPUT);
        input.input.ki.wScan = new WinDef.WORD(0);
        input.input.ki.time = new WinDef.DWORD(0);
        input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
        input.input.ki.wVk = new WinDef.WORD(keyCode);
        input.input.ki.dwFlags = new WinDef.DWORD(KEY_EVENT_KEY_DOWN);
        User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }

    /**
     * Release specific key
     *
     * @param keyCode java.awt.event.KeyEvent
     */
    public static void releaseKey(int keyCode) {
        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        input.input.setType(KEYBOARD_INPUT);
        input.input.ki.wScan = new WinDef.WORD(0);
        input.input.ki.time = new WinDef.DWORD(0);
        input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
        input.input.ki.wVk = new WinDef.WORD(keyCode);
        input.input.ki.dwFlags = new WinDef.DWORD(KEY_EVENT_KEY_UP);
        User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }

    /**
     * Move mouse to specific position on screen
     *
     * @param x position on screen
     * @param y position on screen
     */
    public static void mouseMove(int x, int y) {
        mouseInput(x, y, MOUSE_EVENT_MOVE);
    }

    /**
     * Click left button at specified position on screen
     *
     * @param x position on screen
     * @param y position on screen
     */
    public static void mouseLeftClick(int x, int y) {
        mouseInput(x, y, MOUSE_EVENT_LEFT_DOWN);
        mouseInput(x, y, MOUSE_EVENT_LEFT_UP);
    }

    /**
     * Click right button at specified position on screen
     *
     * @param x position on screen
     * @param y position on screen
     */
    public static void mouseRightClick(int x, int y) {
        mouseInput(x, y, MOUSE_EVENT_RIGHT_DOWN);
        mouseInput(x, y, MOUSE_EVENT_RIGHT_UP);
    }

    /**
     * Click middle button at specified position on screen
     *
     * @param x position on screen
     * @param y position on screen
     */
    public static void mouseMiddleClick(int x, int y) {
        mouseInput(x, y, MOUSE_EVENT_MIDDLE_DOWN);
        mouseInput(x, y, MOUSE_EVENT_MIDDLE_UP);
    }

    private static void mouseInput(int x, int y, int flag) {
        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_MOUSE);
        input.input.setType(MOUSE_INPUT);
        if (x != -1)
            input.input.mi.dx = new WinDef.LONG(x);
        if (y != -1)
            input.input.mi.dy = new WinDef.LONG(y);
        input.input.mi.time = new WinDef.DWORD(0);
        input.input.mi.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
        input.input.mi.dwFlags = new WinDef.DWORD(flag);
        User32.INSTANCE.SendInput(new WinDef.DWORD(1), new WinUser.INPUT[]{input}, input.size());
    }
}
