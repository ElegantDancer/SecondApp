package com.jacp.demo.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * 保存数据库中的常量
 * @author jacp
 *
 */
public class Provider {
	
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jacp.demo";

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.jacp.demo";

    /**
     * 保存programmer表中用到的常量
     * @author jacp
     *
     */
	public static final class ProgrammerColumns implements BaseColumns {
		// 注意两张表的authority要不一样
		public static final String AUTHORITY = "com.jacp.provider.demo.programmer";
		public static final Uri CONTENT_URI = Uri.parse("content://"+ AUTHORITY +"/programmers");
		public static final String TABLE_NAME = "programmer";
		public static final String DEFAULT_SORT_ORDER = "age desc";
		
		public static final String NAME = "name";
		public static final String AGE = "age";
		
	}
	
	/**
	 * 保存leader表中用到的常量
	 * @author mayliang
	 *
	 */
	public static final class LeaderColumns implements BaseColumns {
		public static final String AUTHORITY = "com.jacp.provider.demo.leader";
		public static final Uri CONTENT_URI = Uri.parse("content://"+ AUTHORITY +"/leaders");
		public static final String TABLE_NAME = "leader";
		public static final String DEFAULT_SORT_ORDER = "level desc";
		
		public static final String NAME = "name";
		public static final String TITLE = "title";
		public static final String LEVEL = "level";
		
	}
	
}
