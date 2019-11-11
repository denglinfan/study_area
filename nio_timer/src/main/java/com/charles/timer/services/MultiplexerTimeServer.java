package com.charles.timer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @Description TODO
 * @Date 2019/11/11 11:19 下午
 * @Author Charles
 * @Version 1.0.0
 **/
public class MultiplexerTimeServer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MultiplexerTimeServer.class);

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop;

    /**
     * 初始化多路复用器，绑定监听端口
     * @param port
     */
    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            logger.info("The time server is start in port:{}",port);
        } catch (IOException e) {
            logger.error("Falied to init time server. ErrorMsg:[{}]",e.getMessage(),e);
            return;
        }
    }

    public void stop(){
        this.stop = true;
    }

    public void run() {
        while (!stop){
            try {
                //设置selector休眠时间，无论是否有读写时间发生，每隔一秒均会唤醒selector
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterable = selectionKeys.iterator();
                SelectionKey key = null;
                while (iterable.hasNext()){
                    key = iterable.next();
                    iterable.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if(key != null){
                            key.cancel();
                            if(key.channel() != null){
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                logger.error("Failed to execute time server. ErrorMsg:[{}]",e.getMessage(),e);
            }
        }

        //多路复用器关闭后，所有注册在上面的channel和Pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
        if(selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                logger.error("Failed to close selector! ErrorMsg:[{}]",e.getMessage(),e);
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException{
        if(key.isValid()){
            //处理新接入的请求消息
            if(key.isAcceptable()){
                //Accept the new connection
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                //Add the new connection to the selector
                sc.register(selector,SelectionKey.OP_READ);
            }
            if(key.isReadable()){
                //Read the data
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if(readBytes > 0){
                    //将缓冲区当前的limit设置为position,position设置为0，用于后续对缓冲区对读取操作
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes,"UTF-8");
                    logger.info("The time server receive order : {}",body);
                    String currentTime = "QUERY TIME ORDER ".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                    doWrite(sc, currentTime);
                }else if(readBytes < 0){
                    //对端连路关闭
                    key.channel();
                    sc.close();
                }
            }
        }
    }

    private void doWrite(SocketChannel channel, String response) throws IOException{
        if(response != null && response.trim().length() > 0){
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            //TODO 可能会出现半写包对问题，后续进行优化
            channel.write(writeBuffer);
        }
    }
}
