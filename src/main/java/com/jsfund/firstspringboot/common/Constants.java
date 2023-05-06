package com.jsfund.firstspringboot.common;

/**
 * @author Administrator
 * @create 2023/4/30 21:45
 */
public class Constants {

    public static final int NOT_FOUND_DATA_CODE = 6001 ;
    public static final String NOT_FOUND_DATA_MSG = "没有找到对应数据" ;

    public static final int NOT_PARAM_NULL_CODE = 6002 ;
    public static final String NOT_PARAM_NULL_MSG = "请求参数不允许为空" ;

    public static final int DATA_EXISTED_CODE = 6003 ;
    public static final String DATA_EXISTED_MSG = "数据已经存在" ;

    public static final int NOT_ALLOW_NULL_CODE = 6004 ;
    public static final String NOT_ALLOW_NULL_MSG = "不允许为空" ;

    public static final int REQUEST_DATA_INVALID_CODE = 6005;
    public static final String REQUEST_DATA_INVALID_MSG = "请求内容格式不合规";


    /**
     * 参数 count
     */
    public static final String PARAM_COUNT = "count";

    /**
     * 参数 list
     */
    public static final String PARAM_LIST = "list";

    /***
     * 有效状态
     */
    public static final String STATUS_YES = "1";

    /***
     * 无效状态
     */
    public static final String STATUS_NO = "0";

    public static final String P_TITLE_ID = "数据ID";
    public static final String P_TITLE_TARGETNAME = "指标名称";
    public static final String P_TITLE_RULETYPE = "指标类型";
    public static final String P_TITLE_SQLRULE = "指标规则";

    public static String RULE_TYPE_VALUE = "val";
    public static String RULE_TYPE_LIST = "list";
    public static String RULE_TYPE_CHART_DATA = "chartByData";

    /***
     * 变量
     */
    public static final String VAR_STARTTIME = "\\{startTime\\}";
    public static final String VAR_ENDTIME = "\\{endTime\\}";
}
