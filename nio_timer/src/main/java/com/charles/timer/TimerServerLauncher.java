package com.charles.timer;

import com.charles.timer.services.MultiplexerTimeServer;

/**
 * @Description 时间服务器启动类
 * @Date 2019/11/11 11:16 下午
 * @Author Charles
 * @Version 1.0.0
 **/
public class TimerServerLauncher {

    public static void main(String[] args) {
        int port = 8080;
        if(args != null && args.length > 0){
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer,"NIO-MultiplexerTimeServer-001").start();;
    }
}
