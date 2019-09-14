package com.zero.maven.plugin.testrepkg.inner;

import com.zero.maven.testrepkg.inner.CommonSecondLevel;

/**
 * 
 * @author zero
 */
public class SecondLevel {
	
	private int level;
	
	private String pkg;
	
	private CommonSecondLevel csl;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public CommonSecondLevel getCsl() {
		return csl;
	}

	public void setCsl(CommonSecondLevel csl) {
		this.csl = csl;
	}
	
}
