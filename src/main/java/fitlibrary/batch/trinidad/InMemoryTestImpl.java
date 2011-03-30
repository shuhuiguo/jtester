/*
 * Adapted from Gojko Adsic's trinidad to add parallelism and changes to the report
 */
package fitlibrary.batch.trinidad;

public class InMemoryTestImpl implements TestDescriptor {
	private String name;
	private String content;

	// @Override
	public String getName() {
		return this.name;
	}

	// @Override
	public String getContent() {
		return this.content;
	}

	public InMemoryTestImpl(String name, String content) {
		this.name = name;
		this.content = content;
	}
}