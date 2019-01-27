package com.sci.accounting;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class ListViewAdapter extends BaseAdapter {

	private LinkedList<RecordBean> records = new LinkedList<>();

	private LayoutInflater mInflater;
	private Context mContext;

	public ListViewAdapter(Context context) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
	}

	public void setData(LinkedList<RecordBean> records) {
		this.records = records;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return records.size();
	}

	@Override
	public Object getItem(int position) {
		return records.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.cell_list_view, null);

			RecordBean recordBean = (RecordBean) getItem(position);
			holder = new ViewHolder(convertView, recordBean);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}
}

class ViewHolder {
	TextView tvRemark, tvAmount, tvTime;
	ImageView mvCategoryIcon;


	public ViewHolder(View itemView, RecordBean record) {
		tvRemark = itemView.findViewById(R.id.textView_remark);
		tvAmount = itemView.findViewById(R.id.textView_amount);
		tvTime = itemView.findViewById(R.id.textView_time);
		mvCategoryIcon = itemView.findViewById(R.id.imageView_category);;

		tvRemark.setText(record.getRemark());
		if (record.getType() == 1) {
			tvAmount.setText("-" + record.getAmount());
		} else {
			tvAmount.setText("+" + record.getAmount());
		}

		tvTime.setText(DateUtil.getFormattedTime(record.getTimeStamp()));


		if (record.getType() == 1) {	// 支出
			tvAmount.setText("- " + record.getAmount());
		} else {	// 收入
			tvAmount.setText("+ " + record.getAmount());
		}

		// 輸入紀錄的時間
		tvTime.setText(DateUtil.getFormattedTime(record.getTimeStamp()));

		mvCategoryIcon.setImageResource(GlobalUtil.getInstance().getResourceIcon(record.getCategory()));
	}
}
