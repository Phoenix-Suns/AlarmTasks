package com.windyroad.nghia.common;

/**
 * Created by Nghia-PC on 7/23/2015.
 */
public class ByteUtil {

    /**
     * Công 2 mảng Byte thành 1 mảng
     * @param arrFirst
     * @param arrLast
     * @return
     */
    public static byte[] concatenateByteArrays(byte[] arrFirst, byte[] arrLast) {
        byte[] result = new byte[arrFirst.length + arrLast.length];
        System.arraycopy(arrFirst, 0, result, 0, arrFirst.length);
        System.arraycopy(arrLast, 0, result, arrFirst.length, arrLast.length);
        return result;
    }
}
