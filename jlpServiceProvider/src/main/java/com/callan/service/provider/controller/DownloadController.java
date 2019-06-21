package com.callan.service.provider.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.config.FtpUtil;
import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.db.JDownloadfile;
import com.callan.service.provider.pojo.db.JFiletype;
import com.callan.service.provider.pojo.db.JSystemconfig;
import com.callan.service.provider.service.IJDownloadFileService;
import com.callan.service.provider.service.IJFiletypeService;
import com.callan.service.provider.service.IJSystemConfigService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "下载管理")
public class DownloadController {

	@Autowired
	private IJDownloadFileService downloadFileService;

	@Autowired
	private IJSystemConfigService systemConfigService;

	@Autowired
	private IJFiletypeService filetypeService;

	//"pathdb.tjh.com", 22, "ftpuser", "pacs"
	@Value("${pathFtpUrl}")
	private String pathFtpUrl = "pathdb.tjh.com";
	@Value("${pathFtpPort}")
	private int pathFtpPort = 22;
	@Value("${pathFtpUser}")
	private String pathFtpUser = "ftpuser";
	@Value("${pathFtpPwd}")
	private String pathFtpPwd = "pacs";
	
	
	/**
	 * 下载文件
	 * 
	 * @return
	 */
	@ApiOperation(value = "下载文件")
	@RequestMapping(value = "/api/DownLoad/{id}", method = { RequestMethod.GET })
	public String downloadFile(long id, String fileCode, HttpServletResponse response) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String filePath = getLocalPath();
		JDownloadfile downloadfile = downloadFileService.getByIdAndFileCode(id, fileCode);

		JSystemconfig systemConfig = (JSystemconfig) systemConfigService.getByClassTypeAndKeyName("system", "imagebase")
				.getData();

