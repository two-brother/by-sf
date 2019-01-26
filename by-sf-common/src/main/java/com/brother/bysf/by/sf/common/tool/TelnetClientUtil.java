package com.brother.bysf.by.sf.common.tool;


import org.apache.commons.net.telnet.TelnetClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;

/**
 * @author XFun
 */
public class TelnetClientUtil {
    public static void main(String[] args) {

        try {
            TelnetClient tc = new TelnetClient();
            tc.connect("182.84.129.83", 2307);

            OutputStream os = tc.getOutputStream();

            System.out.println(readUntil(":", tc.getInputStream()));

            writeUtil("admin", os);
            System.out.println(readUntil(":", tc.getInputStream()));

            writeUtil("!DjV!M9j3n1f39Jf0!j0ji!9H1KI0!qio3!2U!", os);
            System.out.println(readUntil(">", tc.getInputStream()));


            writeUtil("en", os);
            System.out.println(readUntil("#", tc.getInputStream()));

            writeUtil("show cpu usage", os);
            System.out.println(readUntil("%", tc.getInputStream()));

            writeUtil("show memory usage", os);
            System.out.println(readUntil("%", tc.getInputStream()));

            writeUtil("exit", os);
            System.out.println(readUntil("%", tc.getInputStream()));


        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 写入命令方法
     */
    public static void writeUtil(String cmd, OutputStream os){
        try {
            cmd = cmd + "\n";
            os.write(cmd.getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 读到指定位置,不在向下读
     */
    public static String readUntil(String endFlag, InputStream in) {

        InputStreamReader isr = new InputStreamReader(in);

        char[] charBytes = new char[1024];
        int n;
        boolean flag = false;
        String str = "";
        try {
            while((n = isr.read(charBytes)) != -1){
                for(int i=0; i< n; i++){
                    char c = charBytes[i];
                    str += c;
                    if(str.endsWith(endFlag)){
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }
}
