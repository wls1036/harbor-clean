package com.fuyaogroup.harbor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: jianfeng.zheng
 * @since: 2021/6/2 3:45 下午
 * @history: 1.2021/6/2 created by jianfeng.zheng
 */
public class Project {
    private Long projectId;
    private String name;

    private List<Repository> repositories = new ArrayList<Repository>();

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }

    public void addRepository(Repository repository) {
        this.repositories.add(repository);
    }
}