		if (systemConfig != null && StringUtils.isNotBlank(systemConfig.getKeyvalue())) {
			File file = new File(systemConfig.getKeyvalue());
			if (file.exists()) {
				filePath = systemConfig.getKeyvalue();
			}
		}
		if (downloadfile != null || StringUtils.isBlank(downloadfile.getRelativepath())) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("0000");
			baseResponse.setText("下载文件名为空");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.info("response : " + json);
			return json;
		}
		filePath = filePath + File.pathSeparator + downloadfile.getRelativepath();
		log.info("下载路径为：" + filePath);
		if (new File(filePath).exists()) {
			response.setContentType("application/octet-stream");
			// 下载文件能正常显示中文
			try {
				response.setHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(filePath, "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			// 实现文件下载
			byte[] buffer = new byte[1024];
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				fis = new FileInputStream(filePath);
				bis = new BufferedInputStream(fis);
				OutputStream os = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer, 0, i);
					i = bis.read(buffer);
				}
				log.info("Download the song successfully!");
			} catch (Exception e) {
				log.error("Download the song failed!", e);
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("0000");
			baseResponse.setText("下载文件不存在");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.info("response : " + json);
			return json;
		}
		resultMap.put("response", new BaseResponse());

		String json = JSONObject.toJSONString(resultMap);
		log.info("response : " + json);
		return json;
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "获取支持的文件类型")
	@RequestMapping(value = "/api/DownLoad/filetype", method = { RequestMethod.GET })
	public String getAllFileTypes() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<JFiletype> fileTypes = (List<JFiletype>) filetypeService.getAll();
		BaseResponse baseResponse = new BaseResponse();
		resultMap.put("response", baseResponse);
		resultMap.put("filetypes", fileTypes);
		String json = JSONObject.toJSONString(resultMap);
		JLPLog log = ThreadPoolConfig.getBaseContext();
		log.info("response : " + json);
		return json;
	}

	private String getLocalPath() {
		String confFilePath = DownloadController.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		int end = confFilePath.lastIndexOf(".jar");
		if (end == -1) {
			end = confFilePath.length();
		}
		confFilePath = confFilePath.substring(0, end);
		int firstIndex = confFilePath.lastIndexOf(System.getProperty("path.separator")) + 1;
		int lastIndex = confFilePath.lastIndexOf("/") + 1;
		confFilePath = confFilePath.substring(firstIndex, lastIndex);
		return confFilePath;
	}

	/**
	 * 访问图片路径
	 */
	@ApiOperation(value = "访问图片路径")
	@RequestMapping(value = "/api/DownLoad/image/**", method = { RequestMethod.GET })
	public String Get(HttpServletRequest request, HttpServletResponse response) {
		String path = request.getRequestURI();
		path =path.substring(path.indexOf("/image/")+6,path.length());
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String filePath = getBasePath();
		JSystemconfig systemconfig = (JSystemconfig) systemConfigService.getByClassTypeAndKeyName("system", "imagebase")
				.getData();

		if (systemconfig != null && StringUtils.isNotBlank(systemconfig.getKeyvalue())) {
			filePath = systemconfig.getKeyvalue();
		}
		String localDir = filePath + "/image";
		filePath = filePath + "/image" + path;
		
		File file = new File(filePath);

		if (!file.exists()) {
			String localDir1 = filePath.substring(0,filePath.lastIndexOf("/"));
			File localDirFile = new File(localDir1);

			if (!localDirFile.isDirectory()) {
				localDirFile.mkdirs();
			}

			try {
				FTPClient ftp = FtpUtil.ftpConn(pathFtpUrl, pathFtpPort, pathFtpUser	, pathFtpPwd);
				FtpUtil.dowloadFile(log, ftp, localDir, "", path);
			} catch (Exception e) {
				log.error(e);
			}

		}

		file = new File(filePath);

		if (!file.exists()) {

			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("404");
			response.setStatus(404);
			baseResponse.setText("下载文件失败");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.info("response : " + json);
			return json;
		}

		log.info("图像下载路径为：" + filePath);
		if (file.exists()) {
			try {
				response.setContentType("image/jpeg/jpg/png/gif/bmp/tiff/svg");
		        if(file.exists()) {
		            FileInputStream in = new FileInputStream(file);
		            OutputStream os = response.getOutputStream();
		            byte[] b = new byte[1024];
		            while(in.read(b)!= -1) {
		                os.write(b);
		            }
		            in.close();
		            os.flush();
		            os.close();
		        } 
			    BaseResponse baseResponse = new BaseResponse();
				resultMap.put("response", baseResponse);
				String json = JSONObject.toJSONString(resultMap);
				log.info("response : " + json);
				return json;
			}catch(Exception e) {
				log.error(e);
				BaseResponse baseResponse = new BaseResponse();
				baseResponse.setCode("400");
				baseResponse.setText("下载文件失败");
				resultMap.put("response", baseResponse);
				String json = JSONObject.toJSONString(resultMap);
				log.info("response : " + json);
				return json;
			}
			    
		} else {
			log.error("下载文件不存在,filePath: " + filePath);
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode("404");
			response.setStatus(404);
			baseResponse.setText("下载文件失败");
			resultMap.put("response", baseResponse);
			String json = JSONObject.toJSONString(resultMap);
			log.info("response : " + json);
			return json;
		}
	}

	
	private String getBasePath() {
		
		String confFilePath = JLPLog.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		int end = confFilePath.lastIndexOf(".jar");
		if(end == -1) {
			end = confFilePath.length();
		}
		confFilePath = confFilePath.substring(0, end);
		int firstIndex = confFilePath.lastIndexOf(System.getProperty("path.separator")) + 1;
		int lastIndex = confFilePath.lastIndexOf("/") + 1;
		confFilePath = confFilePath.substring(firstIndex, lastIndex);
		
		if(confFilePath.contains("target")) {
			confFilePath = confFilePath + "../../";
		}
		
		return confFilePath;
	}

	
}
