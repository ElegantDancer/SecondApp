package com.iqiyi.share.sina;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import java.io.File;

import org.qiyi.android.corejar.deliver.share.ShareBean;
import org.qiyi.android.plugin.share.SharePluginCenter;
import org.w3c.dom.Text;

import com.iqiyi.share.R;
import com.iqiyi.share.imagecache.CacheFactory;
import com.iqiyi.share.imagecache.ImageDataThreadPoolForShare;
import com.iqiyi.share.imagecache.QYVideoLib;
import com.iqiyi.share.imagecache.Utils;
import com.iqiyi.share.util.ShareConstans;
import com.iqiyi.share.util.ShareUtils;
import com.sina.weibo.sdk.ApiUtils;
import com.sina.weibo.sdk.api.BaseMediaObject;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseRequest;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.utils.Utility;

/**
 * Created by zhaozhenzhen on 2016/8/22.
 */
public class ShareSina {

	private final Activity mActivity;
	private final IWeiboShareAPI shareApi;

	public ShareSina(Activity activity) {
		this.mActivity = activity;
		String SINA_SHARE_APP_KEY;
		if (mActivity.getPackageName().equals("tv.pps.mobile")) {
			SINA_SHARE_APP_KEY = ShareConstans.SINA_SHARE_APP_KEY_PPS;
		} else {
			SINA_SHARE_APP_KEY = ShareConstans.SINA_SHARE_APP_KEY_IQY;
		}
		// 注册到sina App
		shareApi = WeiboShareSDK.createWeiboAPI(mActivity, SINA_SHARE_APP_KEY);
		shareApi.registerApp();
	}

	public void share(ShareBean shareBean) {
		if (null == mActivity) {
			return;
		}
		// bitmap图片控制器初始化
		switch (shareBean.getShareType()) {
		case 0:
			shareVideo(mActivity, shareBean);
			break;
		case 1:
			shareWebPage(mActivity, shareBean);
			break;
		case 2:
			shareText(mActivity, shareBean);
			break;
		case 3:
			shareImage(mActivity, shareBean);
			break;
		case 4:
			shareGif(mActivity, shareBean);
			break;
		default:
			break;
		}
		mActivity.finish();
	}
	
	
	private void shareImage(Activity activity, ShareBean shareBean){
		
		if(isSinaSupportAPI(activity)){
			
			TextObject textObject = new TextObject();
			textObject.text = getText(shareBean);
			
			String path = "";
			ImageObject imageObject = new ImageObject();
			imageObject.imageData = getImageBytes(path);
			
			if(imageObject.imageData == null){
				return;
			}
				
			shareMutiMessage(activity, imageObject, textObject, "Image");;
		}else {
			
			String path = "";
		
			ImageObject imageObject = new ImageObject();
			imageObject.imageData = getImageBytes(path);
			if(imageObject.imageData == null){
				return;
			}
				
			shareSingleMessage(activity, imageObject, "Image");
		}
	}
	
	private void shareGif(Activity activity, ShareBean shareBean){
		
		if(isSinaSupportAPI(activity)){
			TextObject textObject = new TextObject();
			textObject.text = getText(shareBean);
			
			String path = "";
			File file = new File(path);
			if(file.exists()){
				ImageObject imageObject = new ImageObject();
				imageObject.imagePath = path;
				
				shareMutiMessage(activity, imageObject, textObject, "Gif");
			}else {
				return;
			}
		}else {
			String path = "";
			File file = new File(path);
			if(file.exists()){
				ImageObject imageObject = new ImageObject();
				imageObject.imagePath = path;
				shareSingleMessage(activity, imageObject, "Gif");
			}else{
				return;
			}
		}
	}
	
	
	private void shareSingleMessage(Activity activity, ImageObject imageObject, String transType){
		WeiboMessage singleMessage = new WeiboMessage();
		singleMessage.mediaObject = imageObject;
			
		SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
		// 用transaction表示一个唯一的请求
		request.transaction = ShareUtils.buildTransaction(transType);
		request.message = singleMessage;
		sendRequestMessage(activity, request, transType);
	}
	
	
	private void shareMutiMessage(Activity activity, ImageObject imageObject, TextObject textObject, String transType){
		WeiboMultiMessage multiMessage = new WeiboMultiMessage();
		multiMessage.imageObject = imageObject;
		multiMessage.textObject = textObject;
		
		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
		// 用transaction表示一个唯一的请求
		request.transaction = ShareUtils.buildTransaction(transType);
		request.multiMessage = multiMessage;
		sendRequestMessage(activity, request, transType);
	}
	
	private String getText(ShareBean bean){
		String text = bean.getTitle();
		text = text.length() < ShareConstans.SINA_TITLE_MAX_LENGTH ? text
				: text.substring(0, ShareConstans.SINA_TITLE_MAX_LENGTH);
		
		return text;
	}
	
	private byte[] getImageBytes(String path){
		File file = new File(path);
		if(file.exists()){
			Bitmap bitmap = BitmapFactory.decodeFile(path);
			return ShareUtils.getBitmapBytes(bitmap, 85, ShareConstans.SINA_SHARE_IMAGE_MAX_LENGTH);
		}
		return null;
	}

