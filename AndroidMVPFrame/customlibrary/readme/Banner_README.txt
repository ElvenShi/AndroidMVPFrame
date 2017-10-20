使用介绍



常量

常量名称	描述	所属方法
BannerConfig.NOT_INDICATOR	不显示指示器和标题	setBannerStyle
BannerConfig.CIRCLE_INDICATOR	显示圆形指示器	setBannerStyle
BannerConfig.NUM_INDICATOR	显示数字指示器	setBannerStyle
BannerConfig.NUM_INDICATOR_TITLE	显示数字指示器和标题	setBannerStyle
BannerConfig.CIRCLE_INDICATOR_TITLE	显示圆形指示器和标题（垂直显示）	setBannerStyle
BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE	显示圆形指示器和标题（水平显示）	setBannerStyle
BannerConfig.LEFT	指示器居左	setIndicatorGravity
BannerConfig.CENTER	指示器居中	setIndicatorGravity
BannerConfig.RIGHT	指示器居右	setIndicatorGravity
动画常量类（setBannerAnimation方法调用）

常量类名
Transformer.Default
Transformer.Accordion
Transformer.BackgroundToForeground
Transformer.ForegroundToBackground
Transformer.CubeIn
Transformer.CubeOut
Transformer.DepthPage
Transformer.FlipHorizontal
Transformer.FlipVertical
Transformer.RotateDown
Transformer.RotateUp
Transformer.ScaleInOut
Transformer.Stack
Transformer.Tablet
Transformer.ZoomIn
Transformer.ZoomOut
Transformer.ZoomOutSlide


方法

            方法名	                                描述	                                                 

setBannerStyle(int bannerStyle)	    设置轮播样式（默认为CIRCLE_INDICATOR）	                                     

setIndicatorGravity(int type)	设置指示器位置（没有标题默认为右边,有标题时默认左边）	                         

isAutoPlay(boolean isAutoPlay)	         设置是否自动轮播（默认自动）	                                             

setViewPagerIsScroll(boolean isScroll)	设置是否允许手动滑动轮播图（默认true）	   

update(List<?> imageUrls,List titles)	更新图片和标题	  

update(List<?> imageUrls)	              更新图片	 

startAutoPlay()	开始轮播	    此方法只作用于banner加载完毕-->需要在start()后执行

stopAutoPlay()	结束轮播	   此方法只作用于banner加载完毕-->需要在start()后执行

start()	开始进行banner渲染	

setOffscreenPageLimit(int limit)	同viewpager的方法作用一样	

setBannerTitle(String[] titles)	设置轮播要显示的标题和图片对应（如果不传默认不显示标题）	

setBannerTitleList(List titles)	设置轮播要显示的标题和图片对应（如果不传默认不显示标题）	

setBannerTitles(List titles)	设置轮播要显示的标题和图片对应（如果不传默认不显示标题）	

setDelayTime(int time)	设置轮播图片间隔时间（单位毫秒，默认为2000）	

setImages(Object[]/List<?> imagesUrl)	设置轮播图片(所有设置参数方法都放在此方法之前执行)	

setImages(Object[]/List<?> imagesUrl,OnLoadImageListener listener)	设置轮播图片，并且自定义图片加载方式	

setOnBannerListener(this)	设置点击事件

setOnLoadImageListener(this)	设置图片加载事件，可以自定义图片加载方式	

setImageLoader(Object implements ImageLoader)	设置图片加载器	

setOnPageChangeListener(this)	设置viewpager的滑动监听	

setBannerAnimation(Class<? extends PageTransformer> transformer)	设置viewpager的默认动画,传值见动画表	

setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer)	设置viewpager的自定义动画


Attributes属性（banner布局文件中调用）

Attributes	forma	describe
delay_time	integer	轮播间隔时间，默认2000
scroll_time	integer	轮播滑动执行时间，默认800
is_auto_play	boolean	是否自动轮播，默认true
title_background	color	reference
title_textcolor	color	标题字体颜色
title_textsize	dimension	标题字体大小
title_height	dimension	标题栏高度
indicator_width	dimension	指示器圆形按钮的宽度
indicator_height	dimension	指示器圆形按钮的高度
indicator_margin	dimension	指示器之间的间距
indicator_drawable_selected	reference	指示器选中效果
indicator_drawable_unselected	reference	指示器未选中效果
image_scale_type	enum	和imageview的ScaleType作用一样

使用步骤

Step 1.banner布局

<com.mobcb.banner.Banner
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/banner"
    android:layout_width="match_parent"
    android:layout_height="高度自己设置" />

Step 2.重写图片加载器(该步骤省略,目前项目中已经完成BannerImageLoader的封装)

public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        Glide.with(context).load(path).into(imageView);
    }
    
    
}

Step 3.在Activity或者Fragment中配置Banner

--------------------------简单使用-------------------------------
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Banner banner = (Banner) findViewById(R.id.banner);
    //设置图片加载器
    banner.setImageLoader(new BannerImageLoader());
    //设置图片集合
    banner.setImages(images);
    //banner设置方法全部调用完毕时最后调用
    banner.start();
}
--------------------------详细使用-------------------------------
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Banner banner = (Banner) findViewById(R.id.banner);
    //设置banner样式
    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
    //设置图片加载器
    banner.setImageLoader(new BannerImageLoader());
    //设置图片集合
    banner.setImages(images);
    //设置banner动画效果
    banner.setBannerAnimation(Transformer.DepthPage);
    //设置标题集合（当banner样式有显示title时）
    banner.setBannerTitles(titles);
    //设置自动轮播，默认为true
    banner.isAutoPlay(true);
    //设置轮播时间
    banner.setDelayTime(1500);
    //设置指示器位置（当banner模式中有指示器时）
    banner.setIndicatorGravity(BannerConfig.CENTER);
    //banner设置方法全部调用完毕时最后调用
    banner.start();
}
-----------------当然如果你想偷下懒也可以这么用--------------------
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Banner banner = (Banner) findViewById(R.id.banner);
    banner.setImages(images).setImageLoader(new BannerImageLoader()).start();
}
Step 4.（可选）增加体验

//如果你需要考虑更好的体验，可以这么操作
@Override
protected void onStart() {
    super.onStart();
    //开始轮播
    banner.startAutoPlay();
}

@Override
protected void onStop() {
    super.onStop();
    //结束轮播
    banner.stopAutoPlay();
}
