package com.fuyaogroup.harbor.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: jianfeng.zheng
 * @since: 2021/6/2 3:47 下午
 * @history: 1.2021/6/2 created by jianfeng.zheng
 */
public class Repository {
    private Long id;
    private String name;
    private Date creationTime;
    private Date updateTime;
    private Integer pullCount;
    private String projectName;

    private List<Image> images = new ArrayList<Image>();
    private List<Image> devImages = new ArrayList<Image>();
    private List<Image> uatImages = new ArrayList<Image>();
    private List<Image> proImages = new ArrayList<Image>();
    private List<Image> genericImages = new ArrayList<Image>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPullCount() {
        return pullCount;
    }

    public void setPullCount(Integer pullCount) {
        this.pullCount = pullCount;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void addArtifact(Image image) {
        this.images.add(image);
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getShortName() {
        int index = this.name.lastIndexOf("/");
        if (index == -1) {
            return this.name;
        }
        return this.name.substring(index + 1);
    }

    public void imageCategory() {
        for (Image image : this.images) {
            String tag = image.getTag();
            int index = tag.indexOf('.');
            if (index != -1) {
                String env = tag.substring(0, index);
                if ("dev".equals(env)) {
                    this.devImages.add(image);
                } else if ("uat".equals(env)) {
                    this.uatImages.add(image);
                } else if ("pro".equals(env)) {
                    this.proImages.add(image);
                } else {
                    this.genericImages.add(image);
                }
            } else {
                this.genericImages.add(image);
            }
        }
    }

    public List<Image> getDevImages() {
        return devImages;
    }

    public void setDevImages(List<Image> devImages) {
        this.devImages = devImages;
    }

    public List<Image> getUatImages() {
        return uatImages;
    }

    public void setUatImages(List<Image> uatImages) {
        this.uatImages = uatImages;
    }

    public List<Image> getProImages() {
        return proImages;
    }

    public void setProImages(List<Image> proImages) {
        this.proImages = proImages;
    }

    public List<Image> getGenericImages() {
        return genericImages;
    }

    public void setGenericImages(List<Image> genericImages) {
        this.genericImages = genericImages;
    }
}
