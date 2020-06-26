package club.wadreamer.cloudlearning.util;

import java.io.File;

/**
 * @ClassName FileUtils
 * @Description TODO
 * @Author bear
 * @Date 2020/4/11 17:40
 * @Version 1.0
 **/
public class FileUtils {
    public static boolean delFile(String filePath) {
        File delFile = new File(filePath);
        if(delFile.isFile() && delFile.exists()) {
            delFile.delete();
            return true;
        }else {
            return false;
        }
    }
}
