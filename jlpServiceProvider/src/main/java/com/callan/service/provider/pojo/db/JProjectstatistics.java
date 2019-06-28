package com.callan.service.provider.pojo.db;

import java.util.Date;

public class JProjectstatistics {
    private Long id;

    private Long projectid;

    private String statisticstype;

    private Long statisticstypedataid;

    private Long count;

    private Date createdate;

    private String activeflag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectid() {
        return projectid;
    }

    public void setProjectid(Long projectid) {
        this.projectid = projectid;
    }

    public String getStatisticstype() {
        return statisticstype;
    }

    public void setStatisticstype(String statisticstype) {
        this.statisticstype = statisticstype == null ? null : statisticstype.trim();
    }

    public Long getStatisticstypedataid() {
        return statisticstypedataid;
    }

    public void setStatisticstypedataid(Long statisticstypedataid) {
        this.statisticstypedataid = statisticstypedataid;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getActiveflag() {
        return activeflag;
    }

    public void setActiveflag(String activeflag) {
        this.activeflag = activeflag == null ? null : activeflag.trim();
    }
}