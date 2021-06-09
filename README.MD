## description

java program to clean harbor docker image,recommend jenkins to run

## install

```bash
mvn clean package
```

## run

```bash
java -jar targe/harbor-clean-1.0.jar {harbor-server-url} {harbor-admin} {harbor-admin-password} {reserve-latest-count} {project-white-list} {repository-white-list}
```

## jenkins

you can create a jenkins job item,and trigger with params

![](http://zhengjianfeng.cn/images/SkfL2qhydlveKxHd9a4ebsy9b6f1EeDp.jpg)

## more

for more details, visit [here](https://zhengjianfeng.cn/?p=551)
