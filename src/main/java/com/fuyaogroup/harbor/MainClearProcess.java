package com.fuyaogroup.harbor;

import com.fuyaogroup.harbor.model.Image;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description:
 * @author: jianfeng.zheng
 * @since: 2021/6/2 3:05 下午
 * @history: 1.2021/6/2 created by jianfeng.zheng
 */
public class MainClearProcess {

    public static void main(String[] cmd) {
        if (cmd.length < 5) {
            MainClearProcess.help();
            return;
        }
        pringArgs(cmd);
        String harborServer = cmd[0];
        String harborAdmin = cmd[1];
        String harborPassword = cmd[2];

        HarborClient client = new HarborClient(harborServer, harborAdmin, harborPassword);
        HarborClearHandler handler = new HarborClearHandler(client);
        handler.doClear(createClearPolicy(cmd));

//        String harborServer = "https://harbor-test.fuyaogroup.com";
//        String harborAdmin = "admin";
//        String harborPassword = "fysam12345";
//        HarborClient client = new HarborClient(harborServer, harborAdmin, harborPassword);
//        Image image = new Image();
//        image.setProjectName("k2");
//        image.setRepositoryName("pai-core-interface");
//        image.setDigest("sha256:a1b2a59492d2ab8465e713e6806de162232c2999528c2c3943bc57c74e7c6121");
//        client.deleteImage(image);
    }

    /**
     * 清除策略，可以设置以下配置项
     *
     * @param cmd
     * @return
     */
    private static ClearPolicy createClearPolicy(String[] cmd) {
        Integer maxReserveCount = Integer.parseInt(cmd[3]);
        String projectWhiteList = cmd[4];
        String repositoryWhiteList = cmd[5];
        ClearPolicy policy = new ClearPolicy();
        policy.setReserveImagesCount(maxReserveCount);
        if (projectWhiteList != null && projectWhiteList.length() > 0) {
            Set<String> skipProjects = new HashSet<String>();
            skipProjects.addAll(Arrays.asList(projectWhiteList.split(",")));
            policy.setWhiteProjects(skipProjects);
        }
        if (repositoryWhiteList != null && repositoryWhiteList.length() > 0) {
            Set<String> skipRepository = new HashSet<String>();
            skipRepository.addAll(Arrays.asList(repositoryWhiteList.split(",")));
            policy.setWhiteRepositories(skipRepository);
        }
        return policy;
    }

    private static void pringArgs(String[] cmd) {
        System.out.println("harborServer===>" + cmd[0]);
        System.out.println("admin===>" + cmd[1]);
        System.out.println("password===>" + cmd[2]);
        System.out.println("reserverCount===>" + cmd[3]);
        System.out.println("projectWhite===>" + cmd[4]);
        System.out.println("repositoryWhite===>" + cmd[5]);
    }

    private static void help() {
        System.out.println("用法:");
        System.out.println("java -jar harbor-clear.jar {harbor地址} {管理员账号} {管理员密码} {最大保留镜像数} {项目白名单} {仓库白名单}");
    }
}
