/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.http.framework.dream;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTool {

    /**
     * 从输入流中读取数据
     *
     * @param inStream
     *
     * @return
     *
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream=new ByteArrayOutputStream();
        byte[]                buffer   =new byte[1024];
        int                   len      =0;
        while ((len=inStream.read(buffer))!=-1) {
            outStream.write(buffer,0,len);
        }
        byte[] data=outStream.toByteArray();//网页的二进制数据
        outStream.close();
        inStream.close();
        return data;
    }
}
