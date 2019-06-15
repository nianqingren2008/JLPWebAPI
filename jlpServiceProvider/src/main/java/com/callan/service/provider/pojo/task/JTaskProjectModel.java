package com.callan.service.provider.pojo.task;

import java.util.List;

import com.callan.service.provider.pojo.advanceQueryBase.QueryConds;

public class JTaskProjectModel {
	
    // 任务名称
    private String name;
    
    // 课题ID
    private long projectId ;
    
    // 任务类型（1:下载 2:统计）
    private int exportType ;
    
    // 数据状态过滤条件
    private long[] projectstatusIDs ;

    // 查询条件
    private List<QueryConds> queryConds;

    // 导出字段ID
    private String[] queryShowFields ;
    
    // 导出文件类型
    private short fileTypeId ;
    
    // 数据导出策略ID
    private int dataExportClass ;
    
    // 导出标签数据
    private int[] tags ;
    
    // 标签转置
    private int[] tagTransposes ;

    
    // 图像导出策略
    private int imageExportClass ;

    // 图像导出数据分类
    private String[] modalities ;
    
    

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public int getExportType() {
		return exportType;
	}

	public void setExportType(int exportType) {
		this.exportType = exportType;
	}

	public long[] getProjectstatusIDs() {
		return projectstatusIDs;
	}

	public void setProjectstatusIDs(long[] projectstatusIDs) {
		this.projectstatusIDs = projectstatusIDs;
	}

	public List<QueryConds> getQueryConds() {
		return queryConds;
	}

	public void setQueryConds(List<QueryConds> queryConds) {
		this.queryConds = queryConds;
	}

	public String[] getQueryShowFields() {
		return queryShowFields;
	}

	public void setQueryShowFields(String[] queryShowFields) {
		this.queryShowFields = queryShowFields;
	}

	 

	public short getFileTypeId() {
		return fileTypeId;
	}

	public void setFileTypeId(short fileTypeId) {
		this.fileTypeId = fileTypeId;
	}

	public int getDataExportClass() {
		return dataExportClass;
	}

	public void setDataExportClass(int dataExportClass) {
		this.dataExportClass = dataExportClass;
	}

	public int[] getTags() {
		return tags;
	}

	public void setTags(int[] tags) {
		this.tags = tags;
	}

	public int[] getTagTransposes() {
		return tagTransposes;
	}

	public void setTagTransposes(int[] tagTransposes) {
		this.tagTransposes = tagTransposes;
	}

	public int getImageExportClass() {
		return imageExportClass;
	}

	public void setImageExportClass(int imageExportClass) {
		this.imageExportClass = imageExportClass;
	}

	public String[] getModalities() {
		return modalities;
	}

	public void setModalities(String[] modalities) {
		this.modalities = modalities;
	}
    
    

}
