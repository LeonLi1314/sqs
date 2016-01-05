package com.rtmap.traffic.sqs.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rtmap.traffic.sqs.dao.IProfessionResultSegmtDao;
import com.rtmap.traffic.sqs.domain.ProfessionResultSegmt;

import lqs.frame.util.DateUtils;

/**
 * 行业数据的数据表访问实现
 * 
 * @author liqingshan
 *
 */
@Repository
public class ProfessionResultSegmtDaoImpl implements IProfessionResultSegmtDao {
	@Resource(name = "jdbcTemplate")
	// @Resource
	private JdbcTemplate jdbcTemplate;

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/";
	static final String USER = "lqs";
	static final String PASS = "1213";

	public void selectProfessionResultSegmt() throws SQLException, ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		Statement stmt = conn.createStatement();
		String sql = "select * from profession_result_segmt";
		ResultSet result = stmt.executeQuery(sql);
		
		while (result.next()) {
			result.getString(0);
			// ...
		}
	}

	@Override
	public ProfessionResultSegmt selectLatestByPorcessTime(Date processTime) {
		StringBuilder commanText = new StringBuilder(128);
		commanText.append("select id,segmt,min_duar,max_duar,avg_duar,pass_rate,median_duar,process_time");
		commanText.append(" from profession_result_segmt");
		commanText.append(" where process_time > ? order by segmt desc limit 1;");

		final ProfessionResultSegmt model = new ProfessionResultSegmt();

		jdbcTemplate.query(commanText.toString(), new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				model.setId(rs.getInt("id"));
				model.setSegmt(rs.getTimestamp("segmt"));
				model.setMinDuar(rs.getInt("min_duar"));
				model.setMaxDuar(rs.getInt("max_duar"));
				model.setAvgDuar(rs.getInt("avg_duar"));
				model.setPassRate(rs.getDouble("pass_rate"));
				model.setMedianDuar(rs.getInt("median_duar"));
				model.setProcessTime(rs.getTimestamp("process_time"));
			}
		}, DateUtils.getTimestamp(processTime));

		if(model.getId() == null)
			return null;
		
		return model;
	}

	@Override
	public List<ProfessionResultSegmt> selectByPorcessTime(Date processTime) {
		StringBuilder commanText = new StringBuilder(100);
		commanText.append("select id,segmt,min_duar,max_duar,avg_duar,pass_rate,median_duar,process_time");
		commanText.append(" from profession_result_segmt");
		commanText.append(" where process_time > ? order by segmt;");

		List<ProfessionResultSegmt> rst = jdbcTemplate.query(commanText.toString(),
				new RowMapper<ProfessionResultSegmt>() {
					@Override
					public ProfessionResultSegmt mapRow(ResultSet rs, int rowNum) throws SQLException {
						final ProfessionResultSegmt model = new ProfessionResultSegmt();
						model.setId(rs.getInt("id"));
						model.setSegmt(rs.getDate("segmt"));
						model.setMinDuar(rs.getInt("min_duar"));
						model.setMaxDuar(rs.getInt("max_duar"));
						model.setAvgDuar(rs.getInt("avg_duar"));
						model.setPassRate(rs.getDouble("pass_rate"));
						model.setMedianDuar(rs.getInt("median_duar"));
						model.setProcessTime(rs.getDate("process_time"));

						return model;
					}
				}, DateUtils.getTimestamp(processTime));

		return rst;
	}

	@Override
	public List<Date> selectTopOfAvgDuarOfSegmts(Date beginDate, Date endDate, int topNum) {
		StringBuilder commanText = new StringBuilder(128);
		commanText.append("select segmt from profession_result_segmt");
		commanText.append(" where process_time >= ? and process_time < ?");
		commanText.append(" order by median_duar desc limit ?");

		List<Date> rst = jdbcTemplate.query(commanText.toString(), new RowMapper<Date>() {
			@Override
			public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getTimestamp("segmt");
			}
		}, DateUtils.getTimestamp(beginDate), DateUtils.getTimestamp(endDate), topNum);

		return rst;
	}

	@Override
	public List<Integer> selectPerHourAvgDuars(Date beginDate, Date endDate) {
		StringBuilder commanText = new StringBuilder(128);
		commanText.append("select round(AVG(t.median_duar), 0) as median_duar from");
		commanText.append(" (");
		commanText.append("select median_duar, date_format(segmt,'%H') as hour_info from profession_result_segmt");
		commanText.append(" where segmt >= ? and segmt < ?");
		commanText.append(" order by segmt");
		commanText.append(") t");
		commanText.append(" group by t.hour_info;");

		List<Integer> rst = jdbcTemplate.query(commanText.toString(), new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("median_duar");
			}
		}, DateUtils.getTimestamp(beginDate), DateUtils.getTimestamp(endDate));

		return rst;
	}
}
