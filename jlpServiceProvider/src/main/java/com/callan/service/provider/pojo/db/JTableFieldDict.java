package com.callan.service.provider.pojo.db;

public class JTablefielddict {
    private Long id;

    private String tablecode;

    private String code;

    private String name;

    private String type;

    private String description;

    private Integer sortno;

    private String controltypecode;

    private String nullable;

    private String dictname;

    private String queryflag;

    private String showflag;

    private String sortflag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTablecode() {
        return tablecode;
    }

    public void setTablecode(String tablecode) {
        this.tablecode = tablecode == null ? null : tablecode.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }

    public String getControltypecode() {
        return controltypecode;
    }

    public void setControltypecode(String controltypecode) {
        this.controltypecode = controltypecode == null ? null : controltypecode.trim();
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable == null ? null : nullable.trim();
    }

    public String getDictname() {
        return dictname;
    }

    public void setDictname(String dictname) {
        this.dictname = dictname == null ? null : dictname.trim();
    }

    public String getQueryflag() {
        return queryflag;
    }

    public void setQueryflag(String queryflag) {
        this.queryflag = queryflag == null ? null : queryflag.trim();
    }

    public String getShowflag() {
        return showflag;
    }

    public void setShowflag(String showflag) {
        this.showflag = showflag == null ? null : showflag.trim();
    }

    public String getSortflag() {
        return sortflag;
    }

    public void setSortflag(String sortflag) {
        this.sortflag = sortflag == null ? null : sortflag.trim();
    }
}