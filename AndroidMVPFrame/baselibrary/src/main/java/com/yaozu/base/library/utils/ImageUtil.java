/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yaozu.base.library.utils;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import static com.yaozu.base.library.utils.BitmapUtil.releaseBitmap;

/**
 * 描述：图片处理类.
 *
 * 解析bitmap `getBitmapByte`
 * 直接获取互联网上的图片 `getBitmap`
 * 获取原图 `getBitmap`
 * 缩放图片.压缩 `scaleImg`
 * 缩放图片,不压缩的缩放
 * 裁剪图片 `cutImg`
 * Drawable转Bitmap `drawableToBitmap`
 * Bitmap对象转换Drawable对象. `bitmapToDrawable`
 * Bitmap对象转换TransitionDrawable对象 `bitmapToTransitionDrawable`
 * Drawable对象转换TransitionDrawable对象 `drawableToTransitionDrawable`
 * 将Bitmap转换为byte[] `bitmap2Bytes`
 * 获取Bitmap大小 `getByteCount`
 * 将byte[]转换为Bitmap `bytes2Bimap`
 * 将View转换为Drawable.需要最上层布局为Linearlayout `view2Drawable`
 * 将View转换为Bitmap.需要最上层布局为Linearlayout `view2Bitmap`
 * 将View转换为byte[] `view2Bytes`
 * 旋转Bitmap为一定的角度 `rotateBitmap`
 * 旋转Bitmap为一定的角度并四周暗化处理 `rotateBitmapTranslate`
 * 转换图片转换成圆形 `toRoundBitmap`
 * 转换图片转换成圆形通过指定的弧度 `toRoundBitmap`
 * 转换图片转换成镜面效果的图片 `toReflectionBitmap`
 * 释放Bitmap对象 `releaseBitmap`
 * 释放Bitmap数组 `releaseBitmapArray`
 * 简单的图像的特征值，用于缩略图找原图比较好 `getHashCode`
 * 图像的特征值颜色分布 将颜色分4个区，0,1,2,3 区组合共64组，计算每个像素点属于哪个区 `getColorHistogram`
 * 计算"汉明距离"（Hamming distance）`hammingDistance`
 * 灰度值计算 `rgbToGray`
 * 压缩图片 `compressBitmap`
 * 根据URI获取图片物理路径 `getAbsoluteImagePath`
 */
public class ImageUtil {

	/** 图片处理：裁剪. */
	public static final int CUTIMG = 0;

	/** 图片处理：缩放. */
	public static final int SCALEIMG = 1;

	/** 图片处理：不处理. */
	public static final int ORIGINALIMG = 2;

	/** 图片最大宽度. */
	public static final int MAX_WIDTH = 4096 / 2;

	/** 图片最大高度. */
	public static final int MAX_HEIGHT = 4096 / 2;
	
	

