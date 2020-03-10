package com.khaki.jxc.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.khaki.jxc.Contants;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Edward on 2017/8/4.
 */

public class ImageUtils {

    public static boolean setUserImageIcon(Context mContext, ImageView icon, String userName){
        try {
            String imageUrl = (String) SharedPrefUtil.getInstance().get(Contants.IMAGE_ICON_URL,"");
            if(imageUrl==null||imageUrl.isEmpty()){
                icon.setImageDrawable(ImageUtils.getIcon(userName,23));
            }
            else{
                Glide.with(mContext).load(Contants.PHOTO_SERVER_IP + imageUrl).into(icon);
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean setUserRectImageIcon(Context mContext, ImageView icon, String userName){
        try {
            icon.setImageDrawable(ImageUtils.getRectIcon(userName,23));
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Drawable getIcon(String content, int textsize){
        if(content == null) return null;
        String str = content;
        if(isContainChinese(content)){
            if(content.getBytes().length > 6)
            {
//                str = str.replace(getSubString(content,2),"");
                int start = str.length() - 2;
                int end = str.length() ;
                str = str.substring(start, end);
            }
        }
        else{
            if(str.length() > 1)
            {
                str = str.substring(0,1);
            }
        }
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getRandomColor();
        TextDrawable drawable =  TextDrawable.builder().beginConfig().width(60).height(60)
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .fontSize(textsize)
                .bold()
                .toUpperCase()
                .endConfig()
                .buildRound(str, color);
        return drawable;
    }

    public static TextDrawable getRectIcon(String content, int textsize){
        if(content == null) return null;
        String str = content;
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getRandomColor();
        TextDrawable drawable =  TextDrawable.builder()
                .beginConfig().width(60).height(60)
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .fontSize(textsize)
                .endConfig()
                .buildRect(str, color);
        return drawable;
    }


    public static boolean isContainChinese(String str){
        return str.length() != str.getBytes().length;
    }

    public static String getSubString(String str, int length) {
        int count = 0;
        int offset = 0;
        char[] c = str.toCharArray();
        int size = c.length;
        if(size >= length){
            for (int i = 0; i < c.length; i++) {
                if (c[i] > 256) {
                    offset = 2;
                    count += 2;
                } else {
                    offset = 1;
                    count++;
                }
                if (count == length) {
                    return str.substring(0, i + 1);
                }
                if ((count == length + 1 && offset == 2)) {
                    return str.substring(0, i);
                }
            }
        }else{
            return str;
        }
        return "";
    }

    public static File changeDrawableToFile(Drawable drawable,String path, String fileName){
        //BitmapFactory.
//        BitmapDrawable bd = (BitmapDrawable) drawable;
//        Bitmap bm = bd.getBitmap();
        return changeBitmapToFile(drawableToBitmap(drawable), path, fileName);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static File changeBitmapToFile(Bitmap bm,String path, String fileName){
        File file=new File(path, fileName);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }
}
