package net.unkown.genshin.logger;

import org.apache.log4j.Logger;
import org.fusesource.jansi.AnsiConsole;

public class PrintLogger {
    static Logger chatLog = Logger.getLogger("Chat"); // 用户聊天
    static Logger pressLog = Logger.getLogger("Press"); // 按键执行

    static Logger traceLog = Logger.getLogger("Trace"); // 调试测试
    static Logger connectLog = Logger.getLogger("Connect"); // 连接信息

    public static void chatLogger(String log) {
        AnsiConsole.systemInstall();
        chatLog.info(log);
        AnsiConsole.systemUninstall();
    }

    public static void pressLogger(String log) {
        AnsiConsole.systemInstall();
        pressLog.warn(log);
        AnsiConsole.systemUninstall();
    }

    public static void traceLogger(String log) {
        AnsiConsole.systemInstall();
        traceLog.trace(log);
        AnsiConsole.systemUninstall();
    }

    public static void connectLogger(String log) {
        AnsiConsole.systemInstall();
        connectLog.debug(log);
        AnsiConsole.systemUninstall();
    }
}
