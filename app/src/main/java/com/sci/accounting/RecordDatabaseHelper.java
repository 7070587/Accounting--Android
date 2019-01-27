package com.sci.accounting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.LinkedList;

public class RecordDatabaseHelper extends SQLiteOpenHelper {

	private static String TAG = "RecordDatabaseHelper";

	public static final String DB_NAME = "Record";		// 數據庫名稱
	public static final String CREATE_RECORD_DB = "create table Record ("
			+ "id integer primary key autoincrement, "
			+ "uuid text, "
			+ "type integer, "
			+ "category text, "
			+ "remark text, "
			+ "amount double, "
			+ "time integer, "
			+ "date date);";

	public static final String SELECT_RECORD_DB = "select DISTINCT * from Record where date = ? order by time asc;";
	public static final String SELECT_RECORD_DATE = "select DISTINCT * from Record order by date asc;";

	public RecordDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	// 建立數據庫
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_RECORD_DB);
	}

	// 更新數據庫
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	// 增
	public long addRecord(RecordBean bean) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("uuid", bean.getUuid());
		values.put("type", bean.getType());
		values.put("category", bean.getCategory());
		values.put("remark", bean.getRemark());
		values.put("amount", bean.getAmount());
		values.put("time", bean.getTimeStamp());
		values.put("date", bean.getDate());

		long rowId = db.insert(DB_NAME, null, values);
		Log.d("RecordDatabaseHelper", bean.getUuid() + "add");
		values.clear();

		return rowId;
	}

	// 刪
	public void removeRecord(String uuid) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(DB_NAME, "uuid = ?", new String[]{uuid});
	}

	// 改
	public void editRecord(String uuid, RecordBean record) {
		removeRecord(uuid);
		record.setUuid(uuid);
		addRecord(record);
	}

	// 查
	public LinkedList<RecordBean> readRecords(String dataStr) {

		LinkedList<RecordBean> records = new LinkedList<>();

		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.rawQuery(SELECT_RECORD_DB, new String[]{dataStr});

//		if (cursor.moveToFirst()) {
//			do {
//				String uuid = cursor.getString(cursor.getColumnIndex("uuid"));
//				int type = cursor.getInt(cursor.getColumnIndex("type"));
//				String category = cursor.getString(cursor.getColumnIndex("category"));
//				String remark = cursor.getString(cursor.getColumnIndex("remark"));
//				Double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
//				long timeStamp = cursor.getLong(cursor.getColumnIndex("time"));
//				String date = cursor.getString(cursor.getColumnIndex("date"));
//
//				RecordBean record = new RecordBean();
//				record.setUuid(uuid);
//				record.setType(type);
//				record.setCategory(category);
//				record.setRemark(remark);
//				record.setAmount(amount);
//				record.setTimeStamp(timeStamp);
//				record.setDate(date);
//
//				cursor.close();
//              records.add(record);
//
//			} while (cursor.moveToNext());
//		}

		if (cursor.moveToFirst()) {
			while (cursor.moveToNext()) {
				String uuid = cursor.getString(cursor.getColumnIndex("uuid"));
				int type = cursor.getInt(cursor.getColumnIndex("type"));
				String category = cursor.getString(cursor.getColumnIndex("category"));
				String remark = cursor.getString(cursor.getColumnIndex("remark"));
				Double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
				long timeStamp = cursor.getLong(cursor.getColumnIndex("time"));
				String date = cursor.getString(cursor.getColumnIndex("date"));

				RecordBean record = new RecordBean();
				record.setUuid(uuid);
				record.setType(type);
				record.setCategory(category);
				record.setRemark(remark);
				record.setAmount(amount);
				record.setTimeStamp(timeStamp);
				record.setDate(date);

				records.add(record);
			}
		}

		cursor.close();
		return records;
	}

	public LinkedList<String> getAvailableDate() {

		LinkedList<String> dates = new LinkedList<>();

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(SELECT_RECORD_DATE, new String[]{});

		if (cursor.moveToFirst()) {
			while (cursor.moveToNext()) {

				String date = cursor.getString(cursor.getColumnIndex("date"));

				// 同一天有多筆消費，日期只需要出現一次
				if (!dates.contains(date)) {
					dates.add(date);
				}
			}
		}


//		if (cursor.moveToFirst()) {
//			do {
//				String date = cursor.getString(cursor.getColumnIndex("date"));
//
//				// 同一天有多筆消費，日期只需要出現一次
//				if (!dates.contains(date)) {
//					dates.add(date);
//				}
//			} while (cursor.moveToNext());
//		}
//
		cursor.close();
		return dates;
	}

}
