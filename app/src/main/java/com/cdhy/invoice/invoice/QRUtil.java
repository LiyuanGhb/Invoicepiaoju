package com.cdhy.invoice.invoice;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QRUtil {
    private static final int QR_WIDTH = 400;
    private static final int QR_HEIGHT = 400;

    public static String createString(String qyyhid, String qymc, String nsrsbh, String qydz, String qydh, String qyyh, String qyyhzh) {
        String s = "cdhy`0`"
                + qyyhid + "`"
                + qymc + "`"
                + nsrsbh + "`"
                + qydz
                + qydh + "`"
                + qyyh
                + qyyhzh;

        String enToStr = replaceBlank(Base64.encodeToString(s.getBytes(), Base64.DEFAULT));
        Log.e("s", s);
        String rever = replaceBlank(reverseString(enToStr));
        String str1 = replaceBlank(rever.substring(0, 3));
        rever = replaceBlank(rever.substring(3, rever.length()) + str1);
        String str2 = replaceBlank(rever.substring(0, 3));
        rever = rever.substring(3, rever.length()) + str2;
        String str3 = rever.substring(0, 3);
        rever = rever.substring(3, rever.length()) + str3;
        return rever;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String reverseString(String str) {
        StringBuffer stringBuffer = new StringBuffer(str);
        return stringBuffer.reverse().toString();
    }


    public static Bitmap createQRImage(String url) {
        try {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            //显示到一个ImageView上面
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
