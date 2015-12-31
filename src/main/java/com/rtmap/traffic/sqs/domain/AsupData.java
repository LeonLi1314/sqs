package com.rtmap.traffic.sqs.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ASUPDATA")
@XmlType(propOrder = { "asupHeader", "asupBody"}) 
public class AsupData {

	private AsupHeader asupHeader;
	private AsupBody asupBody;

	@XmlElement(name="Header")
	public AsupHeader getAsupHeader() {
		return asupHeader;
	}

	public void setAsupHeader(AsupHeader asupHeader) {
		this.asupHeader = asupHeader;
	}

	@XmlElement(name="Body")
	public AsupBody getAsupBody() {
		return asupBody;
	}

	public void setAsupBody(AsupBody asupBody) {
		this.asupBody = asupBody;
	}
}
