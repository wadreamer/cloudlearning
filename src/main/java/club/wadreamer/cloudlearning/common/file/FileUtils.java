package club.wadreamer.cloudlearning.common.file;

import java.io.File;

/**
 * @ClassName FileUtils
 * @Description TODO
 * @Author bear
 * @Date 2020/3/9 10:07
 * @Version 1.0
 **/
public class FileUtils {
    private FileUtils() {
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
}
