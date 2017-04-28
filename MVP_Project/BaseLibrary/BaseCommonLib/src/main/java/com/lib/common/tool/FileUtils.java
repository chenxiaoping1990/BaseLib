package com.lib.common.tool;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <ul>
 * Read or write file
 * <li>{@link #writeFile(String, String, boolean)} write file</li>
 * <li>{@link #writeFile(String, InputStream)} write file</li>
 * <li>{@link #writeFile(String, InputStream, boolean)} write file</li>
 * <li>{@link #writeFile(File, InputStream)} write file</li>
 * <li>{@link #writeFile(File, InputStream, boolean)} write file</li>
 * </ul>
 * <ul>
 * Operate file
 * <li>{@link #copyFile(String, String)}</li>
 * <li>{@link #getFileExtension(String)}</li>
 * <li>{@link #getFileName(String)}</li>
 * <li>{@link #getFileNameWithoutExtension(String)}</li>
 * <li>{@link #getFileSize(String)}</li>
 * <li>{@link #deleteFile(String)}</li>
 * <li>{@link #isFileExist(String)}</li>
 * <li>{@link #isFolderExist(String)}</li>
 * <li>{@link #makeFolders(String)}</li>
 * <li>{@link #makeDirs(String)}</li>
 * </ul>
 *
 * @author allen
 * @since 2016-12-20
 */
public class FileUtils {

    public final static String FILE_EXTENSION_SEPARATOR = ".";

    /**
     * read file
     *
     * @param filePath
     * @param charsetName The name of a supported {@link java.nio.charset.Charset
     *                    </code>charset<code>}
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    public static StringBuilder readFile(String filePath, String charsetName) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(
                    file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * write file
     *
     * @param filePath
     * @param content
     * @param append   is append, if true, write to the end of file, else clear
     *                 content of file and write into it
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileWriter
     */
    public static boolean writeFile(String filePath, String content,
                                    boolean append) {
        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * write file
     *
     * @param filePath
     * @param stream
     * @return
     * @see {@link #writeFile(String, InputStream, boolean)}
     */
    public static boolean writeFile(String filePath, InputStream stream) {
        return writeFile(filePath, stream, false);
    }

    /**
     * write file
     *
     * @param stream the input stream
     * @param append if <code>true</code>, then bytes will be written to the end of
     *               the file rather than the beginning
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean writeFile(String filePath, InputStream stream,
                                    boolean append) {
        return writeFile(filePath != null ? new File(filePath) : null, stream,
                append);
    }

    /**
     * write file
     *
     * @param file
     * @param stream
     * @return
     * @see {@link #writeFile(File, InputStream, boolean)}
     */
    public static boolean writeFile(File file, InputStream stream) {
        return writeFile(file, stream, false);
    }

    /**
     * write file
     *
     * @param file   the file to be opened for writing.
     * @param stream the input stream
     * @param append if <code>true</code>, then bytes will be written to the end of
     *               the file rather than the beginning
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean writeFile(File file, InputStream stream,
                                    boolean append) {
        if (null == file)
            return false;

        OutputStream o = null;
        try {
            makeDirs(file.getAbsolutePath());
            o = new FileOutputStream(file, append);
            byte data[] = new byte[1024];
            int length = -1;
            while ((length = stream.read(data)) != -1) {
                o.write(data, 0, length);
            }
            o.flush();
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (o != null) {
                try {
                    o.close();
                    stream.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * copy file
     *
     * @param sourceFilePath
     * @param destFilePath
     * @return
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean copyFile(String sourceFilePath, String destFilePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(sourceFilePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        }
        return writeFile(destFilePath, inputStream);
    }

    /**
     * read file to string list, a element of list is a line
     *
     * @param filePath
     * @param charsetName The name of a supported {@link java.nio.charset.Charset
     *                    </code>charset<code>}
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    public static List<String> readFileToList(String filePath,
                                              String charsetName) {
        File file = new File(filePath);
        List<String> fileContent = new ArrayList<String>();
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(
                    file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * get file name from path, not include suffix
     * <p/>
     * <pre>
     *      getFileNameWithoutExtension(null)               =   null
     *      getFileNameWithoutExtension("")                 =   ""
     *      getFileNameWithoutExtension("   ")              =   "   "
     *      getFileNameWithoutExtension("abc")              =   "abc"
     *      getFileNameWithoutExtension("a.mp3")            =   "a"
     *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
     *      getFileNameWithoutExtension("c:\\")              =   ""
     *      getFileNameWithoutExtension("c:\\a")             =   "a"
     *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
     *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
     *      getFileNameWithoutExtension("/home/admin")      =   "admin"
     *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
     * </pre>
     *
     * @param filePath
     * @return file name from path, not include suffix
     * @see
     */
    public static String getFileNameWithoutExtension(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1 ? filePath : filePath.substring(0,
                    extenPosi));
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        }
        return (filePosi < extenPosi ? filePath.substring(filePosi + 1,
                extenPosi) : filePath.substring(filePosi + 1));
    }

    /**
     * get file name from path, include suffix
     * <p/>
     * <pre>
     *      getFileName(null)               =   null
     *      getFileName("")                 =   ""
     *      getFileName("   ")              =   "   "
     *      getFileName("a.mp3")            =   "a.mp3"
     *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
     *      getFileName("abc")              =   "abc"
     *      getFileName("c:\\")              =   ""
     *      getFileName("c:\\a")             =   "a"
     *      getFileName("c:\\a.b")           =   "a.b"
     *      getFileName("c:a.txt\\a")        =   "a"
     *      getFileName("/home/admin")      =   "admin"
     *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
     * </pre>
     *
     * @param filePath
     * @return file name from path, include suffix
     */
    public static String getFileName(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
    }

    /**
     * get folder name from path
     * <p/>
     * <pre>
     *      getFolderName(null)               =   null
     *      getFolderName("")                 =   ""
     *      getFolderName("   ")              =   ""
     *      getFolderName("a.mp3")            =   ""
     *      getFolderName("a.b.rmvb")         =   ""
     *      getFolderName("abc")              =   ""
     *      getFolderName("c:\\")              =   "c:"
     *      getFolderName("c:\\a")             =   "c:"
     *      getFolderName("c:\\a.b")           =   "c:"
     *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
     *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
     *      getFolderName("/home/admin")      =   "/home"
     *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
     * </pre>
     *
     * @param filePath
     * @return
     */
    public static String getFolderName(String filePath) {

        if (StringUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }

    /**
     * get suffix of file from path
     * <p/>
     * <pre>
     *      getFileExtension(null)               =   ""
     *      getFileExtension("")                 =   ""
     *      getFileExtension("   ")              =   "   "
     *      getFileExtension("a.mp3")            =   "mp3"
     *      getFileExtension("a.b.rmvb")         =   "rmvb"
     *      getFileExtension("abc")              =   ""
     *      getFileExtension("c:\\")              =   ""
     *      getFileExtension("c:\\a")             =   ""
     *      getFileExtension("c:\\a.b")           =   "b"
     *      getFileExtension("c:a.txt\\a")        =   ""
     *      getFileExtension("/home/admin")      =   ""
     *      getFileExtension("/home/admin/a.txt/b")  =   ""
     *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
     * </pre>
     *
     * @param filePath
     * @return
     */
    public static String getFileExtension(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1) {
            return "";
        }
        return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
    }

    /**
     * Creates the directory named by the trailing filename of this file,
     * including the complete directory path required to create this directory. <br/>
     * <br/>
     * <ul>
     * <strong>Attentions:</strong>
     * <li>makeDirs("C:\\Users\\Trinea") can only create users folder</li>
     * <li>makeFolder("C:\\Users\\Trinea\\") can create Trinea folder</li>
     * </ul>
     *
     * @param filePath
     * @return true if the necessary directories have been created or the target
     * directory already exists, false one of the directories can not be
     * created.
     * <ul>
     * <li>if {@link FileUtils#getFolderName(String)} return null,
     * return false</li>
     * <li>if target directory already exists, return true</li>
     * </ul>
     */
    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (StringUtils.isEmpty(folderName)) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder
                .mkdirs();
    }

    /**
     * @param filePath
     * @return
     * @see #makeDirs(String)
     */
    public static boolean makeFolders(String filePath) {
        return makeDirs(filePath);
    }

    /**
     * Indicates if this file represents a file on the underlying file system.
     *
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return false;
        }

        File file = new File(filePath);
        return (file.exists() && file.isFile());
    }

    /**
     * Indicates if this file represents a directory on the underlying file
     * system.
     *
     * @param directoryPath
     * @return
     */
    public static boolean isFolderExist(String directoryPath) {
        if (StringUtils.isBlank(directoryPath)) {
            return false;
        }

        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());
    }

    /**
     * delete file or directory
     * <ul>
     * <li>if path is null or empty, return true</li>
     * <li>if path not exist, return true</li>
     * <li>if path exist, delete recursion. return true</li>
     * <ul>
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        if (StringUtils.isBlank(path)) {
            return true;
        }

        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        File[] files = file.listFiles();
        if (null != files && files.length > 0)
            for (File f : files) {
                if (f.isFile()) {
                    f.delete();
                } else if (f.isDirectory()) {
                    deleteFile(f.getAbsolutePath());
                }
            }
        return file.delete();
    }

    /**
     * get file size
     * <ul>
     * <li>if path is null or empty, return -1</li>
     * <li>if path exist and it is a file, return file size, else return -1</li>
     * <ul>
     *
     * @param path
     * @return returns the length of this file in bytes. returns -1 if the file
     * does not exist.
     */
    public static long getFileSize(String path) {
        if (StringUtils.isBlank(path)) {
            return -1;
        }

        File file = new File(path);
        return (file.exists() && file.isFile() ? file.length() : -1);
    }

    /**
     * 显示文件的大小
     */
    public static String toStrSize(int fileSize) {
        String fileSizeName = null;
        DecimalFormat df = new DecimalFormat("#.00");
        if (fileSize < 1024) {
            fileSizeName = df.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            fileSizeName = df.format((double) fileSize / 1024) + "K";
        } else if (fileSize < 1073741824) {
            fileSizeName = df.format((double) fileSize / 1048576) + "M";
        } else {
            fileSizeName = df.format((double) fileSize / 1073741824) + "G";
        }
        return fileSizeName;
    }

    /**
     * 获取图片ID
     *
     * @param fileName 文件名称的后缀名称
     * @return 图片的dataType
     */
    public static String getFileType(String fileName) {

        String fileType = null;
        if (fileName.contains(".")) {
            int index = fileName.lastIndexOf(".");
            fileName = fileName.substring(index + 1, fileName.length());
        }
        /* 根据后缀名得到表示文件类型的图片id */
        if (fileName.equalsIgnoreCase("mp3")) {
            // imgId = R.drawable.ic_parttern_icon_mp3;
            fileType = "audio/x-mpeg";
        } else if (fileName.equalsIgnoreCase("c")) {
            // imgId = R.drawable.ic_parttern_icon_c__;
            fileType = "text/plain";
        } else if (fileName.equalsIgnoreCase("pdf")) {
            // imgId = R.drawable.ic_parttern_icon_pdf;
            fileType = "application/pdf";
        } else if (fileName.equalsIgnoreCase("jpg")) {
            // imgId = R.drawable.ic_parttern_icon_jpg;
            fileType = "image/*";
        } else if (fileName.equalsIgnoreCase("gif")) {
            // imgId = R.drawable.ic_parttern_icon_gif;
            fileType = "image/*";
        } else if (fileName.equalsIgnoreCase("png")) {
            // imgId = R.drawable.ic_parttern_icon_png;
            fileType = "image/*";
        } else if (fileName.equalsIgnoreCase("log")) {
            // imgId = R.drawable.ic_parttern_icon_log;
            fileType = "text/plain";
        } else if (fileName.equalsIgnoreCase("js")) {
            // imgId = R.drawable.ic_parttern_icon_js;
            // fileType = "application/x-javascript";
            fileType = "text/html";
        } else if (fileName.equalsIgnoreCase("psd")) {
            // imgId = R.drawable.ic_parttern_icon_psd;
            fileType = "*/*";
        } else if (fileName.equalsIgnoreCase("txt")) {
            // imgId = R.drawable.ic_parttern_icon_txt;
            fileType = "text/plain";
        } else if (fileName.equalsIgnoreCase("swf")) {
            // imgId = R.drawable.ic_parttern_icon_swf;
            fileType = "*/*";
        } else if (fileName.equalsIgnoreCase("rar")) {
            // imgId = R.drawable.ic_parttern_icon_rar;
            fileType = "application/x-zip-compressed";
        } else if (fileName.equalsIgnoreCase("zip")) {
            // imgId = R.drawable.ic_parttern_icon_zip;
            fileType = "application/x-zip-compressed";
        } else if (fileName.equalsIgnoreCase("7z")) {
            // imgId = R.drawable.ic_parttern_icon_7z;
            fileType = "application/x-zip-compressed";
        } else if (fileName.equalsIgnoreCase("xls")) {
            // imgId = R.drawable.ic_parttern_icon_xls;
            fileType = "application/vnd.ms-excel";
        } else if (fileName.equalsIgnoreCase("doc")) {
            // imgId = R.drawable.ic_parttern_icon_doc;
            fileType = "application/msword";
        } else if (fileName.equalsIgnoreCase("ppt")) {
            // imgId = R.drawable.ic_parttern_icon_ppt;
            fileType = "application/vnd.ms-powerpoint";
        } else if (fileName.equalsIgnoreCase("chm")) {
            // imgId = R.drawable.ic_parttern_icon_chm;
            fileType = "application/x-chm";
        } else if (fileName.equalsIgnoreCase("3gp")) {
            // imgId = R.drawable.ic_parttern_icon_3gp;
            fileType = "audio/*";
        } else if (fileName.equalsIgnoreCase("wmv")) {
            // imgId = R.drawable.ic_parttern_icon_wmv;
            fileType = "video/*";
        } else if (fileName.equalsIgnoreCase("rm")) {
            // imgId = R.drawable.ic_parttern_icon_rm;
            fileType = "video/*";
        } else if (fileName.equalsIgnoreCase("rmvb")) {
            // imgId = R.drawable.ic_parttern_icon_rmvb;
            fileType = "video/*";
        } else if (fileName.equalsIgnoreCase("video")) {
            // imgId = R.drawable.ic_parttern_icon_video;
            fileType = "video/*";
        } else if (fileName.equalsIgnoreCase("ai")) {
            // imgId = R.drawable.ic_parttern_icon_ai;
            fileType = "*/*";
        } else if (fileName.equalsIgnoreCase("asp")) {
            // imgId = R.drawable.ic_parttern_icon_asp;
            fileType = "text/plain";
        } else if (fileName.equalsIgnoreCase("avi")) {
            // imgId = R.drawable.ic_parttern_icon_avi;
            fileType = "video/*";
        } else if (fileName.equalsIgnoreCase("bat")) {
            // imgId = R.drawable.ic_parttern_icon_bat;
            fileType = "video/*";
        } else if (fileName.equalsIgnoreCase("css")) {
            // imgId = R.drawable.ic_parttern_icon_css;
            fileType = "text/plain";
        } else if (fileName.equalsIgnoreCase("dmg")) {
            // imgId = R.drawable.ic_parttern_icon_dmg;
            fileType = "*/*";
        } else if (fileName.equalsIgnoreCase("fla")) {
            // imgId = R.drawable.ic_parttern_icon_fla;
            fileType = "*/*";
        } else if (fileName.equalsIgnoreCase("exe")) {
            // imgId = R.drawable.ic_parttern_icon_exe;
            fileType = "application/octet-stream";
        } else if (fileName.equalsIgnoreCase("php")) {
            // imgId = R.drawable.ic_parttern_icon_php;
            fileType = "text/plain";
        } else if (fileName.equalsIgnoreCase("iso")) {
            // imgId = R.drawable.ic_parttern_icon_iso;
            fileType = "*/*";
        } else if (fileName.equalsIgnoreCase("multi")) {
            // imgId = R.drawable.ic_parttern_icon_multi_big;
            fileType = "*/*";
        } else if (fileName.equalsIgnoreCase("html")) {
            // imgId = R.drawable.ic_parttern_icon_html;
            fileType = "text/html";
        } else if (fileName.equalsIgnoreCase("htm")) {
            // imgId = R.drawable.ic_parttern_icon_htm;
            fileType = "text/html";
        } else {
            // imgId
            fileType = "*/*";
        }
        return fileType;
    }

    /**
     * 将文件数据写入sdcard中，保存数据
     *
     * @param content -写入内容
     * @return
     */
    public static boolean writeDataToSD(String content) {
        try {
    /* 获取File对象，确定数据文件的信息 */

            File file = new File(Environment.getExternalStorageDirectory(), "ziizaa_test.txt");
            if (!file.exists()) {
                file.mkdir();
            }
    /* 判断sd的外部设置状态是否可以读写 */
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        /* 流的对象 *//*  */
                FileOutputStream fos = new FileOutputStream(file);
        /* 需要写入的数据 */
        /* 将字符串转换成字节数组 */
                byte[] buffer = content.getBytes();
        /* 开始写入数据 */
                fos.write(buffer);
        /* 关闭流的使用 */
                fos.close();
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }
}
