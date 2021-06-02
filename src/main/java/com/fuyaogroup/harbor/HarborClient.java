package com.fuyaogroup.harbor;

import com.alibaba.fastjson.JSON;
import com.fuyaogroup.harbor.model.Image;
import com.fuyaogroup.harbor.model.Project;
import com.fuyaogroup.harbor.model.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: jianfeng.zheng
 * @since: 2021/6/2 3:40 下午
 * @history: 1.2021/6/2 created by jianfeng.zheng
 */
public class HarborClient {

    private static final Integer MAX_REQUEST_ITEM = 100;
    private static final String API_VERSION = "/api/v2.0";
    private static final String API_GET_REPOSITORY = "/projects/%s/repositories?page_size=100&page=%d";
    private static final String API_GET_PROJECT = "/projects?page_size=100&page=%d";
    private static final String API_GET_ARTIFACT = "/projects/%s/repositories/%s/artifacts?page_size=100&page=%d&with_tag=true";
    private static final String API_DELETE_IAMGE = "/projects/%s/repositories/%s/artifacts/%s";

    private String harborServer;
    private String userName;
    private String password;

    public HarborClient(String harborServer, String userName, String password) {
        this.harborServer = harborServer;
        this.userName = userName;
        this.password = password;
    }

    public List<Project> getProjects() {
        List<Project> projects = new ArrayList<Project>();
        int page = 1;
        while (true) {
            String content = this.requestRestApi("GET", API_GET_PROJECT, page++);
            List<Project> items = JSON.parseArray(content, Project.class);
            projects.addAll(items);
            if (items.size() != MAX_REQUEST_ITEM) {
                break;
            }
        }
        return projects;
    }

    public List<Repository> getRepository(String projectName) {
        List<Repository> repositories = new ArrayList<Repository>();
        int page = 1;
        while (true) {
            String content = this.requestRestApi("GET", API_GET_REPOSITORY, projectName, page++);
            List<Repository> items = JSON.parseArray(content, Repository.class);
            repositories.addAll(items);
            if (items.size() != MAX_REQUEST_ITEM) {
                break;
            }
        }
        return repositories;
    }

    public List<Image> getImages(String projectName, String repositoryName) {
        List<Image> images = new ArrayList<Image>();
        int page = 1;
        while (true) {
            String content = this.requestRestApi("GET", API_GET_ARTIFACT, projectName, repositoryName, page++);
            List<Image> items = JSON.parseArray(content, Image.class);
            images.addAll(items);
            if (items.size() != MAX_REQUEST_ITEM) {
                break;
            }
        }
        return images;
    }

    public void deleteImage(Image image) {
        this.requestRestApi("DELETE", API_DELETE_IAMGE, image.getProjectName(), image.getRepositoryName(), image.getDigest());
    }

    private String requestRestApi(String method, String api, Object... params) {
        HttpRequest request = this.createRequest(method, api, params);
        String body = request.body("utf-8");
        return body;
    }


    private HttpRequest createRequest(String method, String api, Object... params) {
        HttpRequest request = null;
        String url = this.getURL(api, params);
        if ("GET".equals(method)) {
            request = HttpRequest.get(url);
        } else if ("POST".equals(method)) {
            request = HttpRequest.post(url);
        } else if ("DELETE".equals(method)) {
            request = HttpRequest.delete(url);
        }
        request.connectTimeout(3000)
                .readTimeout(5000)
                .basic(this.userName, this.password);
        return request;
    }

    private String getURL(String api, Object... params) {
        return harborServer + API_VERSION + String.format(api, params);
    }


}
