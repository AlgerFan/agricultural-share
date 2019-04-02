package org.wlgzs.agricultural_share.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author 阿杰
 * @create 2018-07-19 21:32
 * @Description: 上传文件
 */
public class UploadUtil {
    //文件写入
    public void saveFile(MultipartFile file, String filePath) {
        try {
            if (!file.isEmpty()) {
                File saveFile = new File("." + filePath);
                if (!saveFile.getParentFile().exists()) saveFile.getParentFile().mkdirs();
                FileOutputStream outputStream = new FileOutputStream(saveFile);
                BufferedOutputStream out = new BufferedOutputStream(outputStream);
                out.write(file.getBytes());
                out.flush();
                out.close();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //删除文件
    public void deleteFile(String url) {
        File file = new File("." + url);
        if (!url.equals("/upload/headPortrait/default.jpg") && file.exists() && file.exists()) {
            file.delete();
        }
    }

    //删除文件夹
    public boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