	private void shareText(Context context, ShareBean shareBean) {
		final String sendType = "text";
		if (isSinaSupportAPI(context)) {
			WeiboMultiMessage multiMessage = new WeiboMultiMessage();
			multiMessage.textObject = getTextObject(shareBean);
			// 初始化一个request
			SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
			// 用transaction唯一表示一个请求
			request.transaction = ShareUtils.buildTransaction(sendType);
			request.multiMessage = multiMessage;
			sendRequestMessage(context, request, sendType);

		} else {
			WeiboMessage message = new WeiboMessage();
			message.mediaObject = getTextObject(shareBean);
			SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
			request.transaction = ShareUtils.buildTransaction(sendType);
			request.message = message;
			sendRequestMessage(context, request, sendType);
		}
	}

	private TextObject getTextObject(ShareBean shareBean) {
		TextObject textObject = new TextObject();
		textObject.text = shareBean.getTitle();
		return textObject;
	}

	private void shareWebPage(final Context context, final ShareBean shareBean) {
		final String sendType = "webpage";
		if (context == null) {
			return;
		}
		final WebpageObject webpageObject = new WebpageObject();
		webpageObject.identify = Utility.generateGUID();
		// 获取webpage的URL
		webpageObject.actionUrl = shareBean.getUrl();

		String title;
		String des;

		if (!TextUtils.isEmpty(shareBean.getWbTitle())) {
			title = shareBean.getWbTitle();
		} else if (!ShareUtils.isVVIsZero(shareBean.getVv())) {
			try {
				title = context.getString(R.string.sns_share_everyone_is_watching) + shareBean.getTitle();
			} catch (Exception ex) {
				title = shareBean.getTitle();
			}
		} else {
			title = shareBean.getTitle();
		}

		if (!TextUtils.isEmpty(shareBean.getWbText())) {
			des = shareBean.getWbText();
		} else {
			des = shareBean.getDes();
		}
		webpageObject.title = title.length() < ShareConstans.SINA_TITLE_MAX_LENGTH ? title
				: title.substring(0, ShareConstans.SINA_TITLE_MAX_LENGTH);
		webpageObject.description = des.length() < ShareConstans.SINA_DES_MAX_LENGTH ? des
				: des.substring(0, ShareConstans.SINA_DES_MAX_LENGTH);
		// 因为新浪中显示webpage页面不友好 更改后，title会自动显示 所以将title更改为des
		
		webpageObject.title = des.length() < ShareConstans.SINA_DEFAULT_MAX_LENGTH ? des
				: des.substring(0, ShareConstans.SINA_DEFAULT_MAX_LENGTH);;
		
		webpageObject.defaultText = webpageObject.title;
		String bitmapurl = shareBean.getBitmapUrl();


			Handler handler = new Handler(Looper.getMainLooper()) {

				private boolean hasExecutedOnce = false;

				@SuppressWarnings("deprecation")
				@Override
				public void handleMessage(Message msg) {
					// 只做一次
					if (!hasExecutedOnce) {

						Bitmap tempbitmap;

						if (msg != null && msg.obj instanceof Bitmap) {
							tempbitmap = (Bitmap) msg.obj;
						} else {

							tempbitmap = Utils
									.drawable2Bitmap(context.getResources().getDrawable(shareBean.getDfPicId()));
						}

						onShareImagePrepared(webpageObject, tempbitmap, context, shareBean, sendType);
						hasExecutedOnce = true;
					}

				}
			};

			handler.sendEmptyMessageDelayed(0, ShareConstans.HANDLER_DELAY_TIME);

			imageDataThreadPoolForShare.doTask(bitmapurl, handler, true);

		} else {
			onShareImagePrepared(webpageObject,
					Utils.drawable2Bitmap(context.getResources().getDrawable(shareBean.getDfPicId())), context,
					shareBean, sendType);
		}

	}

	private void shareVideo(final Context context, final ShareBean bean) {

		final String sendType = "video";
		if (null == context) {
			return;
		}
		final VideoObject videoObj = new VideoObject();
		videoObj.identify = Utility.generateGUID();

		// 设置播放地址
		if (null == bean.getUrl() || bean.getUrl().equals("")) {
			videoObj.actionUrl = ShareConstans.getHtml5Host_URI();
		} else {
			videoObj.actionUrl = bean.getUrl();
		}

		// 设置视频标题
		String title;

		try {
			if (!TextUtils.isEmpty(bean.getWbTitle())) {
				title = bean.getWbTitle();
			} else if (!ShareUtils.isVVIsZero(bean.getVv()) && !bean.getTitle().contains("【爱奇艺直播】")) {
				title = context.getString(R.string.sns_share_everyone_is_watching) + bean.getTitle();
			} else {
				title = bean.getTitle();
			}
		} catch (Exception ex) {
			title = bean.getTitle();
		}

		if (null != title && !title.equals("")) {
			videoObj.title = title.length() < ShareConstans.SINA_TITLE_MAX_LENGTH ? title
					: title.substring(0, ShareConstans.SINA_TITLE_MAX_LENGTH - 1);
		} else {
			videoObj.title = "视频";
		}

		// 设置视频描述内容
		String des;
		if (!TextUtils.isEmpty(bean.getWeiboText())) {
			des = bean.getWeiboText();
		} else if (!TextUtils.isEmpty(bean.getDes())) {
			des = bean.getDes();
		} else {
			des = videoObj.title;
		}
		videoObj.description = des.length() < ShareConstans.SINA_DES_MAX_LENGTH ? des
				: des.substring(0, ShareConstans.SINA_DES_MAX_LENGTH - 1);
		try {
			videoObj.duration = Integer.valueOf(bean.getDn());// 传入播放时长
		} catch (Exception ex) {
			videoObj.duration = 30;// 有时会发生InterFormationException
		}
		videoObj.defaultText = videoObj.title;
		// 设置视频分享时显示的图片
		String bitmapUrl = bean.getBitmapUrl();
		if (null != bitmapUrl && !bitmapUrl.equals("")) {
			Bitmap bitmap = null;

			if (null != QYVideoLib.mImageCacheManager) {
				bitmap = QYVideoLib.mImageCacheManager.getCache(bitmapUrl);
			}

			if (null != bitmap) {
				onShareImagePrepared(videoObj, bitmap, context, bean, sendType);
				return;
			}

			ImageDataThreadPoolForShare imageDataThreadPoolForShare = new ImageDataThreadPoolForShare(context, null);
			Handler mHandler = new Handler(Looper.getMainLooper()) {
				private boolean hasExecutedOnce = false;

				@Override
				public void handleMessage(Message msg) {
					// 只做一次
					if (!hasExecutedOnce) {
						Bitmap bitmap;

						if (null != msg && msg.obj instanceof Bitmap) {
							bitmap = (Bitmap) msg.obj;
						} else {
							bitmap = Utils.drawable2Bitmap(context.getResources().getDrawable(bean.getDfPicId()));
						}

						onShareImagePrepared(videoObj, bitmap, context, bean, sendType);
						hasExecutedOnce = true;
					}
				}
			};

			mHandler.sendEmptyMessageDelayed(0, ShareConstans.HANDLER_DELAY_TIME);
			imageDataThreadPoolForShare.doTask(bitmapUrl, mHandler, false);
		} else {
			onShareImagePrepared(videoObj, Utils.drawable2Bitmap(context.getResources().getDrawable(bean.getDfPicId())),
					context, bean, sendType);
		}
	}

	private void sendRequestMessage(Context context, BaseRequest request, String sendType) {
		// 准备数据，启动分享activity
		if (shareApi.sendRequest((Activity) context, request)) {

		} else {
			// 发送消息失败时，提示 分享失败
			SharePluginCenter.onShareQQorQZoneCallBack(context.getResources().getString(R.string.sns_share_fail));
		}
	}

	private void onShareImagePrepared(BaseMediaObject mediaObject, Bitmap bitmap, Context context, ShareBean shareBean,
			String sendType) {
		if (null == mediaObject || null == bitmap || null == context || null == shareBean) {
			return;
		}

		// 对分享的imageObject照片要求和缩略图不一样
		ImageObject imageObject = null;
		if (shareBean.getShareType() == 1) {
			imageObject = new ImageObject();
			imageObject.imageData = ShareUtils.getBitmapBytes(bitmap, 85, ShareConstans.SINA_SHARE_BITMAP_MAX_LENGTH);
		}

		mediaObject.thumbData = ShareUtils.getBitmapBytes(bitmap, 25, ShareConstans.SINA_BITMAP_MAX_LENTH);
		/**
		 * 先进行API的判断
		 */
		if (isSinaSupportAPI(context)) {

			WeiboMultiMessage multiMessage = new WeiboMultiMessage();
			// 修复微博webpage页面分享出去后，没有缩略图的问题
			if (null != imageObject && shareBean.getShareType() == 1) {
				multiMessage.imageObject = imageObject;
				// ImageObject imageObject = new ImageObject();
				// imageObject.setImageObject(bitmap);
				// multiMessage.imageObject = imageObject;
			}
			multiMessage.mediaObject = mediaObject;
			// 初始化一个request
			SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
			// 用transaction表示一个唯一的请求
			request.transaction = ShareUtils.buildTransaction(sendType);
			request.multiMessage = multiMessage;
			sendRequestMessage(context, request, sendType);

		} else {
			WeiboMessage message = new WeiboMessage();
			message.mediaObject = mediaObject;
			SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
			request.transaction = ShareUtils.buildTransaction(sendType);
			request.message = message;
			sendRequestMessage(context, request, sendType);
		}
	}

	/**
	 * 检查是否可以同时支持发送 文本、图片以及 （网页、视频、音频）中的一种 supportApi >= 10351
	 */
	private boolean isSinaSupportAPI(Context context) {
		return null != context && shareApi.getWeiboAppSupportAPI() >= ApiUtils.BUILD_INT_VER_2_2;
	}
}