package lqs.frame.ui.model;

/**
 * Combo控件数据项实体 2015-12-08
 * 
 * @author liqingshan
 *
 * @param <ValueType>
 *            值字段的类型
 * @param <TextType>
 *            显示字段的类型
 */
public class ComboItem<ValueType, TextType> {
	private ValueType value;
	private TextType text;

	/**
	 * 获取数据项的值成员
	 * 
	 * @return 数据项的值成员
	 */
	public ValueType getValue() {
		return value;
	}

	/**
	 * 设置数据项的值成员
	 * 
	 * @param value
	 *            数据项的值成员
	 */
	public void setValue(ValueType value) {
		this.value = value;
	}

	/**
	 * 获取数据项的显示成员
	 * 
	 * @return 数据项的显示成员
	 */
	public TextType getText() {
		return text;
	}

	/**
	 * 设置数据项的显示成员
	 * 
	 * @param text
	 *            数据项的显示成员
	 */
	public void setText(TextType text) {
		this.text = text;
	}
}
