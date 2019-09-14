# maven-plugin-repkg

## 功能
将java文件包名替换，设置系统环境变量的`groupId`值。
### 声明周期
process-sources
### 命令
repkg

## 使用方式配置如下:

```xml
<plugin>
    <groupId>com.zero.maven</groupId>
    <artifactId>maven-plugin-repkg</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <executions>
        <execution>
            <id>repkg</id>
            <phase>generate-sources</phase>
            <goals>
                <goal>repkg</goal>
            </goals>
            <configuration>
                <fromPkg>com.zero</fromPkg>
                <toPkg>com.one</toPkg>
                <toGroupId>com.one.maven</toGroupId>
            </configuration>
        </execution>
    </executions>
</plugin>
```

