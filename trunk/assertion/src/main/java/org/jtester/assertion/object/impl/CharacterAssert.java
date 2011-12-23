package org.jtester.assertion.object.impl;

import org.jtester.assertion.common.impl.AllAssert;
import org.jtester.assertion.object.intf.ICharacterAssert;

public class CharacterAssert extends AllAssert<Character, ICharacterAssert> implements ICharacterAssert {

	public CharacterAssert() {
		super(ICharacterAssert.class);
		this.valueClaz = Character.class;
	}

	public CharacterAssert(Character value) {
		super(value, ICharacterAssert.class);
		this.valueClaz = Character.class;
	}

	public ICharacterAssert is(char ch) {
		return super.isEqualTo(ch);
	}
}
