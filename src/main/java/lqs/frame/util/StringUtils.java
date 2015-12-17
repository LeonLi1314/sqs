package lqs.frame.util;

/**
 * 字符串工具类
 * 
 * @author liqingshan 2015-12-03
 *
 */
public class StringUtils {
	/**
	 * 判断是否为空或者空字符串
	 * 
	 * @param str
	 *            待判断的字符串
	 * @return true：空或空字符串；false：非空
	 */
	public static boolean isNullOrEmpty(String str) {
		if (str == null)
			return true;
		if (str.trim().length() == 0)
			return true;

		return false;
	}
}
