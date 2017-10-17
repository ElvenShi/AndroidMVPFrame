package com.yaozu.base.library.okhttp.builder;


import com.yaozu.base.library.okhttp.request.PostStringRequest;
import com.yaozu.base.library.okhttp.request.RequestCall;

import okhttp3.MediaType;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/9/30 0030
 * @desc : []
 */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder>
{
    private String content;
    private MediaType mediaType;


    public PostStringBuilder content(String content)
    {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostStringRequest(url, tag, params, headers, content, mediaType,id).build();
    }


}
