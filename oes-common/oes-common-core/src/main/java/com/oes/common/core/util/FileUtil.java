package com.oes.common.core.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.oes.common.core.exception.ApiException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/27 11:53
 */
@Slf4j
public class FileUtil extends cn.hutool.core.io.FileUtil {

  private FileUtil() {
  }

  /**
   * 系统临时目录
   * <br>
   * windows 包含路径分割符，但Linux 不包含, 在windows \\==\ 前提下， 为安全起见 同意拼装 路径分割符，
   * <pre>
   *       java.io.tmpdir
   *       windows : C:\Users/xxx\AppData\Local\Temp\
   *       linux: /temp
   * </pre>
   */
  public static final String SYS_TEM_DIR = System.getProperty("java.io.tmpdir") + File.separator;
  /**
   * 定义GB的计算常量
   */
  private static final int GB = 1024 * 1024 * 1024;
  /**
   * 定义MB的计算常量
   */
  private static final int MB = 1024 * 1024;
  /**
   * 定义KB的计算常量
   */
  private static final int KB = 1024;

  /**
   * 格式化小数
   */
  private static final DecimalFormat DF = new DecimalFormat("0.00");

  /**
   * MultipartFile转File
   */
  public static File toFile(MultipartFile multipartFile) {
    // 获取文件名
    String fileName = multipartFile.getOriginalFilename();
    // 获取文件后缀
    String prefix = "." + getExtensionName(fileName);
    File file = null;
    try {
      // 用uuid作为文件名，防止生成的临时文件重复
      file = File.createTempFile(IdUtil.simpleUUID(), prefix);
      // MultipartFile to File
      multipartFile.transferTo(file);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
    return file;
  }

  /**
   * 获取文件扩展名，不带 .
   */
  public static String getExtensionName(String filename) {
    if (StrUtil.isBlank(filename)) {
      return null;
    }
    int dot = filename.lastIndexOf('.');
    if ((dot > -1) && (dot < (filename.length() - 1))) {
      return filename.substring(dot + 1);
    }
    return filename;
  }

  /**
   * Java文件操作 获取不带扩展名的文件名
   */
  public static String getFileNameNoEx(String filename) {
    if ((filename != null) && (filename.length() > 0)) {
      int dot = filename.lastIndexOf('.');
      if ((dot > -1) && (dot < (filename.length()))) {
        return filename.substring(0, dot);
      }
    }
    return filename;
  }

  /**
   * 文件大小转换
   */
  public static String getSize(long size) {
    String resultSize;
    if (size / GB >= 1) {
      //如果当前Byte的值大于等于1GB
      resultSize = DF.format(size / (float) GB) + "GB   ";
    } else if (size / MB >= 1) {
      //如果当前Byte的值大于等于1MB
      resultSize = DF.format(size / (float) MB) + "MB   ";
    } else if (size / KB >= 1) {
      //如果当前Byte的值大于等于1KB
      resultSize = DF.format(size / (float) KB) + "KB   ";
    } else {
      resultSize = size + "B   ";
    }
    return resultSize;
  }

  /**
   * inputStream 转 File
   */
  static File inputStreamToFile(InputStream ins, String name) throws Exception {
    File file = new File(SYS_TEM_DIR + name);
    if (file.exists()) {
      return file;
    }
    OutputStream os = new FileOutputStream(file);
    int bytesRead;
    int len = 8192;
    byte[] buffer = new byte[len];
    while ((bytesRead = ins.read(buffer, 0, len)) != -1) {
      os.write(buffer, 0, bytesRead);
    }
    os.close();
    ins.close();
    return file;
  }

  /**
   * 将文件名解析成文件的上传路径
   */
  public static File upload(MultipartFile file, String filePath) {
    Date date = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmssS");
    String name = getFileNameNoEx(file.getOriginalFilename());
    String suffix = getExtensionName(file.getOriginalFilename());
    String nowStr = "-" + format.format(date);
    try {
      String fileName = name + nowStr + "." + suffix;
      String path = filePath + fileName;
      // getCanonicalFile 可解析正确各种路径
      File dest = new File(path).getCanonicalFile();
      // 检测是否存在目录
      if (!dest.getParentFile().exists()) {
        if (!dest.getParentFile().mkdirs()) {
          System.out.println("was not successful.");
        }
      }
      // 文件写入
      file.transferTo(dest);
      return dest;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public static void checkSize(long maxSize, long size) {
    // 1M
    int len = 1024 * 1024;
    if (size > (maxSize * len)) {
      throw new ApiException("文件超出规定大小");
    }
  }

  /**
   * 判断两个文件是否相同
   */
  public static boolean check(File file1, File file2) {
    String img1Md5 = getMd5(file1);
    String img2Md5 = getMd5(file2);
    return StrUtil.equals(img1Md5, img2Md5);
  }

  /**
   * 判断两个文件是否相同
   */
  public static boolean check(String file1Md5, String file2Md5) {
    return file1Md5.equals(file2Md5);
  }

  private static byte[] getByte(File file) {
    // 得到文件长度
    return new byte[(int) file.length()];
  }

  private static String getMd5(byte[] bytes) {
    // 16进制字符
    char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
        'f'};
    try {
      MessageDigest mdTemp = MessageDigest.getInstance("MD5");
      mdTemp.update(bytes);
      byte[] md = mdTemp.digest();
      int j = md.length;
      char[] str = new char[j * 2];
      int k = 0;
      // 移位 输出字符串
      for (byte byte0 : md) {
        str[k++] = hexDigits[byte0 >>> 4 & 0xf];
        str[k++] = hexDigits[byte0 & 0xf];
      }
      return new String(str);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  /**
   * 下载文件
   *
   * @param request  /
   * @param response /
   * @param file     /
   */
  public static void downloadFile(HttpServletRequest request, HttpServletResponse response,
      File file, boolean deleteOnExit) {
    response.setCharacterEncoding(request.getCharacterEncoding());
    response.setContentType("application/octet-stream");
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(file);
      response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
      IOUtils.copy(fis, response.getOutputStream());
      response.flushBuffer();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    } finally {
      if (fis != null) {
        try {
          fis.close();
          if (deleteOnExit) {
            file.deleteOnExit();
          }
        } catch (IOException e) {
          log.error(e.getMessage(), e);
        }
      }
    }
  }

  public static String getMd5(File file) {
    return getMd5(getByte(file));
  }

}