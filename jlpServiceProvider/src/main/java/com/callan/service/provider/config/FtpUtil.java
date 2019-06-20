package com.callan.service.provider.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil {

	public static FTPClient ftpConn(String addr, String username, String password) throws Exception {
		return ftpConn(addr, 21, username, password);
	}

	/**
	 * 连接FTP
	 *
	 * @param addr     ftp服务器地址
	 * @param port     端口
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 * @throws Exception
	 */
	public static FTPClient ftpConn(String addr, int port, String username, String password) throws Exception {

		FTPClient ftp = new FTPClient();

		try {
			ftp.connect(addr, port);
		} catch (Exception ex) {
			throw new Exception("FTP地址/端口号错误或网络异常：" + ExceptionUtils.getFullStackTrace(ex));
		}

		if (!ftp.login(username, password)) {
			ftp.logout();
			throw new Exception("FTP用户名密码错误");
		}
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		ftp.setControlEncoding("UTF-8");
		// ftp.enterLocalPassiveMode();
		// ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
		int reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			throw new Exception("FTP连接错误");
		}
		return ftp;
	}

	/**
	 *
	 * @param fileName 上传文件名称
	 * @param in       上传文件流
	 * @return
	 * @throws Exception
	 */
	public static boolean ftpFileUpload(FTPClient ftp, String fileName, InputStream in) throws Exception {
		// String curDir = getCurrentDir(ftp);
		boolean upload = false;
		String LOCAL_CHARSET = "GBK";
		String SERVER_CHARSET = "ISO-8859-1";
		try {
			if (null != ftp) {
				// ftp.setBufferSize(1024);
				// ftp.setControlEncoding("GBK");
				// ftp.setFileType(FTP.BINARY_FILE_TYPE);
				// ftp.enterLocalPassiveMode();
				// 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
				if (FTPReply.isPositiveCompletion(ftp.sendCommand("OPTS UTF8", "ON"))) {
					LOCAL_CHARSET = "UTF-8";
				}
				ftp.setControlEncoding(LOCAL_CHARSET);
				ftp.enterLocalPassiveMode();
				// ftp.setFileType(FTP.BINARY_FILE_TYPE);
				// 上传文件时，文件名称需要做编码转换
				// fileName = new String(fileName.getBytes(LOCAL_CHARSET), SERVER_CHARSET);
				fileName = new String(fileName.getBytes("utf-8"), SERVER_CHARSET);
				// String remoteFullPath = curDir + "/" + fileName;
				upload = ftp.storeFile(fileName, in);
			} else {
				throw new RuntimeException("ftp 未连接。。");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			in.close();
		}
		return upload;
	}

	/**
	 *
	 * @param fileName 上传文件名称
	 * @param in       上传文件流
	 * @return
	 * @throws Exception
	 */
	public static boolean ftpFileUpload(FTPClient ftp, String fileName, InputStream in, int fileType) throws Exception {
		// String curDir = getCurrentDir(ftp);
		boolean upload = false;
		String LOCAL_CHARSET = "GBK";
		String SERVER_CHARSET = "ISO-8859-1";
		try {
			if (null != ftp) {
				// ftp.setBufferSize(1024);
				// ftp.setControlEncoding("GBK");
				// ftp.setFileType(FTP.BINARY_FILE_TYPE);
				// ftp.enterLocalPassiveMode();
				// 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
				if (FTPReply.isPositiveCompletion(ftp.sendCommand("OPTS UTF8", "ON"))) {
					LOCAL_CHARSET = "UTF-8";
				}
				ftp.setControlEncoding(LOCAL_CHARSET);
				ftp.enterLocalPassiveMode();
				ftp.setFileType(fileType);
				// 上传文件时，文件名称需要做编码转换
				// fileName = new String(fileName.getBytes(LOCAL_CHARSET), SERVER_CHARSET);
				fileName = new String(fileName.getBytes("utf-8"), SERVER_CHARSET);
				// String remoteFullPath = curDir + "/" + fileName;
				upload = ftp.storeFile(fileName, in);
			} else {
				throw new RuntimeException("ftp 未连接。。");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			in.close();
		}
		return upload;
	}

	/**
	 *
	 * @param fileName 上传文件名称
	 * @param in       上传文件流
	 * @param encode   上传文件编码
	 * @return
	 * @throws Exception
	 */
	public static boolean ftpFileUpload(FTPClient ftp, String fileName, InputStream in, String encode)
			throws Exception {
		String curDir = getCurrentDir(ftp);
		boolean upload = false;
//       String LOCAL_CHARSET = "GBK";
		String SERVER_CHARSET = "ISO-8859-1";
		try {
			if (null != ftp) {
				// ftp.setBufferSize(1024);
				// ftp.setControlEncoding("GBK");
				// ftp.setFileType(FTP.BINARY_FILE_TYPE);
				// ftp.enterLocalPassiveMode();
				// 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
//               if (FTPReply.isPositiveCompletion(ftp.sendCommand("OPTS UTF8", "ON"))) {
//                   LOCAL_CHARSET = "UTF-8";
//               }
				ftp.setControlEncoding(encode);
				ftp.setFileType(FTP.ASCII_FILE_TYPE);
				ftp.enterLocalPassiveMode();
				// ftp.setFileType(FTP.BINARY_FILE_TYPE);
				// 上传文件时，文件名称需要做编码转换
				// fileName = new String(fileName.getBytes(LOCAL_CHARSET), SERVER_CHARSET);
				fileName = new String(fileName.getBytes("utf-8"), encode);
				// String remoteFullPath = curDir + "/" + fileName;
				upload = ftp.storeFile(fileName, in);
			} else {
				throw new RuntimeException("ftp 未连接。。");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			in.close();
		}
		return upload;
	}

	/**
	 * @description
	 * @author guojun
	 * @param ftp
	 * @param remoteFileName ftp上的文件名
	 * @param out            本地文件
	 * @return
	 * @throws Exception boolean 下载文件
	 */
	public static boolean ftpFileDownload(FTPClient ftp, String remoteFileName, String localFileName, JLPLog log)
			throws Exception {
//        String curDir = getCurrentDir(ftp);
		boolean dowload = false;
		String LOCAL_CHARSET = "GBK";
		OutputStream out = null;
		try {
			out = new	FileOutputStream(new File(localFileName));
			if (null != ftp) {
				// 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
				if (FTPReply.isPositiveCompletion(ftp.sendCommand("OPTS UTF8", "ON"))) {
					LOCAL_CHARSET = "UTF-8";
				}
				log.info("LOCAL_CHARSET--" + LOCAL_CHARSET);
				ftp.setControlEncoding(LOCAL_CHARSET);
				ftp.enterRemotePassiveMode();
				ftp.setFileType(FTP.BINARY_FILE_TYPE);
				// 上传文件时，文件名称需要做编码转换
				// fileName = new String(fileName.getBytes(LOCAL_CHARSET), SERVER_CHARSET);
				remoteFileName = new String(remoteFileName.getBytes("utf-8"), "iso-8859-1");
				ftp.enterLocalPassiveMode();
				dowload = ftp.retrieveFile(remoteFileName, out);
				out.flush();
//                replyCode = ftp.getReplyCode();
				// ftp.getReplyCode();
			} else {
				log.error("ftp 未连接。。");
				throw new RuntimeException("ftp 未连接。。");
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		} finally {
			out.close();
		}
		return dowload;
	}

	/**
	 * @description
	 * @author guojun
	 * @param ftp
	 * @param oldPath
	 * @param newPath
	 * @param fileName void 文件移动
	 */
	public static void moveFile(FTPClient ftp, String oldPath, String newPath, String fileName) throws Exception {
		String LOCAL_CHARSET = "GBK";
		String SERVER_CHARSET = "ISO-8859-1";
		try {
			if (null != ftp) {
				// 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
				if (FTPReply.isPositiveCompletion(ftp.sendCommand("OPTS UTF8", "ON"))) {
					LOCAL_CHARSET = "UTF-8";
				}
				ftp.setControlEncoding(LOCAL_CHARSET);
				ftp.enterRemotePassiveMode();
				ftp.setFileType(FTP.BINARY_FILE_TYPE);
				// 上传文件时，文件名称需要做编码转换
				// fileName = new String(fileName.getBytes(LOCAL_CHARSET), SERVER_CHARSET);
				ftp.rename(fileName, getFileFullName(newPath, fileName));

			} else {
				throw new RuntimeException("ftp 未连接。。");
			}
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	public static void renameFile(FTPClient ftp, String oldName, String newName) throws Exception {
		String LOCAL_CHARSET = "GBK";
		String SERVER_CHARSET = "ISO-8859-1";
		try {
			if (null != ftp) {
				// 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
				if (FTPReply.isPositiveCompletion(ftp.sendCommand("OPTS UTF8", "ON"))) {
					LOCAL_CHARSET = "UTF-8";
				}
				ftp.setControlEncoding(LOCAL_CHARSET);
				ftp.enterRemotePassiveMode();
				ftp.setFileType(FTP.BINARY_FILE_TYPE);
				// 文件名称需要做编码转换

				oldName = new String(oldName.getBytes("utf-8"), SERVER_CHARSET);
				newName = new String(newName.getBytes("utf-8"), SERVER_CHARSET);

				ftp.rename(oldName, newName);

			} else {
				throw new RuntimeException("ftp 未连接。。");
			}
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	/**
	 * 强制切换当前目录,目录不存在时逐级创建、切换
	 *
	 * @param ftp ftp客户端
	 * @param dir 切换的目标路径
	 */
	public static void forceChangeDir(FTPClient ftp, String dir) throws IOException {
		boolean isexist = ftp.changeWorkingDirectory(dir);

		if (!isexist) {
			String[] paths = dir.split("/");
			// Condition 'paths != null' is always 'true'
			for (String path : paths) {
				if (StringUtils.isNotBlank(path)) {
					changeToNextLevel(ftp, path);
				}
			}
		}
	}

	/**
	 * 强制切换到指定绝对路径
	 * <p>
	 * 兼容无权限访问根目录且目标路径不在用户默认目录下的情况
	 * <p>
	 * 注：需要对目标路径有权限
	 *
	 * @param ftp          ftp连接
	 * @param absolutePath 绝对路径
	 * @throws IOException IO异常
	 */
	public static void forceChangeAbsoluteDir(FTPClient ftp, String absolutePath) throws IOException {
		if (StringUtils.isBlank(absolutePath) || !absolutePath.startsWith("/")) {
			throw new IOException("指定路径不是绝对路径：" + absolutePath);
		}
		if (!ftp.changeWorkingDirectory(absolutePath)) {
			// 按单斜杠分隔，忽略双斜杠，如 dfs://path/path1 分隔为["dfs://path","path1"]
			String[] paths = absolutePath.split("(?<!/)/(?!/)");
			ftp.changeWorkingDirectory("/");
			// 测试某FTP服务器时发现，无权限访问根目录时，changeWorkingDirectory("/")返回的仍是true，需要比较当前路径来判断
			if ("/".equals(ftp.printWorkingDirectory())) {
				// 有权限访问根目录
				for (String path : paths) {
					if (StringUtils.isNotBlank(path)) {
						changeToNextLevel(ftp, path);
					}
				}
			} else {
				StringBuilder parentPath = new StringBuilder("/");
				boolean enterParentPath = false;
				for (String path : paths) {
					if (StringUtils.isNotBlank(path)) {
						if (enterParentPath) {
							changeToNextLevel(ftp, path);
							continue;
						}
						parentPath.append(path).append("/");
						ftp.changeWorkingDirectory(parentPath.toString());
						if (parentPath.toString().equals(ftp.printWorkingDirectory() + "/")) {
							// 切换到有权限的最高一层目录
							enterParentPath = true;
						}
					}
				}
			}
		}
		if (!absolutePath.endsWith("/")) {
			absolutePath += "/";
		}
		if (!absolutePath.equals(ftp.printWorkingDirectory() + "/")
				&& !absolutePath.equals("/" + ftp.printWorkingDirectory() + "/")) {
			throw new IOException("强制切换指定绝对路径失败" + absolutePath + ",当前路径：" + ftp.printWorkingDirectory());
		}
	}

	/**
	 * 切换到下一层的指定目录
	 *
	 * @param ftp  ftp连接
	 * @param path 指定目录
	 * @throws IOException 切换失败
	 */
	public static void changeToNextLevel(FTPClient ftp, String path) throws IOException {
		if (!ftp.changeWorkingDirectory(path)) {
			if (!ftp.makeDirectory(path)) {
				throw new IOException("无法创建目录：" + path + ",当前路径：" + ftp.printWorkingDirectory());
			}
			if (!ftp.changeWorkingDirectory(path)) {
				throw new IOException("无法切换到目录：" + path + ",当前路径：" + ftp.printWorkingDirectory());
			}
		}
	}

	// 关闭ftp连接
	public static void closeFtp(FTPClient ftp) throws IOException {
		if (null != ftp) {
			if (ftp.isConnected()) {
				ftp.logout();
				ftp.disconnect();
			}
		}
	}

 
 
	// 下载ftp文件到本地
	public static void dowloadFile(JLPLog log, FTPClient ftpClient, String localDir, String ftpDir, String fileName)
			throws Exception {
		log.info( "开始下载文件 [" +ftpDir + fileName + "]");
		String localFile = getFileFullName(localDir, fileName);
		// 下载文件
		log.info("localFile : " + localFile);
		
//		FileOutputStream outputStream = new FileOutputStream(new File(localFile));
		if (FtpUtil.ftpFileDownload(ftpClient, fileName, localFile, log)) {
			log.info("下载文件 [" + fileName + "] 成功.");
		} else {
			log.info("下载文件 [" + fileName + "] 失败.");
		}
	}


	public static String getFileFullName(String dir, String fileName) {
		if (dir.endsWith("/")) {
			return dir + fileName;
		} else {
			return dir + "/" + fileName;
		}
	}

	// 获取当前路径
	public static String getCurrentDir(FTPClient ftpClient) {
		String dir = "";
		try {
			dir = ftpClient.printWorkingDirectory();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dir;
	}


	/**
	 *
	 * @param log
	 * @param localFileName
	 * @param remoteFileName
	 * @param ftp
	 * @return true:上传成功
	 */
	public static boolean uploadFile(JLPLog log, String localFileName, String remoteFileName, FTPClient ftp) {

		try {
			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			return uploadZhFile(log, localFileName, remoteFileName, ftp);
		} catch (Exception e) {
			return uploadZhFile(log, localFileName, remoteFileName, ftp);
		}
	}

	public static boolean uploadZhFile(JLPLog log, String localFileName, String remoteFileName, FTPClient ftp) {
		String charsetName = "ISO-8859-1";
		FileInputStream is = null;
		try {// 兼容中文名
			is = new FileInputStream(localFileName);
			return ftp.storeFile(new String(remoteFileName.getBytes(), charsetName), is);
		} catch (Exception e) {
			throw new RuntimeException("上传文件异常:" + localFileName, e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.warn("关闭文件失败");
				}
			}
		}
	}

	public static boolean isExist(FTPClient ftpClient, String fileName) {
		try {
			String newName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			FTPFile[] tmp = ftpClient.listFiles(newName);
			if (tmp.length > 0) {

				return true;
			}
		} catch (Exception e) {
			throw new RuntimeException("判断文件是否存在失败", e);
		}

		return false;

	}
  
	private static final String FILE_SEP1 = "/";
	private static final String FILE_SEP2 = "\\";

	/**
	 * 创建目录，支持多级目录创建.文件路径分隔符只支持 "\"和"/";创建了该方法后未经过验证，不建议使用，如果要使用请自行验证
	 *
	 * @param ftp
	 * @param path
	 * @return true：创建成功；false：创建失败
	 * @exception FTPConnectionClosedException If the FTP server prematurely closes
	 *                                         the connection as a result of the
	 *                                         client being idle or some other
	 *                                         reason causing the server to send FTP
	 *                                         reply code 421. This exception may be
	 *                                         caught either as an IOException or
	 *                                         independently as itself.
	 * @exception IOException                  If an I/O error occurs while either
	 *                                         sending a command to the server or
	 *                                         receiving a reply from the server.
	 *
	 */
	@Deprecated
	public static boolean mkdir(FTPClient ftp, String path) throws IOException {
		if (ftp.makeDirectory(path)) {
			return true;
		}
		String parentPath = parentPath(path);
		if ("".equals(parentPath)) {
			return false;
		}
		if (mkdir(ftp, parentPath)) {
			return mkdir(ftp, path);
		}
		return false;
	}

	private static String parentPath(String path) {
		trimEndPath(path);
		if ("".equals(path) || FILE_SEP1.equals(path) || FILE_SEP2.equals(path)) {
			return "";
		}
		int sp1Idx = path.lastIndexOf(FILE_SEP1);
		int sp2Idx = path.lastIndexOf(FILE_SEP2);
		int idx = sp1Idx > sp1Idx ? sp1Idx : sp2Idx;
		return path.substring(0, idx);
	}

	private static String trimEndPath(String path) {
		if (path == null) {
			return "";
		}
		while (path.endsWith(FILE_SEP1) || path.endsWith(FILE_SEP2)) {
			path = path.substring(0, path.length() - 1);
		}
		return path;
	}

}
