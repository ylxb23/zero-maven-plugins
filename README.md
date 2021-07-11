# zero-maven-plugins
My maven plugins

## maven-plugin-repkg**
重新命名包名插件包括`maven-plugin-repkgsources`和`maven-plugin-repkgcompiler`两个模块，分别作用域mvn生命周期的source和compiler阶段。功能是将特定打包成新的包名，譬如原包名"com.zero.abc"可以将结果打包成"com.one.abc"，原包名和目标包名通过配置指定，使用方法参见`maven-plugin-testrepkg`的pom.xml文件，通过以下命令作用生效：
```
mvn package -P REPKGED
```

