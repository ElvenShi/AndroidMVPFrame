package com.yaozu.base.library.okhttp.callback;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/9/30 0030
 * @desc : []
 */
public interface IGenericsSerializator {
    <T> T transform(String response, Class<T> classOfT);
}
