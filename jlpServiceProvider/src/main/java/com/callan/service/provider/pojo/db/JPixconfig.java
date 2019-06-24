package com.callan.service.provider.pojo.db;

public class JPixconfig {
    private Long id;

    private String fieldname;

    private Integer weightcoefficient;

    private String activeflag;
    
    private String name;
    
    

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname == null ? null : fieldname.trim();
    }

    public Integer getWeightcoefficient() {
        return weightcoefficient;
    }

    public void setWeightcoefficient(Integer weightcoefficient) {
        this.weightcoefficient = weightcoefficient;
    }

    public String getActiveflag() {
        return activeflag;
    }

    public void setActiveflag(String activeflag) {
        this.activeflag = activeflag == null ? null : activeflag.trim();
    }
}