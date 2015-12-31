package com.rtmap.traffic.sqs.domain;

import javax.xml.bind.annotation.XmlElement;

public class AsupBodyMsg {

	private SecurityInfo securityInfo;

	@XmlElement(name = "SECURTIYINFO")
	public SecurityInfo getSecurityInfo() {
		return securityInfo;
	}

	public void setSecurityInfo(SecurityInfo securityInfo) {
		this.securityInfo = securityInfo;
	}
}
