package com.sci.accounting;

import android.util.Log;

import java.io.Serializable;
import java.util.UUID;

public class RecordBean implements Serializable {

	public enum RecordType {
		RECORD_TYPE_EXPENSE, RECORD_TYPE_INCOME     // 支出、收入
	}

	private double amount;
	private RecordType type;	// 收入或支出
	private String category;	// 類別
	private String remark;		// 備註
	private String date;    	// 日期 2017-12-12
	private long timeStamp;		// 時間戳

	private String uuid;    	// id 引入UUID，保證唯一，類似身份正號

	public RecordBean() {
		uuid = UUID.randomUUID().toString();
		timeStamp = System.currentTimeMillis();
		date = DateUtil.getFormattedDate();
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getType() {

		if (this.type == RecordType.RECORD_TYPE_EXPENSE) {
			return 1;
		} else {
			return 2;
		}
	}

	public void setType(int type) {
		if (type == 1) {
			this.type = RecordType.RECORD_TYPE_EXPENSE;
		} else {
			this.type = RecordType.RECORD_TYPE_INCOME;
		}
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}


