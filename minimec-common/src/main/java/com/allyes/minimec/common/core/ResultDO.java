package com.allyes.minimec.common.core;


/**
 * @author liuwei
 * @date 2018-03-10
 * 返回参数对象
 */
public class ResultDO<T> extends BaseResultDO {

	private static final long serialVersionUID = -3434272908589805662L;

	private T data;

	public ResultDO() {
		
	}

	public ResultDO(String key, boolean result) {
		setResultCode(key);
		//setErrorMessage(BaseResultCode.getValueWithKey(key));
		setSuccess(result);
	}

	public ResultDO(T data) {
		this.data = data;
	}

	public static <T> ResultDO<T> getResult() {
		return new ResultDO<T>();
	}

	public T getModule() {
		return data;
	}

	public void setModule(T data) {
		this.data = data;
	}

	public void setError(String key){
		setSuccess(false);
		setResultCode(key);
		//setErrorMessage(BaseResultCode.getValueWithKey(key));
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("ResultDO{");
		sb.append("success=").append(this.isSuccess());
		sb.append("resultCode=").append(this.getResultCode());
		sb.append("errorMessage=").append(this.getErrorMessage());
		sb.append("data=").append(data);
		sb.append('}');
		return sb.toString();
	}
}
