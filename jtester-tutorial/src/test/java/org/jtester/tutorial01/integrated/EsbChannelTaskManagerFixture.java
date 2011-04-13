package org.jtester.tutorial01.integrated;

import java.util.HashMap;

import net.sf.json.JSONObject;

import org.jtester.fit.fixture.DtoPropertyFixture;
import org.jtester.utility.StringHelper;

import com.xxx.biz.service.ChannelConstant;
import com.xxx.biz.service.ChannelTaskConstant;
import com.xxx.biz.service.EsbResult;
import com.xxx.biz.service.EsbService;

public class EsbChannelTaskManagerFixture extends DtoPropertyFixture {

	public void createChannelTask(ChannelTaskDto dto) {
		// 这里调用你的实际接口进行测试
		EsbResult result = EsbService.callEsbService(dto.getChannelTaskMap());
		want.object(result).notNull();
		want.bool(result.isSuccess()).isEqualTo("返回结果必须是" + dto.esbResult, dto.esbResult);
	}

	public static class ChannelTaskDto {
		boolean esbResult;
		String source;
		String password;
		String channelTask;

		String orgIdPath;
		String taskName;
		String sendUser;
		int sendCount;
		String sendType;
		String customerFatigueRules;
		boolean fatigueEnabled;
		String blacklistNames;
		boolean isRemoteSend;
		String isDoSendUpLimitation4Org;
		String isDoSendUpLimitation4User;
		String externalId;
		String priority;
		String channelSetting;
		boolean isTest;

		String map;

		public HashMap<String, Object> getChannelTaskMap() {
			if (channelTask == null || channelTask.equalsIgnoreCase("null")) {
				return null;
			}
			HashMap<String, Object> channelTask = new HashMap<String, Object>();
			// default value
			channelTask.put(ChannelTaskConstant.ORG_ID_PATH, "10/1");
			channelTask.put(ChannelTaskConstant.TASK_NAME, "jialemailtask");
			channelTask.put(ChannelTaskConstant.SEND_USER, "le.jial");
			channelTask.put(ChannelTaskConstant.SEND_COUNT, 10);
			channelTask.put(ChannelTaskConstant.SEND_TYPE, ChannelConstant.EMAIL);
			// test case value
			channelTask.put(ChannelTaskConstant.ORG_ID_PATH, orgIdPath);

			channelTask.put(ChannelTaskConstant.TASK_NAME, taskName);
			channelTask.put(ChannelTaskConstant.SEND_USER, sendUser);
			channelTask.put(ChannelTaskConstant.SEND_COUNT, sendCount);
			channelTask.put(ChannelTaskConstant.SEND_TYPE, sendType);
			channelTask.put(ChannelTaskConstant.CUSTOMER_FATIGUE_RULES, new String[] { customerFatigueRules });
			channelTask.put(ChannelTaskConstant.FATIGUE_ENABLED, fatigueEnabled);
			channelTask.put(ChannelTaskConstant.BLACKLIST_NAMES, StringHelper.isBlankOrNull(blacklistNames) ? null
					: new String[] { blacklistNames });

			channelTask.put(ChannelTaskConstant.IS_REMOTE_SEND, isRemoteSend);

			channelTask.put(ChannelTaskConstant.EXTERNAL_ID, externalId);
			channelTask.put(ChannelTaskConstant.PRIORITY, priority);
			channelTask.put(ChannelTaskConstant.CHANNEL_SETTING, channelSetting);
			channelTask.put(ChannelTaskConstant.IS_TEST, isTest);

			return channelTask;
		}

		public String toString() {
			StringBuffer buff = new StringBuffer();

			buff.append("\n").append("source:").append(source);
			buff.append("\n").append("password:").append(password);
			// json
			buff.append("\n").append("channelTask:").append(JSONObject.fromObject(getChannelTaskMap()).toString());

			return buff.toString();
		}
	}
}
