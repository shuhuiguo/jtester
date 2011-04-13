package com.xxx.biz.service;

/**
 * ChannelTaskConstant
 * 
 * @author zili.dengzl
 * @since jdk1.5
 */
public interface ChannelTaskConstant { 

    /**
     * 
     */
    public static final String TASK_NO                         = "taskNo";

    /**
     *
     */
    public static final String ORG_ID_PATH                     = "orgIdPath";
    public static final int    ORG_ID_PATH_LENGTH              = 1024;

    /**
     * 
     */
    public static final String TASK_NAME                       = "taskName";
    public static final int    TASK_NAME_LENGTH                = 256;

    /**
     * 
     */
    public static final String SEND_COUNT                      = "sendCount";

    /**
     * 
     */
    public static final String SEND_USER                       = "sendUser";
    public static final int    SEND_USER_LENGTH                = 32;

    /**
     * {@link SendTypeConstant}
     */
    public static final String SEND_TYPE                       = "sendType";
    public static final int    SEND_TYPE_LENGTH                = 64;

    /**
     * 
     */
    public static final String CUSTOMER_FATIGUE_RULES          = "customerFatigueRules";

    /**
     * 
     */
    public static final String BLACKLIST_NAMES                 = "blacklistNames";

    /**
     *
     */
    public static final String EXTERNAL_ID                     = "externalId";
    public static final int    EXTERNAL_ID_LENGTH              = 128;

    /**
     * 
     */
    public static final String PRIORITY                        = "priority";

    /**
     * 
     */
    public static final String IS_DO_SEND_UP_LIMITATION_4_ORG  = "isDoSendUpLimitation4Org";

    /**
     * 
     */
    public static final String IS_DO_SEND_UP_LIMITATION_4_USER = "isDoSendUpLimitation4User";

    /**
     * format:[HOST]:[PORT]:[USER]:[PASSWORD]
     */
    public static final String CHANNEL_SETTING                 = "channelSetting";
    public static final int    CHANNEL_SETTING_LENGTH          = 512;

    /**
     */
    public static final String RECEIVED_COUNT                  = "receivedCount";

    /**
     */
    public static final String SENDED_COUNT                    = "sendedCount";

    /**
     */
    public static final String SUCCESS_COUNT                   = "successCount";

    /**
     */
    public static final String FAILED_COUNT                    = "failedCount";

    /**
     */
    public static final String FATIGUE_FILTERED_COUNT          = "fatigueFilteredCount";

    /**
     */
    public static final String BLACKLIST_FILTERED_COUNT        = "blacklistFilteredCount";

    /**
     */
    public static final String STAUTS                          = "status";
    /**
     */
    public static final String IS_REMOTE_SEND                  = "isRemoteSend";

    /**
     */
    public static final String MAIL_TEMPLATE                   = "mailTemplate";

    /**
     */
    public static final String IS_TEST                         = "isTest";
    /**
     */
    public static final String FATIGUE_ENABLED                 = "fatigueEnabled";

}
