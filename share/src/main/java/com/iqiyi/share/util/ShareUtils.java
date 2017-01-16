package com.iqiyi.share.util;

import com.iqiyi.share.imagecache.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.DisplayMetrics;

public class ShareUtils {
	
    public static final int CPU_COUNT = getCPUCount();

	
	public static boolean isNeedShowVV(String vv) {
		if (TextUtils.isEmpty(vv)) {
			return false;
		}
		if (vv.endsWith("亿")) {
			return true;
		}
		if (vv.endsWith("万")) {
			try {
				float f = Float.valueOf(vv.substring(0, vv.length() - 1));
				if ((int) f > 100) {
					return true;
				}
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}
	
	public static boolean isVVIsZero(String vv) {
		if (TextUtils.isEmpty(vv)) {
			return true;
		}
		
		if ("0".equals(vv)) {
			return true;
		}
		
		return false;
	}
	
	 public static boolean isEmpty(String str) {
	        if (null == str || "".equals(str) || "null".equals(str)) {
	            return true;
	        } else {
	            if (str.length() > 4) {
	                return false;
	            } else {
	                return str.equalsIgnoreCase("null");
	            }

	        }
	    }
	
	/**
	 * liuzm 获取边界压缩的bitmap流
	 *
	 * @param context
	 * @param _b
	 * @return
	 */
	public static Bitmap byteArray2ImgBitmap(Context context, byte[] _b) {
		return zoomBitmap(context, _b);
	}

	public static Bitmap zoomBitmap(Context context, byte[] _b) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		int w = displayMetrics.widthPixels;
		int h = displayMetrics.heightPixels;
		int d = displayMetrics.densityDpi;

		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;

		try {
			BitmapFactory.decodeByteArray(_b, 0, _b.length, opts);

			// int x = 2;
			int x = computeSampleSize(opts, w > h ? w : h, w * h);
			opts.inTargetDensity = d;
			opts.inSampleSize = x;
			opts.inJustDecodeBounds = false;

			opts.inDither = false;
			opts.inPurgeable = true;

			return BitmapFactory.decodeByteArray(_b, 0, _b.length, opts);
		} catch (OutOfMemoryError ooe) {
			ooe.printStackTrace();
			System.gc();
			// VMRuntime.getRuntime().setTargetHeapUtilization(0.75f);
		}
		return null;
	}
	    
	public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}
	
	private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128
				: (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}
	
	  /**
     * 是否使用两种方式扫描sd卡然后合并
     * @param mContext
     * @return
     */
    public static boolean scanSDDoubleAndMerge(Context mContext){
        if(mContext!=null){
            SharedPreferences pref=mContext.getSharedPreferences(ShareConstans.BASE_CORE_SP_FILE_MULTIPRO,Context.MODE_MULTI_PROCESS);
            return pref.getBoolean(ShareConstans.KEY_SCAN_SD_DOUBLE,false);
        }
        return false;
    }
    
    /**
     * 创建唯一标识
     *
     * @param type
     * @return 返回唯一标识
     */
    public static String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    
    public static int getCPUCount() {
        int ret = Runtime.getRuntime().availableProcessors();
        ret = ret < 2 ? 2 : ret > 8 ? 8 : ret;
        return ret;
    }
    
    /**
	 *  得到如何要求的缩略图  和  分享出去的图片
	 * @param bitmap
	 * @param quality 图片质量  0 ~ 100
	 * @param maxSize 图片的最大size
     * @return
     */
	public static byte[] getBitmapBytes(Bitmap bitmap, int quality, double maxSize){
		// 取得突破字节流
		byte[] tempbytes = Utils.bmpToByteArray(bitmap, quality, false);
		// 取得突破大小
		int size = tempbytes.length / 1024;

		// 图片大小符合要求
		if (size < maxSize) {
			bitmap.recycle();
		}else {
			// 获取bitmap大小 是允许大小的多少倍
			double i = size / maxSize;
			// 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
			// （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
			bitmap = Utils.zoomImage(bitmap, bitmap.getWidth() / Math.sqrt(i), bitmap.getHeight() / Math.sqrt(i));
			// tempbitmap是新生成的图片，需要释放。
			tempbytes = Utils.bmpToByteArray(bitmap, quality, true);
		}

		return tempbytes;
	}
}
