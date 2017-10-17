package com.yaozu.base.library.okhttp.builder;


import com.yaozu.base.library.okhttp.OkHttpUtils;
import com.yaozu.base.library.okhttp.request.OtherRequest;
import com.yaozu.base.library.okhttp.request.RequestCall;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/9/30 0030
 * @desc : [HeadBuilder]
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
