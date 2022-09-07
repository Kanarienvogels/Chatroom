package pers.kanarien.chatroom.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * <p>
 *   描述: 有关文件信息和操作的工具类
 *        1. 通过文件头判断文件类型  
 *        2. 获取格式化的文件大小 
 *        3. 高效的将文件转换成字节数组  
 * </p>
 * @author Kanarien 
 * @version 1.0
 * @date 2017年11月15日 下午9:05:10
 */
public class FileUtils {

    private static final HashMap<String, String> fileTypes = new HashMap<>();
    static { // BOM（Byte Order Mark）文件头字节
        fileTypes.put("494433", "mp3");
        fileTypes.put("524946", "wav");
        fileTypes.put("ffd8ff", "jpg");
        fileTypes.put("FFD8FF", "jpg");
        fileTypes.put("89504E", "png");
        fileTypes.put("89504e", "png");
        fileTypes.put("474946", "gif");
    }
    private static final String B_UNIT = "B";
    private static final String KB_UNIT = "KB";
    private static final String MB_UNIT = "MB";
    private static final String GB_UNIT = "GB";

    /**
     * 0 和 # 都是占位符，但是 0 可能会用0前导补齐（下面的代码能实现保留一位小数的作用）
     */
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.0");

    
    /**
     * <p>
     *    描述：通过含BOM（Byte Order Mark）的文件头的
     *        前 3个字节判断文件类型 
     * </p>
     * @param filePath 文件路径
     * @return
     */
    public static String getFileType(String filePath) {
        return fileTypes.get(getFileHeader3(filePath));
    }

    /**
     * <p>
     *    描述：获取文件头前3个字节的十六进制表示
     * </p>
     * @param filePath 文件路径
     * @return
     */
    private static String getFileHeader3(String filePath) {
        
      File file=new File(filePath);
        // 前三个字节只是头部标识，并非内容
        if(!file.exists() || file.length() < 4){
            return "null";
        }
        String value = null;
        try (FileInputStream is = new FileInputStream(file)) {
            byte[] b = new byte[3];
            is.read(b, 0, b.length);
            value = bytesToHexString(b);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }
    
    private static String bytesToHexString(byte[] src){
        if (src == null || src.length <= 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (byte value : src) {
            int v = value & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * <p>
     *    描述： 获取格式化的文件大小
     *         格式为带单位保留一位小数
     * </p>
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        String fileSizeString = "";
        if (size < 1024) {
            fileSizeString = decimalFormat.format(size) + B_UNIT;
        } else if (size < 1048576) {
            fileSizeString = decimalFormat.format(size / 1024) + KB_UNIT;
        } else if (size < 1073741824) {
            fileSizeString = decimalFormat.format(size / 1048576) + MB_UNIT;
        } else {
            fileSizeString = decimalFormat.format(size / 1073741824) + GB_UNIT;
        }
        return fileSizeString;
    }
    
    public static String getFormatSize(long size) {
        return getFormatSize((double)size);
    }
    
    /**
     * <p>
     *    描述：高效率的将文件转换成字节数组
     * </p>
     * @param filePath
     * @return
     * @throws IOException
     */
//    remove the warning for a potential resource leak.
//    @SuppressWarnings("resource")
    public static byte[] toByteArray(String filePath) throws IOException {
        
        try (FileChannel fc = new RandomAccessFile(filePath, "r").getChannel()) {
            MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0,
                    fc.size()).load();
            System.out.println(byteBuffer.isLoaded());
            byte[] result = new byte[(int)fc.size()];
            if (byteBuffer.remaining() > 0) {
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
