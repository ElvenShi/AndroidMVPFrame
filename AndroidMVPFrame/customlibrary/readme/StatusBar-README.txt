
设置状态栏颜色

StatusBarUtil.setColor(Activity activity, int color)


设置状态栏半透明

StatusBarUtil.setTranslucent(Activity activity, int statusBarAlpha)
​ ​

设置状态栏全透明

StatusBarUtil.setTransparent(Activity activity)


为包含 DrawerLayout 的界面设置状态栏颜色（也可以设置半透明和全透明）

StatusBarUtil.setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, int color)


为使用 ImageView 作为头部的界面设置状态栏透明

StatusBarUtil.setTranslucentForImageView(Activity activity, int statusBarAlpha, View needOffsetView)


在 Fragment 中使用



为滑动返回界面设置状态栏颜色

StatusBarUtil.setColorForSwipeBack(Activity activity, @ColorInt int color, int statusBarAlpha)


通过传入 statusBarAlpha 参数，可以改变状态栏的透明度值，默认值是112。