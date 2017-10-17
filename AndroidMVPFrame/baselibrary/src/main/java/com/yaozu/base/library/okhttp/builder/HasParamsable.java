package com.yaozu.base.library.okhttp.builder;

import java.util.Map;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/9/30 0030
 * @desc : [参数]
 */
public interface HasParamsable
{
    OkHttpRequestBuilder params(Map<String, String> params);
    OkHttpRequestBuilder addParams(String key, String val);
}
