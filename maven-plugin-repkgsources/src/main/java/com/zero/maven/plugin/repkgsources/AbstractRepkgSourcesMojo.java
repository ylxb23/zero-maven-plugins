package com.zero.maven.plugin.repkgsources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * 
 * @date 2019年9月11日 上午12:37:24
 * @author zero
 */
public abstract class AbstractRepkgSourcesMojo extends AbstractMojo {

	/**
	 * 源文件路径，默认{@code ${basedir}/src/main/java/}
	 */
	@Parameter(property="repkg.sourceDir", defaultValue="${basedir}/src/main/java/", required=true, readonly=false)
	private String sourceDir;
	
	/**
	 * 重命名包名之后的源文件路径，默认{@code ${basedir}/target/repkged/java/}
	 */
	@Parameter(property="repkg.targetDir", defaultValue="${basedir}/target/repkged/java/", required=true, readonly=false)
	private String targetDir;
	
	/**
	 * maven project.
	 */
	@Parameter(defaultValue="${project}", readonly=true)
	private MavenProject project;
	
	protected String fromPkgPath;
	protected String toPkgPath;
	protected String toGroupId;
	
	protected final String CHARSET = System.getProperty("project.build.sourceEncoding", "UTF-8");
	
	private static int count = 0;
	
	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			if(StringUtils.isBlank(getFromPkg()) || StringUtils.isBlank(getToPkg())) {
				getLog().warn("Repkg not configurated fromPkg and toPkg, repkg failed.");
				return ;
			}
			if(StringUtils.isBlank(getToGroupId())) {
				getLog().warn("Repkg not configurated toGroupId, repkg failed.");
				return ;
			}
			File source = new File(sourceDir);
			if(!source.exists()) {
				getLog().warn("Repkg source dir[" + sourceDir + "] is not exists");
			}
			File target = new File(targetDir);
			if(target.exists()) {
				target.delete();
			}
			// format path
			String sdf = source.getAbsolutePath();
			String tdf = target.getAbsolutePath();
			fromPkgPath = StringUtils.replace(getFromPkg(), ".", File.separator);
			toPkgPath = StringUtils.replace(getToPkg(), ".", File.separator);
			
			String groupId = project.getGroupId();
			String toGroupId = getToGroupId();
			getLog().info("Repkg configurated groupId[" + groupId + " -> " + toGroupId + "]");
			project.setGroupId(toGroupId);
			
			System.setProperty("repkg.fromPkg", getFromPkg());
			System.setProperty("repkg.toPkg", getToPkg());
			System.setProperty("repkg.toGroupId", toGroupId);
			System.setProperty("repkg.targetPath", tdf);
			count = 0;
			sourceJavaCopy(sdf, tdf, getFromPkg(), getToPkg());
			getLog().info("Repkged " + count + " source files from [" + getFromPkg() + "] to [" + getToPkg() + "] from [" + sourceDir + ", to [" + targetDir + "]");
			project.addCompileSourceRoot(tdf);
			
		} catch (Exception e) {
			getLog().error("Repkg error", e);
			throw new MojoExecutionException("Repkg error", e);
		}
	}
	
	private void sourceJavaCopy(final String sourceDir, final String targetDir, final String fromPkg, final String toPkg) throws MojoFailureException {
		// clean target dir
		File target = new File(targetDir);
		if(target.exists() || target.isFile()) {
			target.delete();
		}
		getLog().info("Repkg target dir[" + target.getAbsolutePath() + "] cleaned");
		target.mkdirs();
		// check source dir
		File source = new File(sourceDir);
		if(!source.exists()) {
			return;
		} else if(source.isFile()) {
			getLog().warn("Repkg java source dir[" + source.getAbsolutePath() + "] is not a directory.");
			return;
		}
		// copy and rename files
		copyAndPickinSourceJavaFiles(source, target, sourceDir, targetDir, fromPkg, toPkg);
	}

	private void copyAndPickinSourceJavaFiles(File source, File target, final String sourceDir, final String targetDir, String pkgFrom, String pkgTo) throws MojoFailureException {
		try {
			walkFileList(source, sourceDir, targetDir, pkgFrom, pkgTo);
		} catch (IOException e) {
			getLog().error("Repkg process file error, source dir:" + source.getAbsolutePath(), e);
			throw new MojoFailureException("Repkg process file error", e);
		}
	}
	
	private void walkFileList(File source, final String sourceDir, final String targetDir, final String pkgFrom, final String pkgTo) throws IOException {
		if(source.isDirectory()) {
			for(File item : source.listFiles()) {
				walkFileList(item, sourceDir, targetDir, pkgFrom, pkgTo);
			}
		} else {
			processSourceFile(source, sourceDir, targetDir, pkgFrom, pkgTo);
		}
	}
	
	private void processSourceFile(File sourceFile, final String sourceDir, final String targetDir, final String pkgFrom, final String pkgTo) throws IOException {
		String sourceFileName = sourceFile.getName();
		String targetFilePath = StringUtils.replace(StringUtils.replace(sourceFile.getAbsolutePath(), sourceDir, targetDir), fromPkgPath, toPkgPath);
		getLog().debug("Repkg process source file:" + sourceFile.getAbsolutePath() + ", target file:" + targetFilePath);
		File targetFile = new File(targetFilePath);
		if(!sourceFileName.endsWith(".java")) {
			FileUtils.copyFile(sourceFile, targetFile);
			return ;
		}
		count++;
		File parentTargetFile = targetFile.getParentFile();
		if(!parentTargetFile.exists()) {
			getLog().debug("Create dir:" + parentTargetFile.getAbsolutePath());
			parentTargetFile.mkdirs();
		}
		targetFile.createNewFile();
		final String lineSeparator = System.getProperty("line.separator");
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile), CHARSET));
				FileOutputStream fos = new FileOutputStream(targetFile);
				OutputStreamWriter osw = new OutputStreamWriter(fos, CHARSET);){
	        StringBuffer buffer = new StringBuffer();
	        String line = null;
	        while((line = reader.readLine()) != null) { // 顺序读
	        	if(ignoreProcessLine(line, pkgFrom)) {
	        		buffer.append(line).append(lineSeparator);
	        	} else {
	        		buffer.append(StringUtils.replace(line, pkgFrom, pkgTo)).append(lineSeparator);
	        	}
	        }
	        osw.write(buffer.toString());
	        osw.flush();
	    }
	}

	
	private static boolean ignoreProcessLine(String line, String fromPkg) {
		// empty, blank, commented
		return StringUtils.isEmpty(line) | isBeCommentedLine(line) | !line.contains(fromPkg);
	}
	
	private static final Pattern COMMENTED_LINE_PATTERN = Pattern.compile("\\s*\\/\\*[\\w\\W]*?\\*\\/|\\/\\/.*");
	private static boolean isBeCommentedLine(String line) {
		if(StringUtils.isNotEmpty(line)) {
			if(COMMENTED_LINE_PATTERN.matcher(line).find()) {
				return true;
			}
		}
		return false;
	}

	public abstract String getFromPkg();

	public abstract String getToPkg();
	
	public abstract String getToGroupId();
}
