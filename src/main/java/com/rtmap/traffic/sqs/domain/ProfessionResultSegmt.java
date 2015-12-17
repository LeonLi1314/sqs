package com.rtmap.traffic.sqs.domain;

import java.util.Date;

public class ProfessionResultSegmt {
	private Integer id;

	private Date segmt;

	private int minDuar;

	private int maxDuar;

	private int avgDuar;

	private double passRate;

	private int medianDuar;

	private Date processTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getSegmt() {
		return segmt;
	}

	public void setSegmt(Date segmt) {
		this.segmt = segmt;
	}

	public int getMinDuar() {
		return minDuar;
	}

	public void setMinDuar(int minDuar) {
		this.minDuar = minDuar;
	}

	public int getMaxDuar() {
		return maxDuar;
	}

	public void setMaxDuar(int maxDuar) {
		this.maxDuar = maxDuar;
	}

	public int getAvgDuar() {
		return avgDuar;
	}

	public void setAvgDuar(int avgDuar) {
		this.avgDuar = avgDuar;
	}

	public double getPassRate() {
		return passRate;
	}

	public void setPassRate(double passRate) {
		this.passRate = passRate;
	}

	public int getMedianDuar() {
		return medianDuar;
	}

	public void setMedianDuar(int medianDuar) {
		this.medianDuar = medianDuar;
	}

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}
}