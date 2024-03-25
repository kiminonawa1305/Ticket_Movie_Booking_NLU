package com.lamnguyen.ticket_movie_nlu.model.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {

    // Hàm để chuyển đổi InputStream thành byte array
    public static byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException, IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}