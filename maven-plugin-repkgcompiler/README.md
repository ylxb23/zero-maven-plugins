# maven-plugin-repkgcompiler

## 功能
重置maven插件`compileSourceRoots`值，使编译内容限制在重命名后的java文件范围。结合maven原生的`jar`和`install`插件，将重命名包名的打包结果增加`classifier`，并将`jar`包安装到本地maven仓库。`jar`和`install`的触发需要在执行`install-file`时进行。
即完整的打包命令为:
```bash
mvn clean install
```

### 声明周期
process-sources
### 命令
repkgcompiler

### 使用方式配置如下:

```xml
<plugin>
    <groupId>com.zero.maven</groupId>
    <artifactId>maven-plugin-repkgcompiler</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <executions>
        <execution>
            <id>repkgcompiler</id>
            <phase>process-sources</phase>
            <goals>
                <goal>repkgcompiler</goal>
            </goals>
        </execution>
    </executions>
</plugin>
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jar-plugin</artifactId>
    <version>3.1.2</version>
    <configuration>
        <classifier>${repkged.classifier}</classifier>
    </configuration>
</plugin>
<plugin>
   <groupId>org.apache.maven.plugins</groupId>
   <artifactId>maven-install-plugin</artifactId>
   <version>2.4</version>
   <executions>
       <execution>
           <id>install-file</id>
           <goals>
               <goal>install-file</goal>
           </goals>
           <phase>install</phase>
           <configuration>
               <groupId>com.one.maven</groupId>
               <artifactId>${project.artifactId}</artifactId>
               <version>${project.version}</version>
               <classifier>${repkged.classifier}</classifier>
               <packaging>jar</packaging>
               <generatePom>true</generatePom>
               <file>${basedir}/target/${project.build.finalName}-${repkged.classifier}.jar</file>
           </configuration>
       </execution>
   </executions>
</plugin>
```

### 可以通过自定义条件触发，配置如下：

```xml
    <profiles>
        <profile>
            <id>REPKGED</id>
            <properties>
                <repkged.classifier>repkged</repkged.classifier>
            </properties>
            <activation>
                <activeByDefault>false</activeByDefault>
            </activation>
            
            <build>
                <plugins>
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
            
                    <plugin>
                        <groupId>com.zero.maven</groupId>
                        <artifactId>maven-plugin-repkgcompiler</artifactId>
                        <version>0.0.1-SNAPSHOT</version>
                        <executions>
                            <execution>
                                <id>repkgcompiler</id>
                                <phase>process-sources</phase>
                                <goals>
                                    <goal>repkgcompiler</goal>
                                </goals>
                            </execution>
                        </executions>
                    </plugin>
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-jar-plugin</artifactId>
                        <version>3.1.2</version>
                        <configuration>
                            <classifier>${repkged.classifier}</classifier>
                        </configuration>
                    </plugin>
                    <plugin>
                       <groupId>org.apache.maven.plugins</groupId>
                       <artifactId>maven-install-plugin</artifactId>
                       <version>2.4</version>
                       <executions>
                           <execution>
                               <id>install-file</id>
                               <goals>
                                   <goal>install-file</goal>
                               </goals>
                               <phase>install</phase>
                               <configuration>
                                   <groupId>com.one.maven</groupId>
                                   <artifactId>${project.artifactId}</artifactId>
                                   <version>${project.version}</version>
                                   <classifier>${repkged.classifier}</classifier>
                                   <packaging>jar</packaging>
                                   <generatePom>true</generatePom>
                                   <file>${basedir}/target/${project.build.finalName}-${repkged.classifier}.jar</file>
                               </configuration>
                           </execution>
                       </executions>
                    </plugin>
                </plugins>
            </build>
        </profile>
    </profiles>

```
