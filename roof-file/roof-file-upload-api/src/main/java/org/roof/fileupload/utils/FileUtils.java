package org.roof.fileupload.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * File Utils
 * @author hzliuxin1 Created on 2012-8-22
 */
public class FileUtils {

	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

	private static final int BUFFER_SIZE = 16 * 1024;

	/**
	 * 将文件读取为字符流
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] readFile(File file) {
		byte[] data = null;
		try (InputStream in = new FileInputStream(file)) {
			data = new byte[in.available()];
			in.read(data);
		} catch (FileNotFoundException e) {
			logger.error("错误:",e.getCause());
		} catch (IOException e) {
			logger.error("错误:",e.getCause());
		}
		return data;
	}

	/**
	 * 将字节流写入到文件
	 * 
	 * @param b
	 * @param file
	 */
	public static void writeToFile(byte[] b, File file) {
		try (FileOutputStream writer = new FileOutputStream(file);) {
			if (!file.exists()) {
				file = createFile(file.getPath());
			}
			writer.write(b);
		} catch (IOException e) {
			logger.error("错误:",e.getCause());
		}
	}

	public static void copy(File src, File dst) {
		mkDirs(dst.getPath());
		try (InputStream in = new FileInputStream(src); OutputStream out = new FileOutputStream(dst);) {
			copy(in, out);
		} catch (Exception e) {
			logger.error("错误:",e.getCause());
		}
	}

	public static void copy(InputStream in, OutputStream out) {
		try (BufferedInputStream bufferedInputStream = new BufferedInputStream(in, BUFFER_SIZE);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out, BUFFER_SIZE)) {
			byte[] buffer = new byte[BUFFER_SIZE];
			while (bufferedInputStream.read(buffer) > 0) {
				bufferedOutputStream.write(buffer);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}


	/**
	 * 取得文件扩展名
	 * 
	 * @param fileName
	 *            文件全名
	 * @return 文件扩展名(例如: .gif)
	 */
	public static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

	/**
	 * 格式化路径 删除开始的"/" 增加 结尾的"/"
	 * 
	 * @param path
	 *            格式化后的路径
	 */
	public static String formatePath(String path) {
		if (path.startsWith(File.separator)) {
			path = path.substring(1);
		}
		if (!path.endsWith(File.separator)) {
			path += File.separator;
		}
		String os = System.getProperties().getProperty("os.name");
		if (os.startsWith("Linux")) {
			path = "/" + path;
		}
		logger.info("os:" + os + ":" + path);
		return path;
	}

	// 创建文件
	public static File createFile(String path) throws IOException {
		File file;
		file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	// 创建目录
	public static void mkDirs(String path) {
		File file = new File(path);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
	}

	/**
	 * 在文件夹下加入今天的日期(yyyyMMdd)为名称的子文件夹 <br />
	 * 有则返回没有则创建
     *
	 * @param path
	 *            相对目录
	 * @return 加入日期文件夹的目录
	 */
	public static String addDateDir(String path) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new Date());
		date = date + File.separator;
		String fullPath = path + date;
		return fullPath;
	}

    /**
     * 删除文件
     *
     * @param path 文件目录
     * @return 是否删除成功
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);
        return deleteFile(file);
    }


    public static boolean deleteFile(File file) {
        return file.exists() && file.isFile() && file.delete();
    }

    /**
     * 删除文件夹及文件夹下的所有文件
     *
     * @param path 文件目录
     */
    public static void deleteDir(String path) {
        File file = new File(path);
        deleteDir(file);
        file.delete();
    }

    public static void deleteDir(File dir) {
        if (dir.isFile()) {
            deleteFile(dir);
            return;
        }
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            dir.delete();
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                deleteDir(file);
                file.delete();
            } else {
                deleteFile(file);
            }
        }
    }
	/**
	 * 读取文件夹下所有子目录下的后缀文件，并将其保存到list集合中。
	 * 
	 * @param url
	 *            目录路径
	 * @return List 文件集合
	 * @param suffix
	 *            查找的文件后缀
	 * @throws IOException
	 */
	public static List<String> readFolder(String url, List<String> list, String suffix) throws IOException {
		if (list == null) {
			list = new ArrayList<String>();
		}
		File file = new File(url);

		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					readFolder(files[i].getPath(), list, suffix);
				} else {
					if (files[i].getName().endsWith(suffix)) {
						list.add(files[i].getName());// 文件名
						// list.add(files[i].getAbsolutePath());
						// list.add(files[i].getCanonicalPath());
					}
				}
			}
		}
		return list;
	}

    /**
     * 获取文件的行数
     *
     * @param path 文件路径
     * @return 文件行数
     */
    public static int lineNumber(String path) {
        try (FileReader fileReader = new FileReader(path);
             LineNumberReader lineNumberReader = new LineNumberReader(fileReader)) {
            lineNumberReader.skip(Long.MAX_VALUE);
            int lineNumber = lineNumberReader.getLineNumber();
            lineNumber++;
            return lineNumber;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

	public static List<String> readCurrFolder(String url, String suffix) throws IOException {
		List<String> list = new ArrayList<String>();
		File file = new File(url);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().endsWith(suffix)) {
					list.add(files[i].getName());
				}
			}
		}
		return list;
	}

	/**
	 * 读取文件内容
	 * 
	 * @param file
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String readContent(String file, String charset) throws IOException {
		StringBuffer text = new StringBuffer();
		try (InputStreamReader fr = new InputStreamReader(new FileInputStream(file), charset);
				BufferedReader br = new BufferedReader(fr);) {
			String s = null;
			while ((s = br.readLine()) != null) {
				text.append(s + "\n");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.info(file + "文件读取完毕:\n" + text);
		return text.toString();
	}

	/**
	 * 备份旧的文件
	 */
	public static void bakFile(String oldPath, String newPath, boolean isDel) {
		File dst = new File(newPath);
		File dir = new File(dst.getParent());
		// 如果目录不存在,则创建目录;
		if (!dir.exists()) {
			dir.mkdirs();
		}
		if (!dst.exists()) {
			try {
				dst.createNewFile();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		try (FileInputStream fold = new FileInputStream(oldPath);
				FileOutputStream fnew = new FileOutputStream(newPath);) {

			byte[] b = new byte[512];
			int n;
			while ((n = fold.read(b)) != -1) {
				fnew.write(b, 0, n);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (isDel) {
				File f = new File(oldPath);
				f.delete();
			}
		}
		logger.info("文件已经备份到:" + newPath);
	}

}
