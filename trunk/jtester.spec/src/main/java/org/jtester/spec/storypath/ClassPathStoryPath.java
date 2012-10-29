package org.jtester.spec.storypath;

import java.io.InputStream;
import java.util.List;

import org.jtester.spec.JSpec;
import org.jtester.spec.annotations.StoryFile;
import org.jtester.spec.annotations.StoryType;
import org.jtester.spec.scenario.JSpecScenario;
import org.jtester.tools.commons.StringHelper;

/**
 * 从本地classpath读取jspec用例描述信息
 * 
 * @author darui.wudr 2012-5-18 上午10:10:55
 */
public class ClassPathStoryPath extends StoryPath {
	static final String DOT_REGEX = "\\.";
	static final String SLASH = "/";
	static final String EMPTY = "";
	static final String DEFAULT_EXTENSION = ".story";

	private String path;
	private String name;
	private ClassLoader classLoader;

	public ClassPathStoryPath(Class<? extends JSpec> claz) {
		this.path = this.resolveDirectory(claz);
		this.name = this.resolveName(claz);
		this.classLoader = claz.getClassLoader();
	}

	String getStoryFile(StoryFile story) {
		String file = story == null ? "" : story.value().trim();
		if (StringHelper.isEmpty(file)) {
			StringBuffer sb = new StringBuffer();
			if (path.length() > 0) {
				sb.append(path).append(SLASH);
			}
			sb.append(name);
			sb.append(DEFAULT_EXTENSION);
			return sb.toString();
		} else if (file.startsWith("classpath:")) {
			return file.substring(10);
		} else {
			StringBuffer sb = new StringBuffer();
			if (path.length() > 0) {
				sb.append(path).append(SLASH);
			}
			sb.append(file);
			return sb.toString();
		}
	}

	protected String resolveDirectory(Class<? extends JSpec> claz) {
		Package pack = claz.getPackage();
		if (pack != null) {
			return pack.getName().replaceAll(DOT_REGEX, SLASH);
		}
		return EMPTY;
	}

	@Override
	public List<JSpecScenario> getStory(StoryFile story, String encoding) {
		String storyFile = this.getStoryFile(story);
		InputStream stream = classLoader.getResourceAsStream(storyFile);
		if (stream == null) {
			throw new RuntimeException("Story path '" + storyFile + "' not found by class loader " + classLoader);
		}
		StoryType type = getStoryType(story);
		List<JSpecScenario> list = JSpecScenario.parseFrom(type, stream, encoding);
		this.clean();
		return list;
	}

	protected String resolveName(Class<? extends JSpec> claz) {
		return claz.getSimpleName();
	}

	private void clean() {
		this.classLoader = null;
		this.path = null;
		this.name = null;
	}
}
