package com.zero.maven.plugin.repkgcompiler;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.StringUtils;

/**
 * 
 * @date 2019年9月11日 上午12:37:24
 * @author zero
 */
public abstract class AbstractRepkgCompilerMojo extends AbstractMojo {
	
	/**
	 * maven project.
	 */
	@Parameter(defaultValue="${project}", readonly=true)
	private MavenProject project;
	
	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			if(StringUtils.isBlank(getFromPkg())) {
				return ;
			}
			String srcPath = "src/main/java".replace("/", File.separator);
			for(String src : project.getCompileSourceRoots()) {
				if(src.indexOf(srcPath) > 0) {
					getLog().info("Repkgcompiler remove compile source root: " + src);
					project.getCompileSourceRoots().remove(src);
				}
			}
			getLog().info("Repkg compile with src source dir:" + project.getCompileSourceRoots());
		} catch (Exception e) {
			getLog().error("Repkgcompiler error", e);
			throw new MojoExecutionException("Repkgcompiler error", e);
		} finally {
		}
	}
	
	public abstract String getFromPkg();

	public abstract String getToPkg();
	
	public abstract String getToGroupId();
}
