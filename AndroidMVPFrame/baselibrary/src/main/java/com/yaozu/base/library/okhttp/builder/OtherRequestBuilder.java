package com.yaozu.base.library.okhttp.builder;


import com.yaozu.base.library.okhttp.request.OtherRequest;
import com.yaozu.base.library.okhttp.request.RequestCall;

import okhttp3.RequestBody;
/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/9/30 0030
 * @desc : [DELETE、PUT、PATCH等其他方法]
 */
public class OtherRequestBuilder extends OkHttpRequestBuilder<OtherRequestBuilder>
{
    private RequestBody requestBody;
    private String method;
    private String content;

    public OtherRequestBuilder(String method)
    {
        this.method = method;
    }

    @Override
    public RequestCall build()
    {
        return new OtherRequest(requestBody, content, method, url, tag, params, headers,id).build();
    }

    public OtherRequestBuilder requestBody(RequestBody requestBody)
    {
        this.requestBody = requestBody;
        return this;
    }

    public OtherRequestBuilder requestBody(String content)
    {
        this.content = content;
        return this;
    }


}
