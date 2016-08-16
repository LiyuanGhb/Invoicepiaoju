package com.cdhy.invoice.invoice;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Administrator on 2016/6/27.
 */
public class TcpClient {
    /**
     * tcp请求
     *
     * @param message 二维码内容
     * @param ip      pc端ip地址
     * @return pc端结果
     */
    public static String tcp(String message, String ip) {
        Socket socket = null;
        String rexml = "";
        boolean istrue = false;
        try {
            socket = new Socket(ip, 7001);
            OutputStream ops = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(ops);
            dos.write(message.getBytes());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            char[] data = new char[1024 * 10];
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
            int len = br.read(data);
            rexml = String.valueOf(data, 0, len);
            System.out.println("服务器端返回过来的是: " + rexml);
            dos.close();
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
            istrue = true;
            return "";
        } finally {
            if (!istrue) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return rexml;
    }

}
