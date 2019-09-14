package com.zero.maven.testrepkg;

import com.zero.maven.testrepkg.inner.CommonSecondLevel;

/**
 * @author zero
 */
public class CommonFirstLevel {
	private int level;
	
	private String pkg;
	
	private CommonSecondLevel inner;

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

	public CommonSecondLevel getInner() {
		return inner;
	}

	public void setInner(CommonSecondLevel inner) {
		this.inner = inner;
	}
	
}