	/**
	 * 解析bitmap
	 * 
	 * @param bitmap
	 * @return
	 */
	public static byte[] getBitmapByte(Bitmap bitmap) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	/**
	 * 直接获取互联网上的图片.
	 * 
	 * @param url
	 *            要下载文件的网络地址
	 * @param type
	 *            图片的处理类型（剪切或者缩放到指定大小，参考AbConstant类）
	 * @param desiredWidth
	 *            新图片的宽
	 * @param desiredHeight
	 *            新图片的高
	 * @return Bitmap 新图片
	 */
	public static Bitmap getBitmap(String url, int type, int desiredWidth, int desiredHeight) {
		Bitmap bm = null;
		URLConnection con = null;
		InputStream is = null;
		try {
			URL imageURL = new URL(url);
			con = imageURL.openConnection();
			con.setDoInput(true);
			con.connect();
			is = con.getInputStream();
			// 获取资源图片
			Bitmap wholeBm = BitmapFactory.decodeStream(is, null, null);
			if (type == CUTIMG) {
				bm = cutImg(wholeBm, desiredWidth, desiredHeight);
			} else if (type == SCALEIMG) {
				bm = scaleImg(wholeBm, desiredWidth, desiredHeight);
			} else {
				bm = wholeBm;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bm;
	}

	/**
	 * 描述：获取原图.
	 * 
	 * @param file
	 *            File对象
	 * @return Bitmap 图片
	 */
	public static Bitmap getBitmap(File file) {
		Bitmap resizeBmp = null;
		try {
			resizeBmp = BitmapFactory.decodeFile(file.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resizeBmp;
	}

	/**
	 * 描述：缩放图片.压缩
	 * 
	 * @param file
	 *            File对象
	 * @param desiredWidth
	 *            新图片的宽
	 * @param desiredHeight
	 *            新图片的高
	 * @return Bitmap 新图片
	 */
	public static Bitmap scaleImg(File file, int desiredWidth, int desiredHeight) {

		Bitmap resizeBmp = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为true,decodeFile先不创建内存 只获取一些解码边界信息即图片大小信息
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(file.getPath(), opts);

		// 获取图片的原始宽度高度
		int srcWidth = opts.outWidth;
		int srcHeight = opts.outHeight;
		int[] size = resizeToMaxSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
		desiredWidth = size[0];
		desiredHeight = size[1];

		// 缩放的比例
		float scale = getMinScale(srcWidth, srcHeight, desiredWidth, desiredHeight);
		int destWidth = srcWidth;
		int destHeight = srcHeight;
		if (scale != 0) {
			destWidth = (int) (srcWidth * scale);
			destHeight = (int) (srcHeight * scale);
		}

		// 默认为ARGB_8888.
		opts.inPreferredConfig = Config.RGB_565;
		// 以下两个字段需一起使用：
		// 产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
		opts.inPurgeable = true;
		// 位图可以共享一个参考输入数据(inputstream、阵列等)
		opts.inInputShareable = true;

		// inSampleSize=2 表示图片宽高都为原来的二分之一，即图片为原来的四分之一
		// 缩放的比例，SDK中建议其值是2的指数值，通过inSampleSize来进行缩放，其值表明缩放的倍数
		if (scale < 0.25) {
			// 缩小到4分之一
			opts.inSampleSize = 2;
		} else if (scale < 0.125) {
			// 缩小到8分之一
			opts.inSampleSize = 4;
		} else {
			opts.inSampleSize = 1;
		}

		// 设置大小
		opts.outWidth = destWidth;
		opts.outHeight = destHeight;

		// 创建内存
		opts.inJustDecodeBounds = false;
		// 使图片不抖动
		opts.inDither = false;

		resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
		// 缩小或者放大
		resizeBmp = scaleImg(resizeBmp, scale);
		return resizeBmp;
	}

	/**
	 * 描述：缩放图片,不压缩的缩放.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param desiredWidth
	 *            新图片的宽
	 * @param desiredHeight
	 *            新图片的高
	 * @return Bitmap 新图片
	 */
	public static Bitmap scaleImg(Bitmap bitmap, int desiredWidth, int desiredHeight) {

		if (!checkBitmap(bitmap)) {
			return null;
		}
		Bitmap resizeBmp = null;

		// 获得图片的宽高
		int srcWidth = bitmap.getWidth();
		int srcHeight = bitmap.getHeight();

		int[] size = resizeToMaxSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
		desiredWidth = size[0];
		desiredHeight = size[1];

		float scale = getMinScale(srcWidth, srcHeight, desiredWidth, desiredHeight);
		resizeBmp = scaleImg(bitmap, scale);
		// 超出的裁掉
		if (resizeBmp.getWidth() > desiredWidth || resizeBmp.getHeight() > desiredHeight) {
			resizeBmp = cutImg(resizeBmp, desiredWidth, desiredHeight);
		}
		return resizeBmp;
	}

	/**
	 * 描述：根据等比例缩放图片.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param scale
	 *            比例
	 * @return Bitmap 新图片
	 */
	public static Bitmap scaleImg(Bitmap bitmap, float scale) {

		if (!checkBitmap(bitmap)) {
			return null;
		}

		if (scale == 1) {
			return bitmap;
		}

		Bitmap resizeBmp = null;
		try {
			// 获取Bitmap资源的宽和高
			int bmpW = bitmap.getWidth();
			int bmpH = bitmap.getHeight();

			// 注意这个Matirx是android.graphics底下的那个
			Matrix matrix = new Matrix();
			// 设置缩放系数，分别为原来的0.8和0.8
			matrix.postScale(scale, scale);
			resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bmpW, bmpH, matrix, true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resizeBmp != bitmap) {
				bitmap.recycle();
			}
		}
		return resizeBmp;
	}

	/**
	 * 描述：裁剪图片.
	 * 
	 * @param file
	 *            File对象
	 * @param desiredWidth
	 *            新图片的宽
	 * @param desiredHeight
	 *            新图片的高
	 * @return Bitmap 新图片
	 */
	public static Bitmap cutImg(File file, int desiredWidth, int desiredHeight) {

		Bitmap resizeBmp = null;

		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为true,decodeFile先不创建内存 只获取一些解码边界信息即图片大小信息
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(file.getPath(), opts);

		// 获取图片的原始宽度
		int srcWidth = opts.outWidth;
		// 获取图片原始高度
		int srcHeight = opts.outHeight;

		int[] size = resizeToMaxSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
		desiredWidth = size[0];
		desiredHeight = size[1];

		// 缩放的比例
		float scale = getMinScale(srcWidth, srcHeight, desiredWidth, desiredHeight);
		int destWidth = srcWidth;
		int destHeight = srcHeight;
		if (scale != 1) {
			destWidth = (int) (srcWidth * scale);
			destHeight = (int) (srcHeight * scale);
		}

		// 默认为ARGB_8888.
		opts.inPreferredConfig = Config.RGB_565;
		// 以下两个字段需一起使用：
		// 产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
		opts.inPurgeable = true;
		// 位图可以共享一个参考输入数据(inputstream、阵列等)
		opts.inInputShareable = true;
		// 缩放的比例，缩放是很难按准备的比例进行缩放的，通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
		if (scale < 0.25) {
			// 缩小到4分之一
			opts.inSampleSize = 2;
		} else if (scale < 0.125) {
			// 缩小
			opts.inSampleSize = 4;
		} else {
			opts.inSampleSize = 1;
		}
		// 设置大小
		opts.outHeight = destHeight;
		opts.outWidth = destWidth;
		// 创建内存
		opts.inJustDecodeBounds = false;
		// 使图片不抖动
		opts.inDither = false;
		Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), opts);
		if (bitmap != null) {
			resizeBmp = cutImg(bitmap, desiredWidth, desiredHeight);
		}
		return resizeBmp;
	}

	/**
	 * 描述：裁剪图片.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param desiredWidth
	 *            新图片的宽
	 * @param desiredHeight
	 *            新图片的高
	 * @return Bitmap 新图片
	 */
	public static Bitmap cutImg(Bitmap bitmap, int desiredWidth, int desiredHeight) {

		if (!checkBitmap(bitmap)) {
			return null;
		}

		if (!checkSize(desiredWidth, desiredHeight)) {
			return null;
		}

		Bitmap resizeBmp = null;

		try {
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();

			int offsetX = 0;
			int offsetY = 0;

			if (width > desiredWidth) {
				offsetX = (width - desiredWidth) / 2;
			} else {
				desiredWidth = width;
			}

			if (height > desiredHeight) {
				offsetY = (height - desiredHeight) / 2;
			} else {
				desiredHeight = height;
			}

			resizeBmp = Bitmap.createBitmap(bitmap, offsetX, offsetY, desiredWidth, desiredHeight);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resizeBmp != bitmap) {
				bitmap.recycle();
			}
		}
		return resizeBmp;
	}

