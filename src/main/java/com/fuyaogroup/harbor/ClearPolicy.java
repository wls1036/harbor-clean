package com.fuyaogroup.harbor;

import com.fuyaogroup.harbor.model.Project;
import com.fuyaogroup.harbor.model.Repository;

import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @author: jianfeng.zheng
 * @since: 2021/6/2 5:43 下午
 * @history: 1.2021/6/2 created by jianfeng.zheng
 */
public class ClearPolicy {
    private int reserveImagesCount;

    /**
     * 白名单项目
     */
    private Set<String> whiteProjects;

    /**
     * 白名单仓库
     */
    private Set<String> whiteRepositories;

    public int getReserveImagesCount() {
        return reserveImagesCount;
    }

    public void setReserveImagesCount(int reserveImagesCount) {
        this.reserveImagesCount = reserveImagesCount;
    }

    public Set<String> getWhiteProjects() {
        return whiteProjects;
    }

    public void setWhiteProjects(Set<String> whiteProjects) {
        this.whiteProjects = whiteProjects;
    }

    public Set<String> getWhiteRepositories() {
        return whiteRepositories;
    }

    public void setWhiteRepositories(Set<String> whiteRepositories) {
        this.whiteRepositories = whiteRepositories;
    }

    public boolean skipProject(Project project) {
        return whiteProjects != null && whiteProjects.contains(project.getName());
    }

    public boolean skipRepository(Repository repository) {
        return whiteRepositories != null && whiteRepositories.contains(repository.getShortName());
    }
}
