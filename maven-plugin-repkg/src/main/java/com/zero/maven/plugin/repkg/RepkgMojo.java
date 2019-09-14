package com.zero.maven.plugin.repkg;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * 源文件处理
 * @date 2019年9月8日 下午10:43:41
 * @author zero
 */
@Mojo(name="repkg", defaultPhase=LifecyclePhase.GENERATE_SOURCES)
public class RepkgMojo extends AbstractRepkgMojo {

	@Parameter(property="repkg.fromPkg", defaultValue="${repkg.fromPkg}", required=true, readonly=false)
	private String fromPkg;
	
	@Parameter(property="repkg.toPkg", defaultValue="${repkg.toPkg}", required=true, readonly=false)
	private String toPkg;
	
	@Parameter(property="repkg.toGroupId", defaultValue="${repkg.toGroupId}", required=true, readonly=false)
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
