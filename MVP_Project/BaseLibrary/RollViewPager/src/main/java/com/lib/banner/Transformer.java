package com.lib.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.lib.banner.transformer.AccordionTransformer;
import com.lib.banner.transformer.BackgroundToForegroundTransformer;
import com.lib.banner.transformer.CubeInTransformer;
import com.lib.banner.transformer.CubeOutTransformer;
import com.lib.banner.transformer.DefaultTransformer;
import com.lib.banner.transformer.DepthPageTransformer;
import com.lib.banner.transformer.FlipHorizontalTransformer;
import com.lib.banner.transformer.FlipVerticalTransformer;
import com.lib.banner.transformer.ForegroundToBackgroundTransformer;
import com.lib.banner.transformer.RotateDownTransformer;
import com.lib.banner.transformer.RotateUpTransformer;
import com.lib.banner.transformer.ScaleInOutTransformer;
import com.lib.banner.transformer.StackTransformer;
import com.lib.banner.transformer.TabletTransformer;
import com.lib.banner.transformer.ZoomInTransformer;
import com.lib.banner.transformer.ZoomOutSlideTransformer;
import com.lib.banner.transformer.ZoomOutTranformer;

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
