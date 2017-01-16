package com.example.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.org.adapter.FaceAdapter;
import com.org.adapter.FacePageAdeapter;
import com.org.adapter.MessageAdapter;
import com.org.util.MyApplication;
import com.org.util.SharePreferenceUtil;
import com.org.view.CirclePageIndicator;
import com.org.view.JazzyViewPager;
import com.org.view.JazzyViewPager.TransitionEffect;
import com.org.xlistview.MsgListView;
import com.org.xlistview.MsgListView.IXListViewListener;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity
        implements
        OnClickListener,
        OnTouchListener,
        IXListViewListener {

    private Button sendBtn;
    private ImageButton faceBtn;
    private LinearLayout faceLinearLayout;
    private EditText msgEt;
    private InputMethodManager mInputMethodManager;
    private MessageAdapter mMessageAdapter;
    private JazzyViewPager faceViewPager;
    private MsgListView mMsgListView;
    private MyApplication mApplication;
    private SharePreferenceUtil mSpUtil;
    private WindowManager.LayoutParams mLayoutParams;
    private List<String> mListFaceKeys;
    private int currentPage = 0;
    private boolean isFaceShow = false;
    private static int MsgPagerNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_main);
        initData();
        initUI();
        initFacePage();
    }

    private void initData() {
        mApplication = MyApplication.getInstance();
        //SharePreference�洢��
        mSpUtil = new SharePreferenceUtil(this, "message_save");
        //��ʼ����Ϣ�б�������
        mMessageAdapter = new MessageAdapter(this, initMsgData());

        //���ر�����б�
        Set<String> keySet = MyApplication.getInstance().getFaceMap().keySet();
        mListFaceKeys = new ArrayList<String>();
        mListFaceKeys.addAll(keySet);
        MsgPagerNum = 0;
    }

    private void initUI() {
        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        //��ȡ���ڴ�������
        mLayoutParams = getWindow().getAttributes();

        mMsgListView = (MsgListView) findViewById(R.id.msg_listView);
        // ����ListView���ر�������뷨
        mMsgListView.setOnTouchListener(this);
        mMsgListView.setPullLoadEnable(false);
        mMsgListView.setXListViewListener(this);
        mMsgListView.setAdapter(mMessageAdapter);
        mMsgListView.setSelection(mMessageAdapter.getCount() - 1);

        sendBtn = (Button) findViewById(R.id.send_btn);
        faceBtn = (ImageButton) findViewById(R.id.face_btn);
        faceLinearLayout = (LinearLayout) findViewById(R.id.face_ll);
        msgEt = (EditText) findViewById(R.id.msg_et);
        faceLinearLayout = (LinearLayout) findViewById(R.id.face_ll);
        faceViewPager = (JazzyViewPager) findViewById(R.id.face_pager);

        //�������ؼ�
        TextView mTitle = (TextView) findViewById(R.id.ivTitleName);
        mTitle.setText("ĬĬ����");
        TextView mTitleLeftBtn = (TextView) findViewById(R.id.ivTitleBtnLeft);
        mTitleLeftBtn.setVisibility(View.VISIBLE);
        mTitleLeftBtn.setOnClickListener(this);

        //�����Ĵ��������İ�
        msgEt.setOnTouchListener(this);
        msgEt.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mLayoutParams.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                            || isFaceShow) {
                        faceLinearLayout.setVisibility(View.GONE);
                        isFaceShow = false;
                        // imm.showSoftInput(msgEt, 0);
                        return true;
                    }
                }
                return false;
            }
        });

        //������ʵʱ���볤�ȵļ���
        msgEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    sendBtn.setEnabled(true);
                } else {
                    sendBtn.setEnabled(false);
                }
            }
        });

        faceBtn.setOnClickListener(this);
        sendBtn.setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.msg_listView:   //ListView����ʵ��
                mInputMethodManager.hideSoftInputFromWindow(msgEt.getWindowToken(), 0);
                faceLinearLayout.setVisibility(View.GONE);
                isFaceShow = false;
                break;
            case R.id.msg_et:    //�������ʵ��
                mInputMethodManager.showSoftInput(msgEt, 0);
                faceLinearLayout.setVisibility(View.GONE);
                isFaceShow = false;
                break;

            default:
                break;
        }
        return false;
    }

    //��ʷ���ݣ��ڿ�ʼʱ��ʾ
    private List<MessageItem> initMsgData() {
        List<MessageItem> msgList = new ArrayList<MessageItem>();// ��Ϣ��������

        MessageItem item = new MessageItem(MessageItem.MESSAGE_TYPE_TEXT,
                mSpUtil.getNick(), System.currentTimeMillis(), "��ʷ��Ϣ",
                mSpUtil.getHeadIcon(), false, 0);
        msgList.add(item);
        return msgList;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.face_btn:  //��������
                if (!isFaceShow) {
                    mInputMethodManager.hideSoftInputFromWindow(msgEt.getWindowToken(), 0);
                    try {
                        Thread.sleep(80);// �����ʱ���һ����Ļ������
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    faceLinearLayout.setVisibility(View.VISIBLE);
                    isFaceShow = true;
                } else {
                    faceLinearLayout.setVisibility(View.GONE);
                    isFaceShow = false;
                }
                break;
            case R.id.send_btn:// ������Ϣ
                String msg = msgEt.getText().toString();
                MessageItem item = new MessageItem(MessageItem.MESSAGE_TYPE_TEXT,
                        mSpUtil.getNick(), System.currentTimeMillis(), msg,
                        mSpUtil.getHeadIcon(), false, 0);
                mMessageAdapter.upDateMsg(item);

                mMsgListView.setSelection(mMessageAdapter.getCount() - 1);
                msgEt.setText("");
                break;
            case R.id.ivTitleBtnLeft:
                finish();
                break;
//		case R.id.ivTitleBtnRigh:
//			break;
            default:
                break;
        }

    }

    //���ر��飬�Լ����÷�ҳЧ��
    private void initFacePage() {
        List<View> lv = new ArrayList<View>();
        for (int i = 0; i < MyApplication.NUM_PAGE; ++i)
            lv.add(getGridView(i));
        FacePageAdeapter adapter = new FacePageAdeapter(lv, faceViewPager);
        faceViewPager.setAdapter(adapter);
        faceViewPager.setCurrentItem(currentPage);
        faceViewPager.setTransitionEffect(mEffects[mSpUtil.getFaceEffect()]);
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(faceViewPager);
        adapter.notifyDataSetChanged();
        faceLinearLayout.setVisibility(View.GONE);
        indicator.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                currentPage = arg0;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // do nothing
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // do nothing
            }
        });

    }

    //������ؼ����ã����ñ���
    private GridView getGridView(int i) {
        GridView gv = new GridView(this);
        gv.setNumColumns(7);      //һ����ʾ7������
        gv.setSelector(new ColorDrawable(Color.TRANSPARENT));// ����GridViewĬ�ϵ��Ч��
        gv.setBackgroundColor(Color.TRANSPARENT);
        gv.setCacheColorHint(Color.TRANSPARENT);
        gv.setHorizontalSpacing(1);
        gv.setVerticalSpacing(1);
        gv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        gv.setGravity(Gravity.CENTER);
        gv.setAdapter(new FaceAdapter(this, i));
        gv.setOnTouchListener(forbidenScroll());        // ��ֹ��pageview�ҹ���
        gv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == MyApplication.NUM) {   // ɾ���������λ��
                    int selection = msgEt.getSelectionStart();
                    String text = msgEt.getText().toString();
                    if (selection > 0) {
                        String text2 = text.substring(selection - 1);
                        if ("]".equals(text2)) {
                            int start = text.lastIndexOf("[");
                            int end = selection;
                            msgEt.getText().delete(start, end);
                            return;
                        }
                        msgEt.getText().delete(selection - 1, selection);
                    }
                } else {
                    int count = currentPage * MyApplication.NUM + arg2;
                    // ע�͵Ĳ��֣���EditText����ʾ�ַ���
                    // String ori = msgEt.getText().toString();
                    // int index = msgEt.getSelectionStart();
                    // StringBuilder stringBuilder = new StringBuilder(ori);
                    // stringBuilder.insert(index, keys.get(count));
                    // msgEt.setText(stringBuilder.toString());
                    // msgEt.setSelection(index + keys.get(count).length());

                    // �����ⲿ�֣���EditText����ʾ����
                    Bitmap bitmap = BitmapFactory.decodeResource(
                            getResources(), (Integer) MyApplication
                                    .getInstance().getFaceMap().values()
                                    .toArray()[count]);
                    if (bitmap != null) {
                        int rawHeigh = bitmap.getHeight();
                        int rawWidth = bitmap.getHeight();
                        int newHeight = 40;
                        int newWidth = 40;
                        // ������������
                        float heightScale = ((float) newHeight) / rawHeigh;
                        float widthScale = ((float) newWidth) / rawWidth;
                        // �½�������
                        Matrix matrix = new Matrix();
                        matrix.postScale(heightScale, widthScale);
                        // ����ͼƬ����ת�Ƕ�
                        // matrix.postRotate(-30);
                        // ����ͼƬ����б
                        // matrix.postSkew(0.1f, 0.1f);
                        // ��ͼƬ��Сѹ��
                        // ѹ����ͼƬ�Ŀ�͸��Լ�kB��С����仯
                        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                                rawWidth, rawHeigh, matrix, true);
                        ImageSpan imageSpan = new ImageSpan(MainActivity.this,
                                newBitmap);
                        String emojiStr = mListFaceKeys.get(count);
                        SpannableString spannableString = new SpannableString(
                                emojiStr);
                        spannableString.setSpan(imageSpan,
                                emojiStr.indexOf('['),
                                emojiStr.indexOf(']') + 1,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        msgEt.append(spannableString);
                    } else {
                        String ori = msgEt.getText().toString();
                        int index = msgEt.getSelectionStart();
                        StringBuilder stringBuilder = new StringBuilder(ori);
                        stringBuilder.insert(index, mListFaceKeys.get(count));
                        msgEt.setText(stringBuilder.toString());
                        msgEt.setSelection(index + mListFaceKeys.get(count).length());
                    }
                }
            }
        });
        return gv;
    }

    // ��ֹ��pageview�ҹ���
    private OnTouchListener forbidenScroll() {
        return new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true;
                }
                return false;
            }
        };
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        mInputMethodManager.hideSoftInputFromWindow(msgEt.getWindowToken(), 0);
        faceLinearLayout.setVisibility(View.GONE);
        isFaceShow = false;
        super.onPause();
    }


    //��������ˢ�µ�Ч��
    @Override
    public void onRefresh() {
        MsgPagerNum++;
        List<MessageItem> msgList = initMsgData();
        int position = mMessageAdapter.getCount();
        mMessageAdapter.setMessageList(msgList);
        mMsgListView.stopRefresh();

        mMsgListView.setSelection(mMessageAdapter.getCount() - position - 1);
        Log.i("Show", "MsgPagerNum = " + mMessageAdapter + ", adapter.getCount() = "
                + mMessageAdapter.getCount());

    }

    @Override
    public void onLoadMore() {
        // TODO Auto-generated method stub

    }

    private TransitionEffect mEffects[] = {TransitionEffect.Standard,
            TransitionEffect.Tablet, TransitionEffect.CubeIn,
            TransitionEffect.CubeOut, TransitionEffect.FlipVertical,
            TransitionEffect.FlipHorizontal, TransitionEffect.Stack,
            TransitionEffect.ZoomIn, TransitionEffect.ZoomOut,
            TransitionEffect.RotateUp, TransitionEffect.RotateDown,
            TransitionEffect.Accordion,};// ���鷭ҳЧ��

}
