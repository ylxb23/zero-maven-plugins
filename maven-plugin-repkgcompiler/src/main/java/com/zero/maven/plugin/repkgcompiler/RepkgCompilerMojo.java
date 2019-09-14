package com.zero.maven.plugin.repkgcompiler;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * 源文件处理
 * @date 2019年9月8日 下午10:43:41
 * @author zero
 */
@Mojo(name="repkgcompiler", defaultPhase=LifecyclePhase.PROCESS_SOURCES)
public class RepkgCompilerMojo extends AbstractRepkgCompilerMojo {

	
	public void execute() throws MojoExecutionException, MojoFailureException {
		super.execute();
	}

	@Override
	public String getFromPkg() {
		return System.getProperty("repkg.fromPkg");
	}

	@Override
	public String getToPkg() {
		return System.getProperty("repkg.toPkg");
	}

	@Override
	public String getToGroupId() {
		return System.getProperty("repkg.toGroupId");
	}
	
}
