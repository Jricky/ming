package ming.framework.core.controller;

import lombok.extern.slf4j.Slf4j;
import ming.framework.constant.RequestConst;
import ming.framework.core.po.ServiceApi;
import ming.framework.core.service.cache.CacheService;
import ming.framework.util.BeanFactory;
import ming.framework.util.JsonUtil;
import ming.framework.util.ReflectUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * BaseController
 * Created by Jrick on 2018/1/1.
 */
@Slf4j
public abstract class BaseController {

	@Resource
	private CacheService cacheService;

	protected String request(HttpServletRequest request) {
		Map<String, Object> paramMap = initParamMap(request);
		String jsonRes = JsonUtil.toJson(callService(paramMap));
		return jsonRes;
	}

	private Map<String, Object> initParamMap(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, Object> map = new HashMap<>();
		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
			String[] valueArr = entry.getValue();
			String value = "";
			if (valueArr != null && valueArr.length > 0 && valueArr[0] != null) {
				value = valueArr[0].trim();
			}
			map.put(entry.getKey(), value);
		}
		return map;
	}

	private Object callService(Map<String, Object> paramMap) {
		String apiName = MapUtils.getString(paramMap, RequestConst.API_NAME);
		String apiVersion = MapUtils.getString(paramMap, RequestConst.API_VERSION);
		ServiceApi serviceApi = cacheService.getServiceApi(apiName, apiVersion);
		String paramClass = getParamClass(serviceApi);
		Object req = JsonUtil.toObj(JsonUtil.toJson(paramMap), paramClass);
		log.info("====>调用接口 : {}-{}", serviceApi.getApiName(), serviceApi.getDescription());
		log.info("====>请求参数 : {}", JsonUtil.toJson(req));
		Object service = getBean(serviceApi.getService());
		Object result = ReflectUtil.invoke(service, serviceApi.getMethod(), req);
		log.info("<====返回参数 : {}", JsonUtil.toJson(result));
		return result;
	}

	private String getParamClass(ServiceApi serviceApi) {
		Object service = getBean(serviceApi.getService());
		Method method = ReflectUtil.getMethod(service.getClass(), serviceApi.getMethod());
		return method.getParameterTypes()[0].getName();
	}

	private Object getBean(String serviceName) {
		String formatService = formatService(serviceName);
		Object service = BeanFactory.getBean(formatService);
		return service;
	}

	private String formatService(String service) {
		return StringUtils.uncapitalize(service);
	}
}
