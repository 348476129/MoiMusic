package com.example.moimusic.utils;

import android.text.TextUtils;

/**
 * Created by qqq34 on 2016/2/18.
 */
public  class Utils {
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return true;
        else return mobiles.matches(telRegex);
    }

    public static boolean isPassword(String password) {
        if (password.length() >= 6 && password.length() <= 20) {
            return true;
        } else if (password.length() == 0) {
            return true;
        } else {
            return false;
        }
    }
    public static String fitNum(int i){ //把数字超过10000的 变成万
        String s = "";
        if (i>=10000){
            double d = (double) i/10000;
            String result = String .format("%.2f",d);
            s = result+"万";
        }else {
            s=i+"";
        }
        return s;
    }
}