package com.example.activity;;

public class MessageItem {
	// Text
	public static final int MESSAGE_TYPE_TEXT = 1;
	// image
	public static final int MESSAGE_TYPE_IMG = 2;
	// file
	public static final int MESSAGE_TYPE_FILE = 3;

	private int msgType;
	private String name;// ��Ϣ����
	private long time;// ��Ϣ����
	private String message;// ��Ϣ����
	private int headImg;
	private boolean isComMeg = true;// �Ƿ�Ϊ�յ�����Ϣ

	private int isNew;

	public MessageItem() {
		// TODO Auto-generated constructor stub
	}

	public MessageItem(int msgType, String name, long date, String message,
			int headImg, boolean isComMeg, int isNew) {
		super();
		this.msgType = msgType;
		this.name = name;
		this.time = date;
		this.message = message;
		this.headImg = headImg;
		this.isComMeg = isComMeg;
		this.isNew = isNew;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDate() {
		return time;
	}

	public void setDate(long date) {
		this.time = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getHeadImg() {
		return headImg;
	}

	public void setHeadImg(int headImg) {
		this.headImg = headImg;
	}

	public boolean isComMeg() {
		return isComMeg;
	}

	public void setComMeg(boolean isComMeg) {
		this.isComMeg = isComMeg;
	}

	public static int getMessageTypeText() {
		return MESSAGE_TYPE_TEXT;
	}

	public static int getMessageTypeImg() {
		return MESSAGE_TYPE_IMG;
	}

	public static int getMessageTypeFile() {
		return MESSAGE_TYPE_FILE;
	}

	public int getIsNew() {
		return isNew;
	}

	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}

}
