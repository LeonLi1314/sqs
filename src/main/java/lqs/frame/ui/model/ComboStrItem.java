package lqs.frame.ui.model;

/**
 * Combo控件数据项实体 （value和text字段必须是String类型）
 * 
 * @author liqingshan 2015-12-08
 *
 */
public class ComboStrItem {
	private String value;
	private String text;

	/**
	 * 获取数据项对应的值
	 * 
	 * @return 数据项对应的值
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 设置数据项对应的值
	 * 
	 * @param value
	 *            String类型的值
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 获取数据项对应显示的值
	 * 
	 * @return 数据i昂对应显示的值
	 */
	public String getText() {
		return text;
	}

	/**
	 * 设置数据项对应显示的值
	 * 
	 * @param text
	 *            显示的String值
	 */
	public void setText(String text) {
		this.text = text;
	}
}
