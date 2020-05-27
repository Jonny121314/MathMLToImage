package com.hongrant.www.achieve.comm.util;

/**
 * 公共常量
 * @author yangshaoxin
 *
 */
public class CommConstant {
	
	/**
	 * UTF-8字符集
	 */
	public static final String ENCODING_UTF_8 = "UTF-8";
	
	/**
	 * ISO-8859-1字符集
	 */
	public static final String ENCODING_ISO_8859_1 = "ISO-8859-1";
	
	/**
	 * 系统用户
	 */
	public static final Integer SYSTEM_USER_ID = -1;
	
	/**
	 * 默认客户
	 */
	public static final Integer DEFAULT_CUSTOMER_ID = -1;
	
	/**
	 * 默认联系人
	 */
	public static final Integer DEFAULT_CONTACT_ID = -1;
	
	/**
	 * 精确到小数点以后0位
	 */
	public static final int SCALE_0 = 0;
	
	/**
	 * 精确到小数点以后2位
	 */
	public static final int SCALE_2 = 2;
	
	/**
	 * 精确到小数点以后5位
	 */
	public static final int SCALE_5 = 5;
	
	/**
	 * 正常/活动状态
	 */
	public static final String STATE_ACTIVITY = "A";
	
	/**
	 * 新建状态
	 */
	public static final String STATE_NEW = "N";
	
	/**
	 * 计划状态
	 */
	public static final String STATE_PLAN = "P";
	
	/**
	 * 制作中状态
	 */
	public static final String STATE_MAKEING = "M";
	
	/**
	 * 完成状态
	 */
	public static final String STATE_COMPLETE = "C";
	
	/**
	 * 关闭状态
	 */
	public static final String STATE_CLOSE = "B";
	
	/**
	 * 未结清状态
	 */
	public static final String STATE_UNSETTLE = "W";
	
	/**
	 * 结算（支付）中
	 */
	public static final String STATE_SETTLING = "Z";
	
	/**
	 * 已结清状态
	 */
	public static final String STATE_SETTLED = "Y";
	
	/**
	 * 冻结状态
	 */
	public static final String STATE_FROZEN = "F";
	
	/**
	 * 对账中状态
	 */
	public static final String STATE_STATMENT = "D";
	
	/**
	 * 已调整
	 */
	public static final String STATE_ADJUSTED = "d";
	
	/**
	 * 无效状态
	 */
	public static final String STATE_INVALID = "X";
	
	/**
	 * 现金支付
	 */
	public static final String PAYMENT_CASH = "1";
	
	/**
	 * 微信支付
	 */
	public static final String PAYMENT_WECHAT = "2";
	
	/**
	 * 支付宝支付
	 */
	public static final String PAYMENT_ALIPAY = "3";
	
	/**
	 * 预存款支付
	 */
	public static final String PAYMENT_PRE_DEPOSIT = "4";
	
	/**
	 * 银行对账支付
	 */
	public static final String PAYMENT_BANK_STATEMENT = "5";
	
	/**
	 * 公司银行账号支付
	 */
	public static final String PAYMENT_PUBLIC_BANK_ACCOUNT = "6";
	
	/**
	 * 私人银行账号支付
	 */
	public static final String PAYMENT_PRIVATE_BANK_ACCOUNT = "7";
	
	/**
	 * 仓库编码前缀
	 */
	public static final String CODE_PREFIX_CK = "CK";
	
	/**
	 * 产品类型编码前缀
	 */
	public static final String CODE_PREFIX_CL = "CL";
	
	/**
	 * 物料类型编码前缀
	 */
	public static final String CODE_PREFIX_ST = "ST";
	
	/**
	 * 物料编码前缀
	 */
	public static final String CODE_PREFIX_SP = "SP";
	
	/**
	 * 客户编码前缀
	 */
	public static final String CODE_PREFIX_C = "C";
	
	/**
	 * 联系人编码前缀
	 */
	public static final String CODE_PREFIX_P = "P";
	
	/**
	 * 供应商编码前缀
	 */
	public static final String CODE_PREFIX_G = "G";
}
