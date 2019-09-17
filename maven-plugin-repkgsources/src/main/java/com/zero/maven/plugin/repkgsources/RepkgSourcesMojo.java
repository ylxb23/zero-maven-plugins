package com.zero.maven.plugin.repkgsources;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * 源文件处理，拷贝java源文件，将源代码的包名重命名成新的包名，并重命名项目的groupId成新groupId
 * @date 2019年9月8日 下午10:43:41
 * @author zero
 */
@Mojo(name="repkgsources", defaultPhase=LifecyclePhase.GENERATE_SOURCES, threadSafe=true)
public class RepkgSourcesMojo extends AbstractRepkgSourcesMojo {

	/**
	 * 源部分包名
	 */
	@Parameter(property="repkg.fromPkg", defaultValue="", required=true, readonly=false)
	private String fromPkg;
	
	/**
	 * 重命名后的部分包名
	 */
	@Parameter(property="repkg.toPkg", defaultValue="", required=true, readonly=false)
	private String toPkg;
	
	/**
	 * 重命名包名后的groupId
	 */
	@Parameter(property="repkged.toGroupId", defaultValue="", required=true, readonly=false)
	private String toGroupId;
	
	
	public void execute() throws MojoExecutionException, MojoFailureException {
		super.execute();
	}

	@Override
	public String getFromPkg() {
		return fromPkg;
	}

	public void setFromPkg(String fromPkg) {
		this.fromPkg = fromPkg;
	}

	@Override
	public String getToPkg() {
		return toPkg;
	}

	public void setToPkg(String toPkg) {
		this.toPkg = toPkg;
	}

	public String getToGroupId() {
		return toGroupId;
	}

	public void setToGroupId(String toGroupId) {
		this.toGroupId = toGroupId;
	}
	
}
