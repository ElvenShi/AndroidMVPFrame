/**
 * Copyright 2016 mobcb
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobcb.refreshlayout.adapters;

import android.databinding.BindingAdapter;

import com.mobcb.refreshlayout.BGARefreshLayout;

/**
 * @author Shiyaozu
 * @version [V1.0.0]
 * @date 2017/6/1 0001
 * @desc [描述相关类/方法]
 */
public class BGARefreshLayoutAdapter {

    @BindingAdapter({"bga_refresh_delegate"})
    public static void setDelegate(BGARefreshLayout refreshLayout, BGARefreshLayout.BGARefreshLayoutDelegate delegate) {
        refreshLayout.setDelegate(delegate);
    }

}