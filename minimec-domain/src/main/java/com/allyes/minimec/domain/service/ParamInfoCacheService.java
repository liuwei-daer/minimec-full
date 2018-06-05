package com.allyes.minimec.domain.service;


import com.allyes.minimec.common.exception.BizException;
import com.allyes.minimec.domain.model.sys.ParamInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author  liuwei
 * @date: 2018-03-10
 * 系统参数缓存服务
 */
@Slf4j
@Service
public class ParamInfoCacheService {

	public static final String CACHE_NAME="paramInfo";
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ParamInfoService paramInfoService;

    public void putIt(String key,String val) throws BizException {
    	BoundHashOperations<String, String, String> ops = stringRedisTemplate.boundHashOps(CACHE_NAME);
    	ops.put(key, val);// 存入数据 ops.putAll(maps); 存入多条数据
	}
    

    /**
     * 重新装载消息提示到Cache
     * @throws BizException
     */
	public void reload() throws BizException {
		List<ParamInfo> list = paramInfoService.getAll();
		if(list==null || list.isEmpty()){
			return ;
		}
		BoundHashOperations<String, String, String> ops = stringRedisTemplate.boundHashOps(CACHE_NAME);
		for(ParamInfo info:list){
			ops.put(info.getParamKey(), info.getParamValue());
		}
		
	}

    /**
     * 获取对应参数值信息
     * @param paramKey
     * @return
     * @throws BizException
     */
	public String getValue(String paramKey){
		BoundHashOperations<String, String, String> ops = stringRedisTemplate.boundHashOps(CACHE_NAME);
		String pv=ops.get(paramKey);
		return pv;
	}

	public Integer getIntValue(String paramKey) {
		String kv=getValue(paramKey);
		Integer rtInt=null;
		try {
			rtInt=Integer.parseInt(kv.trim());
		} catch (NumberFormatException e) {
			rtInt=null;
			log.error(paramKey+"系统参数未配置",e);
		}
		return rtInt;
	}

	/**
	 * 根据paramKey获取paramValue
	 * @param paramKey
	 * @return
	 */
	public String getValueByKey(String paramKey) {
		BoundHashOperations<String, String, String> ops = stringRedisTemplate.boundHashOps(CACHE_NAME);
		String paramValue = ops.get(paramKey);
		if(StringUtils.isEmpty(paramValue)){
			paramValue = paramInfoService.getParamValue(paramKey);
			if(paramValue!=null){
				this.putIt(paramKey, paramValue);
			}
		}
		return paramValue;
	}

	/**
	 * 根据paramKey获取paramValue
	 * @param paramKey
	 * @return
	 */
	public Integer getIntValueByKey(String paramKey) {
		BoundHashOperations<String, String, String> ops = stringRedisTemplate.boundHashOps(CACHE_NAME);
		String paramValue = ops.get(paramKey);
		if(StringUtils.isEmpty(paramValue)){
			paramValue = paramInfoService.getParamValue(paramKey);
			if(paramValue!=null){
				this.putIt(paramKey, paramValue);
			}
		}
		Integer resInt = null;
		try {
			resInt = Integer.parseInt(paramValue);
		} catch (NumberFormatException e) {
			log.error("系统参数未配置：" + paramKey,e);
		}
		return resInt;
	}
}
