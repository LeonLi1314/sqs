package lqs.frame.web;

public class CurrentWebContext {
	// 存储每个会话的基本信息，每次请求的参数，请求结束后销毁对象

	/*
	 * 客户端请求携带登陆成功生成的token：终端ip、mac地址、用户名、sessionId、登录时间计算得出。每隔10分钟token失效重新计算。
	 * 使用token可以获取用户对应的缓存信息
	 * 
	 */
}
