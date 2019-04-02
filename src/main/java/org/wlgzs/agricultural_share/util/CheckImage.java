package org.wlgzs.agricultural_share.util;

public class CheckImage {

    public static boolean verifyImage(String fileName){
        String reg="(?i).+?\\.(jpg|gif|bmp|png|jpeg)";
        return fileName.matches(reg);
    }

    public static boolean verifyImages(String[] fileNames){
        String reg="(?i).+?\\.(jpg|gif|bmp|png|jpeg)";
        for(int i = 0;i<fileNames.length;i++){
            if(!fileNames[i].matches(reg)){
                return false;
            }
        }
        return true;
    }
}
