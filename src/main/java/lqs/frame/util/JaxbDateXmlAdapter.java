package lqs.frame.util;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * jaxb组件日期处理格式化
 * 
 * @author liqngshan 2015-12-31
 *
 */
public class JaxbDateXmlAdapter extends XmlAdapter<String, Date> {
	/**
	 * 日期转换为字符串
	 */
	@Override
	public String marshal(Date date) throws Exception {
		return DateUtils.formatDate(date, DatePatterns.POPULAR_DATE24TIME);
	}

	/**
	 * 字符串转化为日期
	 */
	@Override
	public Date unmarshal(String date) throws Exception {
		return DateUtils.parseDate(date, DatePatterns.POPULAR_DATE24TIME);
	}
}
