package lqs.frame.ui.model;

import java.util.List;

/**
 * ui控件数据源处理类
 * 
 * @author liqingshan 2015-12-09
 *
 */
public class DataSourceUtils {
	/**
	 * 全部项的值
	 */
	public final static String ITEM_VALUE_ALL = "ALL";
	/**
	 * 全部项的显示文本
	 */
	public final static String ITEM_TEXT_ALL = "全部";

	/**
	 * Combo控件增加【全部】选项
	 * 
	 * @param source
	 *            控件数据源
	 */
	public static void AddComboWithAll(List<ComboStrItem> source) {
		ComboStrItem item = new ComboStrItem();
		item.setText(ITEM_TEXT_ALL);
		item.setValue(ITEM_VALUE_ALL);
		source.add(0, item);
	}
}
