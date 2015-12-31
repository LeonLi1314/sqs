package com.rtmap.traffic.sqs.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lqs.frame.util.JaxbDateXmlAdapter;

@XmlType(propOrder = { "evnt", "dttm"}) 
public class SecurityInfoMeta {

	private String evnt;
	private Date dttm;

	@XmlElement(name = "EVNT")
	public String getEvnt() {
		return evnt;
	}

	public void setEvnt(String evnt) {
		this.evnt = evnt;
	}

	@XmlElement(name = "DTTM")
	@XmlJavaTypeAdapter(JaxbDateXmlAdapter.class)
	public Date getDttm() {
		return dttm;
	}

	public void setDttm(Date dttm) {
		this.dttm = dttm;
	}
}
