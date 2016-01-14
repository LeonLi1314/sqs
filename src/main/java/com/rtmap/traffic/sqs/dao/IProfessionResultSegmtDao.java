package com.rtmap.traffic.sqs.dao;

import java.util.Date;
import java.util.List;

import com.rtmap.traffic.sqs.domain.ProfessionResultSegmt;

/**
 * 行业数据的数据访问接口
 * 
 * @author liqingshan
 *
 */
public interface IProfessionResultSegmtDao {

	/**
	 * 获取指定时间内最近的一条处理结果
	 * 
	 * @param processTime
	 *            指定日期
	 * @return 最近的一条处理结果，不存在则为空
	 */
	ProfessionResultSegmt selectLatestByPorcessTime(Date processTime);

	/**
	 * 获取指定日期内的处理结果集合
	 * 
	 * @param processTime
	 *            指定日期
	 * @return 指定日期后的处理结果集合，不存在则为空
	 */
	List<ProfessionResultSegmt> selectByPorcessTime(Date processTime);

	/**
	 * 查询指定时间段内平均等待时长排名靠前的时间点
	 * 
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @param topNum
	 *            取前几个值
	 * @param isMedianDur
	 *            是否取中位数，否则取平均数
	 * @return 时间点集合
	 */
	List<Date> selectTopOfAvgDuarOfSegmts(Date beginDate, Date endDate, int topNum, boolean isMedianDur);

	/**
	 * 查询指定时间段内每小时的平均等待时长
	 * 
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @param isMedianDur
	 *            是否取中位数，否则取平均数
	 * @return 每小时平均等待时长集合
	 */
	List<Integer> selectPerHourAvgDuars(Date beginDate, Date endDate, boolean isMedianDur);
}
