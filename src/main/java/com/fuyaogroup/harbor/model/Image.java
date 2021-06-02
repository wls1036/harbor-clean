package com.fuyaogroup.harbor.model;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: jianfeng.zheng
 * @since: 2021/6/2 3:52 下午
 * @history: 1.2021/6/2 created by jianfeng.zheng
 */
public class Image implements Comparator<Image> {
    private String digest;
    private Date pullTime;
    private Date pushTime;
    private Long size;
    private List<Map> tags;
    private String repositoryName;
    private String projectName;
    private String tag;
    private String fullName;


    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Date getPullTime() {
        return pullTime;
    }

    public void setPullTime(Date pullTime) {
        this.pullTime = pullTime;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getTag() {
        if (this.tags != null && this.tag == null) {
            this.tag = (String) this.tags.get(0).get("name");
        }
        return this.tag;
    }

    public void setTags(List<Map> tags) {
        this.tags = tags;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFullName() {
        if (this.fullName == null) {
            this.fullName = String.format("%s/%s:%s", this.getProjectName(), this.getRepositoryName(), this.getTag());
        }
        return fullName;
    }

    public int compare(Image image1, Image image2) {
        return image1.getFullName().compareTo(image2.getFullName());
    }
}
