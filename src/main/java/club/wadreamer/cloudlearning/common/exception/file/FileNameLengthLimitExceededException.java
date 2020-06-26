package club.wadreamer.cloudlearning.common.exception.file;

import org.apache.commons.fileupload.FileUploadException;

/**
 * @ClassName FileNameLengthLimitExceededException 文件名超长异常
 * @Description TODO
 * @Author bear
 * @Date 2020/3/1 21:27
 * @Version 1.0
 **/
public class FileNameLengthLimitExceededException extends FileUploadException {
    private static final long serialVersionUID = 1L;
    private int length;
    private int maxLength;
    private String filename;

    public FileNameLengthLimitExceededException(String filename, int length, int maxLength) {
        super("file name : [" + filename + "], length : [" + length + "], max length : [" + maxLength + "]");
        this.length = length;
        this.maxLength = maxLength;
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public int getLength() {
        return length;
    }

    public int getMaxLength() {
        return maxLength;
    }
}
