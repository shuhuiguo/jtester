package org.jtester.junit;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * 空文件，具体文件在 jtester.junit 包中，打包时排除
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings({ "rawtypes" })
public class JTesterRunner extends BlockJUnit4ClassRunner {
	public JTesterRunner(Class testClass) throws InitializationError {
		super(testClass);
	}
}
