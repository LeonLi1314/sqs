package com.rtmap.traffic.sqs.domain;

import javax.xml.bind.annotation.XmlElement;

public class AsupBody {
	private AsupBodyMsg msg;

	@XmlElement(name = "msg")
	public AsupBodyMsg getMsg() {
		return msg;
	}

	public void setMsg(AsupBodyMsg msg) {
		this.msg = msg;
	}

}