	private static float getMinScale(int srcWidth, int srcHeight, int desiredWidth, int desiredHeight) {
		// 缩放的比例
		float scale = 0;
		// 计算缩放比例，宽高的最小比例
		float scaleWidth = (float) desiredWidth / srcWidth;
		float scaleHeight = (float) desiredHeight / srcHeight;
		if (scaleWidth > scaleHeight) {
			scale = scaleWidth;
		} else {
			scale = scaleHeight;
		}

		return scale;
	}

	private static int[] resizeToMaxSize(int srcWidth, int srcHeight, int desiredWidth, int desiredHeight) {
		int[] size = new int[2];
		if (desiredWidth <= 0) {
			desiredWidth = srcWidth;
		}
		if (desiredHeight <= 0) {
			desiredHeight = srcHeight;
		}
		if (desiredWidth > MAX_WIDTH) {
			// 重新计算大小
			desiredWidth = MAX_WIDTH;
			float scaleWidth = (float) desiredWidth / srcWidth;
			desiredHeight = (int) (desiredHeight * scaleWidth);
		}

		if (desiredHeight > MAX_HEIGHT) {
			// 重新计算大小
			desiredHeight = MAX_HEIGHT;
			float scaleHeight = (float) desiredHeight / srcHeight;
			desiredWidth = (int) (desiredWidth * scaleHeight);
		}
		size[0] = desiredWidth;
		size[1] = desiredHeight;
		return size;
	}

