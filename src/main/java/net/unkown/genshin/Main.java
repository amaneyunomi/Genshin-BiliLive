package net.unkown.genshin;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.Shell32;
import com.sun.jna.platform.win32.ShellAPI;
import com.sun.jna.win32.W32APIOptions;
import net.unkown.genshin.danmaku.message.DankamuService;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        getUAC(); // 获取UAC权限

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("By:UnKown");

        System.out.print("请输入B站房间号: ");
        String roomID = in.readLine();

        System.out.println("正在连接至直播间, 请稍等..");
        new DankamuService(roomID).start();

        //runPointTimer();
    }

    /**
     * 检测输出屏幕以及鼠标的数据
     */
    private static void runPointTimer() {
        // 发送屏幕信息
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Toolkit t = Toolkit.getDefaultToolkit();
                Dimension size = t.getScreenSize();

                Point p = MouseInfo.getPointerInfo().getLocation();
                System.out.println("屏幕分辨率: " + size.width + "*" + size.height);
                System.out.println("坐标X: " + p.x + " 坐标Y: " + p.y);

                double zoom = (double) 1920 / size.width;
                System.out.println("缩放比例: " + zoom);
                System.out.println("X * " + zoom + ": " + p.x * zoom + "  Y * " + zoom + ": " + p.y * zoom);
            }
        }, 0L, 5 * 1000L);
    }

    /**
     * 获取UAC权限(管理员身份)
     * 因为游戏内移动鼠标等操作需要管理员权限
     */
    private static void getUAC() {
        if(!checkPrivileges()){
            //Path to this jar (might not work inside ide...
            String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String decodedPath;

            decodedPath = URLDecoder.decode(jarPath, StandardCharsets.UTF_8);
            decodedPath = decodedPath.substring(1);
            //Command for shell
            String CMD = System.getProperty("java.home") + "\\bin\\java";//java path...
            String argsForCMD = "-jar " + "\"" + decodedPath + "\"";//Arguments required to execute the jar file.
            JOptionPane.showMessageDialog(null, "我们需要获取UAC权限(管理员身份)\n请点击确定后同意此次请求!!");//Optional...
            //Execute as admin
            Shell32 INSTANCE = Native.load("shell32", Shell32.class, W32APIOptions.UNICODE_OPTIONS);//loading the system shell (kind of similar to the Command Prompt)
            ShellAPI.SHELLEXECUTEINFO shellinfo = new ShellAPI.SHELLEXECUTEINFO();
            //Info for execution:
            shellinfo.lpFile = CMD;
            shellinfo.lpParameters = argsForCMD;
            //Those infos dont matter much, except maybe disabling the dark windows which appears for a sec...
            shellinfo.nShow = 10;
            shellinfo.fMask = 0x00000040;
            shellinfo.lpVerb = "runas";
            boolean result = INSTANCE.ShellExecuteEx(shellinfo);
            if (!result)
            {
                int lastError = Kernel32.INSTANCE.GetLastError();
                String errorMessage = Kernel32Util.formatMessageFromLastErrorCode(lastError);
                throw new RuntimeException("Error performing elevation: " + lastError + ": " + errorMessage + " (apperror=" + shellinfo.hInstApp + ")");
            }
            System.exit(0);
        }
    }

    /**
     * 检测用户是否以管理员身份运行
     * @return 是否拥有UAC权限
     */
    private static boolean checkPrivileges() {
        File testPriv = new File("C:\\Program Files\\");
        if (!testPriv.canWrite()) return false;
        File fileTest = null;
        try {
            fileTest = File.createTempFile("test", ".dll", testPriv);
        } catch (IOException e) {
            return false;
        } finally {
            if (fileTest != null) {
                fileTest.delete();
            }
        }
        return true;
    }

}
