package com.iqiyi.share.util;

public class ShareConstans {

	public static final String QQ_SHARE_APP_ID_IQY = "206978";
	public static final String QQ_SHARE_APP_ID_PPS = "206978";
	public static final String AP_SHARE_APP_ID_IQY = "2015080500199900";
	public static final String AP_SHARE_APP_ID_PPS = "2015082000224988";
	public static final String SINA_SHARE_APP_KEY_IQY = "1724732452";
	public static final String SINA_SHARE_APP_KEY_PPS = "1724120758";

	
	//限定的标题长度
	public static final int SINA_TITLE_MAX_LENGTH = 140;
	//限定的内容长度
	public static final int SINA_DES_MAX_LENGTH = 1024;
	
	public static final int SINA_DEFAULT_MAX_LENGTH = 120;
	/**
	 * sina客户端分享 图片最大限制 32kb
	 */
	public static final double SINA_BITMAP_MAX_LENTH = 32.0; //kb
	public static final double SINA_SHARE_BITMAP_MAX_LENGTH = 300.0;//kb 图片清晰度和发送成功的取舍
	
	public static final double SINA_SHARE_IMAGE_MAX_LENGTH = 2048.0;//kb 
	
	public static final double SINA_SHARE_GIF_MAX_LENGTH = 10 * 1024.0;//kb 图片清晰度和发送成功的取舍

	public static final int HANDLER_DELAY_TIME = 1600;//ms

	public static final String TAG_SINA = "sina";
	
	 //需要跨进程的放在这个文件
    public static final String BASE_CORE_SP_FILE_MULTIPRO="base_core_file_multiprocess";
    
    //使用两种方式扫描sd卡,然后merge
    public static final String KEY_SCAN_SD_DOUBLE="scan_sd_double";
    
    /**
     * 技术产品中心  微信分享h5播放地址域名
     */
    private static String HTML5_PLAY_HOST = "m.iqiyi.com";
    
    /**
     * @return 返回html5播放url
     */
    public static String getHtml5Host_URI() {
        return "http://" + HTML5_PLAY_HOST + "/play.html";
    }

    /**
	 * jpg等图片本地缓存时把后缀名（.jpg）自动修改替换，为了避免手机媒体库（图片库）显示客户端缓存的图片。
	 */
	public final static String QIYI_SUFFIX_PIC = ".qiyi_suffix_pic";
	
	/**
     * Unknown storage state, such as when a path isn't backed by known storage
     * media.
     *
     * @see #getStorageState(File)
     */
    public static final String MEDIA_UNKNOWN = "unknown";


	

}
