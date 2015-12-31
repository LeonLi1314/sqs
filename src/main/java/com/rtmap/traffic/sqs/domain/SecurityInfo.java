package com.rtmap.traffic.sqs.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "meta", "body"}) 
public class SecurityInfo {
	private SecurityInfoMeta meta;
	private SecurityInfoBody body;

	@XmlElement(name = "META")
	public SecurityInfoMeta getMeta() {
		return meta;
	}

	public void setMeta(SecurityInfoMeta meta) {
		this.meta = meta;
	}

	@XmlElement(name = "BODY")
	public SecurityInfoBody getBody() {
		return body;
	}

	public void setBody(SecurityInfoBody body) {
		this.body = body;
	}
}
