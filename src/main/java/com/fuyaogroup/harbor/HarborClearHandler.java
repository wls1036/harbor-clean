package com.fuyaogroup.harbor;

import com.alibaba.fastjson.JSON;
import com.fuyaogroup.harbor.model.Image;
import com.fuyaogroup.harbor.model.Project;
import com.fuyaogroup.harbor.model.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @author: jianfeng.zheng
 * @since: 2021/6/2 4:19 下午
 * @history: 1.2021/6/2 created by jianfeng.zheng
 */
public class HarborClearHandler {

    private HarborClient client;

    public HarborClearHandler(HarborClient harborClient) {
        this.client = harborClient;
    }

    public void doClear(ClearPolicy policy) {
        List<Project> projects = this.getProjects();
        //被保留的镜像
        List<Image> reserves = new ArrayList<Image>();
        //即将被删除的镜像
        List<Image> clears = new ArrayList<Image>();
        for (Project project : projects) {
            if (policy.skipProject(project)) {
                System.out.println("跳过项目===>" + project.getName());
                continue;
            }
            for (Repository repository : project.getRepositories()) {
                if (policy.skipRepository(repository)) {
                    System.out.println("跳过仓库===>" + repository.getName());
                    continue;
                }

                //计算通用镜像
                for (int i = 0; i < repository.getGenericImages().size(); ++i) {
                    if (i < policy.getReserveImagesCount()) {
                        reserves.add(repository.getGenericImages().get(i));
                    } else {
                        clears.add(repository.getGenericImages().get(i));
                    }
                }

                //计算开发环境镜像
                for (int i = 0; i < repository.getDevImages().size(); ++i) {
                    if (i < policy.getReserveImagesCount()) {
                        reserves.add(repository.getDevImages().get(i));
                    } else {
                        clears.add(repository.getDevImages().get(i));
                    }
                }

                //计算uat环境镜像
                for (int i = 0; i < repository.getUatImages().size(); ++i) {
                    if (i < policy.getReserveImagesCount()) {
                        reserves.add(repository.getUatImages().get(i));
                    } else {
                        clears.add(repository.getUatImages().get(i));
                    }
                }

                //计算生产环境镜像
                for (int i = 0; i < repository.getProImages().size(); ++i) {
                    if (i < policy.getReserveImagesCount()) {
                        reserves.add(repository.getProImages().get(i));
                    } else {
                        clears.add(repository.getProImages().get(i));
                    }
                }
            }
        }
        System.out.println("保留镜像数目======>" + reserves.size());
        System.out.println("删除镜像数目======>" + clears.size());
        System.out.println("\r\n\r\n");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<以下镜像将被保留>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("\r\n\r\n");
        for (Image image : reserves) {
            System.out.println(String.format("%s/%s:%s", image.getProjectName(), image.getRepositoryName(), image.getTag()));
        }
        System.out.println("\r\n\r\n");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<以下镜像将被删除>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("\r\n\r\n");
        for (Image image : clears) {
            System.out.println(String.format("%s/%s:%s", image.getProjectName(), image.getRepositoryName(), image.getTag()));
        }

        System.out.println("\r\n\r\n");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<开始删除镜像>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("\r\n\r\n");
        for (Image image : clears) {
            System.out.println("删除镜像=====>" + image.getFullName());
            this.client.deleteImage(image);
        }
    }


    private List<Project> getProjects() {
        List<Project> projects = client.getProjects();
        for (Project project : projects) {
            List<Repository> repositories = this.getProjectRepositories(project.getName());
            for (Repository item : repositories) {
                item.imageCategory();
            }
            project.setRepositories(repositories);
        }
        return projects;
    }

    private List<Repository> getProjectRepositories(String projectName) {
        List<Repository> repositories = client.getRepository(projectName);
        for (Repository item : repositories) {
            item.setProjectName(projectName);
            item.setImages(this.getImages(projectName, item.getShortName()));
        }
        return repositories;
    }

    private List<Image> getImages(String projectName, String repositoryName) {
        List<Image> images = client.getImages(projectName, repositoryName);
        for (Image item : images) {
            item.setProjectName(projectName);
            item.setRepositoryName(repositoryName);
        }
        return images;
    }
}
