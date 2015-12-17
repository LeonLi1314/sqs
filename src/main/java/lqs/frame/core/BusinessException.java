package lqs.frame.core;

/**
 * unChecked业务异常， 业务异常基类，所有业务异常需继承此类。
 * 
 * @author liqingshan 2015-12-03
 *
 *
 */
/*
 * Checked异常必须被显式地捕获或者传递；而unchecked异常则可以不必捕获或抛出。
 * Checked异常继承java.lang.Exception类。Unchecked异常继承自java.lang.RuntimeException类。
 * 使用unchecked异常比使用checked异常减少了无用的catch-rethrow try-catch块
 * 必须有统一处理捕获unchecked异常的地方，防止忘记处理unchecked异常
 */
public class BusinessException extends RuntimeException {
	/**
	 * BusinessException的序列版本号
	 */
	private static final long serialVersionUID = 1967787977466211275L;
	private String message;

	public BusinessException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}
}
