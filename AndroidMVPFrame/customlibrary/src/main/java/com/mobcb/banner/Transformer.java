package com.mobcb.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.mobcb.banner.transformer.AccordionTransformer;
import com.mobcb.banner.transformer.BackgroundToForegroundTransformer;
import com.mobcb.banner.transformer.CubeInTransformer;
import com.mobcb.banner.transformer.CubeOutTransformer;
import com.mobcb.banner.transformer.DefaultTransformer;
import com.mobcb.banner.transformer.DepthPageTransformer;
import com.mobcb.banner.transformer.FlipHorizontalTransformer;
import com.mobcb.banner.transformer.FlipVerticalTransformer;
import com.mobcb.banner.transformer.ForegroundToBackgroundTransformer;
import com.mobcb.banner.transformer.RotateDownTransformer;
import com.mobcb.banner.transformer.RotateUpTransformer;
import com.mobcb.banner.transformer.ScaleInOutTransformer;
import com.mobcb.banner.transformer.StackTransformer;
import com.mobcb.banner.transformer.TabletTransformer;
import com.mobcb.banner.transformer.ZoomInTransformer;
import com.mobcb.banner.transformer.ZoomOutSlideTransformer;
import com.mobcb.banner.transformer.ZoomOutTranformer;


public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
