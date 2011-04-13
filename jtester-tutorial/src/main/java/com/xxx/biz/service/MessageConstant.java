package com.xxx.biz.service;

/**
 * EsbChannelMessageManager
 * 
 * @author zili.dengzl
 * @since jdk1.5
 */
public interface MessageConstant { 

    /**
     */
    public static final String MESSAGE_LIST          = "messageList";
    /**
     */
    public static final String EXTERNAL_ID           = "externalId";

    /**
     */
    public static final String CHARSET               = "charset";

    /**
     */
    public static final String SUBJECT               = "subject";
    public static final int    SUBJECT_LENGTH        = 256;

    /**
     * 
     */
    public static final String HTML_CONTENT          = "htmlContent";

    /**
     *
     */
    public static final String HTML_CONTENT_FILE     = "htmlContentFile";

    /**

     */
    public static final String TEXT_CONTENT          = "textContent";

    /**

     */
    public static final String FROM                  = "from";
    public static final int    FROM_LENGTH           = 512;

    /**

     */
    public static final String REPLY_TO              = "replyTo";
    public static final int    REPLY_TO_LENGTH       = 512;

    /**

     */
    public static final String RECIPIENTS_TO         = "recipientsTo";
    public static final int    RECIPIENTS_TO_LENGTH  = 1024;

    /**
     */
    public static final String RECIPIENTS_CC         = "recipientsCc";
    public static final int    RECIPIENTS_CC_LENGTH  = 2048;

    /**
     */
    public static final String RECIPIENTS_BCC        = "recipientsBcc";
    public static final int    RECIPIENTS_BCC_LENGTH = 512;

    /**
     */
    public static final String ATTACHMENTS           = "attachments";

    /**
     */
    public static final String ATTACHMENT_NAMES      = "attachmentNames";

    /**
     */
    public static final String RECEIVER_ID           = "receiverId";

    /**
     */
    public static final String RECEIVER_ID_TYPE      = "receiverIdType";

    /**
     */
    public static final String MESSAGE_ID            = "messageId";

    /**
     */
    public static final String MESSAGE_SEQUENCE      = "messageSequence";

    /**
     */
    public static final String STATUS                = "status";

    /**
     */
    public static final String GMT_SEND              = "gmtSend";

}
