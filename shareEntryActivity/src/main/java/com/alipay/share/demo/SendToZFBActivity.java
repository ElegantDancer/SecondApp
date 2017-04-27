package com.alipay.share.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.alipay.share.sdk.openapi.APAPIFactory;
import com.alipay.share.sdk.openapi.APImageObject;
import com.alipay.share.sdk.openapi.APMediaMessage;
import com.alipay.share.sdk.openapi.APTextObject;
import com.alipay.share.sdk.openapi.APWebPageObject;
import com.alipay.share.sdk.openapi.IAPApi;
import com.alipay.share.sdk.openapi.SendMessageToZFB;

import java.io.File;

public class SendToZFBActivity extends Activity implements View.OnClickListener {

    private IAPApi api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_send_to_zfb);
        api = APAPIFactory.createZFBApi(getApplicationContext(), Constants.APP_ID, false);
        findViewById(R.id.text).setOnClickListener(this);
        findViewById(R.id.image).setOnClickListener(this);
        findViewById(R.id.webpage).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text:
                sendTextMessage();
                break;
            case R.id.image:
                showImageShareDialog();
                break;
            case R.id.webpage:
                sendWebPageMessage();
                break;
            default:
                break;

        }
    }

    //网页分享
    private void sendWebPageMessage() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_web_page, null);
        final EditText title = (EditText) view.findViewById(R.id.title);
        final EditText desc = (EditText) view.findViewById(R.id.desc);
        final EditText thumbUrl = (EditText) view.findViewById(R.id.thumb_url);
        final EditText url = (EditText) view.findViewById(R.id.url);
        final CheckBox timelineChecked = (CheckBox) view.findViewById(R.id.timelineChecked);
        //在支付宝版本会合并分享渠道的情况下,没必要露出选择分享渠道的选项
        if (!isAlipayIgnoreChannel()) {
            timelineChecked.setVisibility(View.VISIBLE);
        } else {
            timelineChecked.setVisibility(View.GONE);
        }
        new AlertDialog.Builder(this).setTitle("分享网页").setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        APWebPageObject webPageObject = new APWebPageObject();
                        webPageObject.webpageUrl = url.getText().toString();
                        APMediaMessage webMessage = new APMediaMessage();
                        webMessage.title = title.getText().toString();
                        webMessage.description = desc.getText().toString();
                        webMessage.mediaObject = webPageObject;
                        webMessage.thumbUrl = thumbUrl.getText().toString();
                        SendMessageToZFB.Req webReq = new SendMessageToZFB.Req();
                        webReq.message = webMessage;
                        webReq.transaction = buildTransaction("webpage");

                        //在支付宝版本会合并分享渠道的情况下,不需要传递分享场景参数
                        if (!isAlipayIgnoreChannel()) {
                            webReq.scene = timelineChecked.isChecked()
                                    ? SendMessageToZFB.Req.ZFBSceneTimeLine
                                    : SendMessageToZFB.Req.ZFBSceneSession;

                        }
                        api.sendReq(webReq);
                        finish();
                    }
                }).setNegativeButton("取消", null).show();

    }

    //文本信息分享
    private void sendTextMessage() {
        final EditText editor = new EditText(this);
        editor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editor.setText("支付宝分享测试");
        new AlertDialog.Builder(this).setView(editor).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = editor.getText().toString();

                //初始化一个APTextObject对象
                APTextObject textObject = new APTextObject();
                textObject.text = text;

                //用APTextObject对象初始化一个APMediaMessage对象
                APMediaMessage mediaMessage = new APMediaMessage();
                mediaMessage.mediaObject = textObject;

                //构造一个Req
                SendMessageToZFB.Req req = new SendMessageToZFB.Req();
                req.message = mediaMessage;

                //调用api接口发送消息到支付宝
                api.sendReq(req);

                finish();

            }
        }).setNegativeButton("取消", null).show();
    }

    //图片分享
    private void showImageShareDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_share_image, null);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view).create();
        dialog.show();
        //图片本地路径分享
        view.findViewById(R.id.image_local_path).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                sendLocalImage();
            }
        });

        //图片二进制流分享
        view.findViewById(R.id.image_byte).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                sendByteImage();
            }
        });

        //图片url分享
        view.findViewById(R.id.image_url).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                sendOnlineImage();
            }
        });
    }


    private void sendByteImage() {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        APImageObject imageObject = new APImageObject(bmp);
        APMediaMessage mediaMessage = new APMediaMessage();
        mediaMessage.mediaObject = imageObject;
        SendMessageToZFB.Req req = new SendMessageToZFB.Req();
        req.message = mediaMessage;
        req.transaction = buildTransaction("image");
        bmp.recycle();
        api.sendReq(req);
        finish();
    }

    private void sendOnlineImage() {
        final EditText editor = new EditText(this);
        editor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editor.setText("http://g.hiphotos.baidu.com/image/pic/item/43a7d933c895d14383606d9b72f082025aaf0777.jpg");
        new AlertDialog.Builder(this).setView(editor).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                APImageObject imageObject = new APImageObject();
                imageObject.imageUrl = editor.getText().toString();
                APMediaMessage mediaMessage = new APMediaMessage();
                mediaMessage.mediaObject = imageObject;
                SendMessageToZFB.Req req = new SendMessageToZFB.Req();
                req.message = mediaMessage;
                req.transaction = buildTransaction("image");
                api.sendReq(req);
                finish();
            }
        }).setNegativeButton("取消", null).show();

    }

    private void sendLocalImage() {
        final EditText editor = new EditText(this);
        editor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editor.setText("/sdcard/test.png");
        new AlertDialog.Builder(this).setView(editor).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String path = editor.getText().toString();
                File file = new File(path);
                if (!file.exists()) {
                    Toast.makeText(SendToZFBActivity.this, "选择的文件不存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                APImageObject imageObject = new APImageObject();
                imageObject.imagePath = editor.getText().toString();
                APMediaMessage mediaMessage = new APMediaMessage();
                mediaMessage.mediaObject = imageObject;
                SendMessageToZFB.Req req = new SendMessageToZFB.Req();
                req.message = mediaMessage;
                req.transaction = buildTransaction("image");
                api.sendReq(req);
                finish();
            }
        }).setNegativeButton("取消", null).show();
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


    private boolean isAlipayIgnoreChannel() {
        return api.getZFBVersionCode() >= 101;
    }
}
