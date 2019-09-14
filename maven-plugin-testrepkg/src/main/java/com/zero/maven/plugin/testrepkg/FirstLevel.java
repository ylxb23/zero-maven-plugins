package com.zero.maven.plugin.testrepkg;

import com.zero.maven.plugin.testrepkg.inner.SecondLevel;
import com.zero.maven.testrepkg.CommonFirstLevel;

/**
 * @author zero
 */
public class FirstLevel {
	private int level;
	
	private String pkg;
	
	private SecondLevel inner;
	
	private CommonFirstLevel cfl;

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

	public SecondLevel getInner() {
		return inner;
	}

	public void setInner(SecondLevel inner) {
		this.inner = inner;
	}

	public CommonFirstLevel getCfl() {
		return cfl;
	}

	public void setCfl(CommonFirstLevel cfl) {
		this.cfl = cfl;
	}
	
}
