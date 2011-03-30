package org.jtester.module.tracer;

import java.io.File;
import java.io.InputStream;

import org.jtester.module.tracer.TracerSequece;
import org.jtester.testng.JTester;
import org.jtester.utility.ResourceUtil;
import org.testng.annotations.Test;

@Test(groups = { "jtester" })
public class TracerSequeceTest extends JTester {

	@Test
	public void generateSequence() throws Exception {
		InputStream in = ResourceUtil.findClassPathStream(TracerSequeceTest.class, "generateSequence.txt");
		String sequenceDescription = ResourceUtil.convertStreamToString(in);
		in.close();
		File outFile = File.createTempFile("sequence", ".jpg");
		// String outFileName = "d:/ttt.jpg";
		TracerSequece.generateJpg(sequenceDescription, outFile);
		want.file(outFile).isExists();
		outFile.delete();
	}
}
