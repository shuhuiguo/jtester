package org.jtester.assertion.object.impl;

import java.io.File;

import ext.jtester.hamcrest.Matcher;

import org.jtester.assertion.common.impl.BaseAssert;
import org.jtester.assertion.object.intf.IFileAssert;
import org.jtester.matcher.file.FileExistsMatcher;
import org.jtester.matcher.file.FileMatchers;
import org.jtester.matcher.file.FileExistsMatcher.FileExistsMatcherType;

public class FileAssert extends BaseAssert<File, IFileAssert> implements IFileAssert {
	public FileAssert() {
		super(IFileAssert.class);
		this.valueClaz = File.class;
	}

	public FileAssert(File file) {
		super(file, IFileAssert.class);
		this.valueClaz = File.class;
	}

	public IFileAssert isExists() {
		FileExistsMatcher matcher = new FileExistsMatcher((File) this.value, FileExistsMatcherType.ISEXISTS);
		return this.assertThat(matcher);
	}

	public IFileAssert unExists() {
		FileExistsMatcher matcher = new FileExistsMatcher((File) this.value, FileExistsMatcherType.UNEXISTS);
		return this.assertThat(matcher);
	}

	public IFileAssert nameContain(String expected) {
		Matcher<?> matcher = FileMatchers.nameContain(expected);
		return this.assertThat(matcher);
	}

	public IFileAssert nameEq(String expected) {
		Matcher<?> matcher = FileMatchers.nameEq(expected);
		return this.assertThat(matcher);
	}
}