	private static boolean checkBitmap(Bitmap bitmap) {
		if (bitmap == null) {
			return false;
		}

		if (bitmap.getWidth() <= 0 || bitmap.getHeight() <= 0) {
			return false;
		}
		return true;
	}

	private static boolean checkSize(int desiredWidth, int desiredHeight) {
		if (desiredWidth <= 0 || desiredHeight <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * 描述：简单的图像的特征值，用于缩略图找原图比较好.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @return the hash code
	 */
	public static String getHashCode(Bitmap bitmap) {
		// 第一步，缩小尺寸。
		// 将图片缩小到8x8的尺寸，总共64个像素。这一步的作用是去除图片的细节，
		// 只保留结构、明暗等基本信息，摒弃不同尺寸、比例带来的图片差异。

		Bitmap temp = Bitmap.createScaledBitmap(bitmap, 8, 8, false);

		int width = temp.getWidth();
		int height = temp.getHeight();
		Log.i("th", "将图片缩小到8x8的尺寸:" + width + "*" + height);

		// 第二步，第二步，简化色彩。
		// 将缩小后的图片，转为64级灰度。也就是说，所有像素点总共只有64种颜色。
		int[] pixels = new int[width * height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				pixels[i * height + j] = rgbToGray(temp.getPixel(i, j));
			}
		}

		releaseBitmap(temp);

		// 第三步，计算平均值。
		// 计算所有64个像素的灰度平均值。
		int avgPixel = MathUtil.average(pixels);

		// 第四步，比较像素的灰度。
		// 将每个像素的灰度，与平均值进行比较。大于或等于平均值，记为1；小于平均值，记为0。
		int[] comps = new int[width * height];
		for (int i = 0; i < comps.length; i++) {
			if (pixels[i] >= avgPixel) {
				comps[i] = 1;
			} else {
				comps[i] = 0;
			}
		}

		// 第五步，计算哈希值。
		// 将上一步的比较结果，组合在一起，就构成了一个64位的整数，
		// 这就是这张图片的指纹。
		StringBuffer hashCode = new StringBuffer();
		for (int i = 0; i < comps.length; i += 4) {
			int result = comps[i] * (int) Math.pow(2, 3) + comps[i + 1] * (int) Math.pow(2, 2)
					+ comps[i + 2] * (int) Math.pow(2, 1) + comps[i + 2];
			hashCode.append(MathUtil.binaryToHex(result));
		}
		String sourceHashCode = hashCode.toString();
		// 得到指纹以后，就可以对比不同的图片，看看64位中有多少位是不一样的。
		// 在理论上，这等同于计算"汉明距离"（Hamming distance）。
		// 如果不相同的数据位不超过5，就说明两张图片很相似；如果大于10，就说明这是两张不同的图片。
		return sourceHashCode;
	}

	/**
	 * 描述：图像的特征值颜色分布 将颜色分4个区，0,1,2,3 区组合共64组，计算每个像素点属于哪个区.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @return the color histogram
	 */
	public static int[] getColorHistogram(Bitmap bitmap) {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// 区颜色分布
		int[] areaColor = new int[64];

		// 获取色彩数组。
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int pixels = bitmap.getPixel(i, j);
				int alpha = (pixels >> 24) & 0xFF;
				int red = (pixels >> 16) & 0xFF;
				int green = (pixels >> 8) & 0xFF;
				int blue = (pixels) & 0xFF;
				int redArea = 0;
				int greenArea = 0;
				int blueArea = 0;
				// 0-63 64-127 128-191 192-255
				if (red >= 192) {
					redArea = 3;
				} else if (red >= 128) {
					redArea = 2;
				} else if (red >= 64) {
					redArea = 1;
				} else if (red >= 0) {
					redArea = 0;
				}

				if (green >= 192) {
					greenArea = 3;
				} else if (green >= 128) {
					greenArea = 2;
				} else if (green >= 64) {
					greenArea = 1;
				} else if (green >= 0) {
					greenArea = 0;
				}

				if (blue >= 192) {
					blueArea = 3;
				} else if (blue >= 128) {
					blueArea = 2;
				} else if (blue >= 64) {
					blueArea = 1;
				} else if (blue >= 0) {
					blueArea = 0;
				}
				int index = redArea * 16 + greenArea * 4 + blueArea;
				// 加入
				areaColor[index] += 1;
			}
		}
		return areaColor;
	}

	/**
	 * 计算"汉明距离"（Hamming distance）。
	 * 如果不相同的数据位不超过5，就说明两张图片很相似；如果大于10，就说明这是两张不同的图片。.
	 * 
	 * @param sourceHashCode
	 *            源hashCode
	 * @param hashCode
	 *            与之比较的hashCode
	 * @return the int
	 */
	public static int hammingDistance(String sourceHashCode, String hashCode) {
		int difference = 0;
		int len = sourceHashCode.length();
		for (int i = 0; i < len; i++) {
			if (sourceHashCode.charAt(i) != hashCode.charAt(i)) {
				difference++;
			}
		}
		return difference;
	}

	/**
	 * 灰度值计算.
	 * 
	 * @param pixels
	 *            像素
	 * @return int 灰度值
	 */
	private static int rgbToGray(int pixels) {
		// int _alpha = (pixels >> 24) & 0xFF;
		int _red = (pixels >> 16) & 0xFF;
		int _green = (pixels >> 8) & 0xFF;
		int _blue = (pixels) & 0xFF;
		return (int) (0.3 * _red + 0.59 * _green + 0.11 * _blue);
	}

	/**
	 * 压缩图片
	 * 
	 * @param sourcePath
	 *            目标路径
	 * @param targetPath
	 *            压缩完图片路径
	 * @param maxSize
	 *            压缩后大小
	 */
	public static void compressBitmap(String sourcePath, String targetPath, float maxSize) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		BitmapFactory.decodeFile(sourcePath, options);

		final float originalWidth = options.outWidth;
		final float originalHeight = options.outHeight;

		float convertedWidth;
		float convertedHeight;

		if (originalWidth > originalHeight) {
			convertedWidth = maxSize;
			convertedHeight = maxSize / originalWidth * originalHeight;
		} else {
			convertedHeight = maxSize;
			convertedWidth = maxSize / originalHeight * originalWidth;
		}

		final float ratio = originalWidth / convertedWidth;

		options.inSampleSize = (int) ratio;
		options.inJustDecodeBounds = false;

		Bitmap convertedBitmap = BitmapFactory.decodeFile(sourcePath, options);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		convertedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
		FileOutputStream fileOutputStream;
		try {
			File file = new File(targetPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			fileOutputStream = new FileOutputStream(new File(targetPath));
			fileOutputStream.write(byteArrayOutputStream.toByteArray());
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据URI获取图片物理路径
	 */
	public static String getAbsoluteImagePath(Uri uri, Activity activity) {
		String[] proj = { MediaColumns.DATA };
		Cursor cursor = activity.managedQuery(uri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

}
