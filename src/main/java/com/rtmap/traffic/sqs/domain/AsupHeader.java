package com.rtmap.traffic.sqs.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lqs.frame.util.JaxbDateXmlAdapter;

@XmlType(propOrder = { "serviceName", "sendSystem", "createTime"}) 
public class AsupHeader {
	private String serviceName;
	private String sendSystem;
	private Date createTime;

	@XmlElement(name = "ServiceName")
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@XmlElement(name="SendSystem")
	public String getSendSystem() {
		return sendSystem;
	}

	public void setSendSystem(String sendSystem) {
		this.sendSystem = sendSystem;
	}

	@XmlElement(name="CreateTime")
	@XmlJavaTypeAdapter(JaxbDateXmlAdapter.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
