package com.rtmap.traffic.sqs.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "area", "waitingTime", "loadfactor", "waitingStatus", "waitingLevel", "waitingPeopleCount",
		"passRate", "waitingPeakTime", "dailyWaitingLevel" })
public class SecurityInfoBody {
	private String area;
	private int waitingTime;
	private int loadfactor;
	private String waitingStatus;
	private String waitingLevel;
	private int waitingPeopleCount;
	private int passRate;
	private String waitingPeakTime;
	private String dailyWaitingLevel;

	@XmlElement(name = "AREA")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@XmlElement(name = "WAITINGTIME")
	public int getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	@XmlElement(name = "LOADFACTOR")
	public int getLoadfactor() {
		return loadfactor;
	}

	public void setLoadfactor(int loadfactor) {
		this.loadfactor = loadfactor;
	}

	@XmlElement(name = "WAITINGSTATUS")
	public String getWaitingStatus() {
		return waitingStatus;
	}

	public void setWaitingStatus(String watingStatus) {
		this.waitingStatus = watingStatus;
	}

	@XmlElement(name = "WAITINGLEVEL")
	public String getWaitingLevel() {
		return waitingLevel;
	}

	public void setWaitingLevel(String watingLevel) {
		this.waitingLevel = watingLevel;
	}

	@XmlElement(name = "WAITINGPEOPLECOUNT")
	public int getWaitingPeopleCount() {
		return waitingPeopleCount;
	}

	public void setWaitingPeopleCount(int watingPeopleCount) {
		this.waitingPeopleCount = watingPeopleCount;
	}

	@XmlElement(name = "PASSRATE")
	public int getPassRate() {
		return passRate;
	}

	public void setPassRate(int passRate) {
		this.passRate = passRate;
	}

	@XmlElement(name = "WAITINGPEAKTIME")
	public String getWaitingPeakTime() {
		return waitingPeakTime;
	}

	public void setWaitingPeakTime(String watingPeakTime) {
		this.waitingPeakTime = watingPeakTime;
	}

	@XmlElement(name = "DAILYWAITINGLEVEL")
	public String getDailyWaitingLevel() {
		return dailyWaitingLevel;
	}

	public void setDailyWatingLevel(String dailyWatingLevel) {
		this.dailyWaitingLevel = dailyWatingLevel;
	}
}
