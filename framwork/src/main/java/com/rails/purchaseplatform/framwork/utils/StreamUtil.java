package com.rails.purchaseplatform.framwork.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author： sk_comic@163.com
 * @date: 2022/3/14
 */
public class StreamUtil {


    //把一个inputStream 转换成一个String
    public static String readStream(InputStream in) throws Exception{
        //定义一个内存输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = -1;
        byte[] buffer = new byte[1024]; //1kb
        while((len=in.read(buffer))!=-1){
            baos.write(buffer, 0, len);
        }
        in.close();
        return baos.toString();

    }

}
