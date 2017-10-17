package com.yaozu.base.library.okhttp.cookie.store;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/9/30 0030
 * @desc : [cookie存储接口]
 */
public interface CookieStore
{

    void add(HttpUrl uri, List<Cookie> cookie);

    List<Cookie> get(HttpUrl uri);

    List<Cookie> getCookies();

    boolean remove(HttpUrl uri, Cookie cookie);

    boolean removeAll();

}
