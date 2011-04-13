package com.xxx.biz.service;

/**
 * ChannelConstant
 * 
 * @author zili.dengzl
 * @since jdk1.5
 */
public class ChannelConstant { 

    //common 
    public static final String  SOURCE                   = "source";
    public static final int     SOURCE_LENGTH            = 128;

    public static final String  PASSWORD                 = "password";

    //priority
    public static final Integer HIGH_PRIORITY            = 8;
    public static final Integer MEDIUM_PRIORITY          = 4;
    public static final Integer LOW_PRIORITY             = 2;

    //receiverType
    public static final String  MEMBER_ID                = "memberId";
    public static final String  RECEIVE_ADDRRSS          = "receiveAddress";

    //Result
    public static final String  IS_SUCCESSED             = "isSuccessed";
    public static final String  EXCEPTION_DESC           = "exceptionDesc";
    public static final String  TASK_NO                  = "taskNo";

    //sendType
    public static final String  EMAIL                    = "e_mail";
    public static final String  EFAX                     = "e_fax";
    public static final String  SMS                      = "sms";
    public static final String  DIAL                     = "dial";
    public static final String  ALITALK                  = "alitalk";

    //TaskStatus
    public static final String  READY                    = "ready";
    public static final String  EXECUTING                = "executing";
    public static final String  TRANSMIT_STARTED         = "TransmitStarted";
    public static final String  TRANSMIT_COMPLETED       = "TransmitCompleted";
    public static final String  TASK_COMPLETED           = "taskCompleted";

    //MessageStatus
    public static final String  SUCCESS                  = "success";
    public static final String  FAILED                   = "failed";
    public static final String  FATIGUED                 = "fatigued";

    //taskInfoMap
    public static final String  TASK_INFO                = "taskInfo ";

    //resource types
    public static final String  TYPE_BATCH_SIZE          = "batchSize";
    public static final String  TYPE_QUEUE_FOR_SOURCE    = "queueForSource";
    public static final String  TYPE_MAIL_SENDER_KEY     = "mailSenderKey";
    public static final String  TYPE_MAIL_TRANSPORT_NAME = "mailTransportName";
    public static final String  TYPE_MAIL_RETRY_INFO     = "mailRetryInfo";

    public static final String  DELIMITER_DOT            = ".";

    //alitalk type 
    public static final String  ALITALK_TYPE_SEND        = "send";
    public static final String  ALITALK_TYPE_FLOAT       = "float";
}
