package org.jtester.assertion.object.intf;

import org.jtester.assertion.common.intf.IBaseAssert;

/**
 * char类型对象断言接口
 * 
 * @author darui.wudr
 * 
 */
public interface ICharacterAssert extends IBaseAssert<Character, ICharacterAssert> {
	ICharacterAssert is(char ch);
}
