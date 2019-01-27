package com.sci.accounting;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;


public class MainFragment extends Fragment implements AdapterView.OnItemLongClickListener {

	private View rootView;

	private TextView mTextView;
	private ListView mListView;
	private ListViewAdapter listViewAdapter;

	private LinkedList<RecordBean> records = new LinkedList<>();

	private String date = "";

	@SuppressLint("ValidFragment")
	public MainFragment(String date) {
		this.date = date;

		records = GlobalUtil.getInstance().databaseHelper.readRecords(date);

	}

	public MainFragment() {
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_main, container, false);
		initView();
		return rootView;
	}

	public void reload() {
		records = GlobalUtil.getInstance().databaseHelper.readRecords(date);
		if (listViewAdapter == null) {
			// 刷新頁面
			listViewAdapter = new ListViewAdapter(getActivity().getApplicationContext());
		}

		listViewAdapter.setData(records);
		mListView.setAdapter(listViewAdapter);

		if (listViewAdapter.getCount() > 0) {
			rootView.findViewById(R.id.no_record_layout).setVisibility(View.INVISIBLE);
		}
	}

	private void initView() {
		mTextView = rootView.findViewById(R.id.day_text);
		mListView = rootView.findViewById(R.id.listView);
		mTextView.setText(date);
		listViewAdapter = new ListViewAdapter(getContext());
		listViewAdapter.setData(records);
		mListView.setAdapter(listViewAdapter);

		if (listViewAdapter.getCount() > 0) {
			rootView.findViewById(R.id.no_record_layout).setVisibility(View.INVISIBLE);
		}

		mTextView.setText(DateUtil.getDateTitle(date));

		mListView.setOnItemLongClickListener(this);
	}


	public int getTotalCost() {
		double totalCost = 0;

		for (RecordBean record : records) {
			if (record.getType() == 1 ) {    // 支出
				totalCost -= record.getAmount();
			} else {	// 收入
				totalCost += record.getAmount();

			}
		}

		return (int) totalCost;
	}

	// 長按彈出對話框
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		// show dialog
		showDialog(position);
		return false;

	}

	private void showDialog(final int index) {
		final String[] options = {"Edit", "Remove"};

		final RecordBean selectedRecord = records.get(index);

		// 對話框
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

		builder.create();

		builder.setItems(options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// which下標
				// 0 --> Remove ， 1 --> Edit

				if (which == 0) {
					// 0 --> Remove
					String uuid = selectedRecord.getUuid();

					// 刪除紀錄
					GlobalUtil.getInstance().databaseHelper.removeRecord(uuid);

					// 刪除後重新加載listview
					reload();

					GlobalUtil.getInstance().mainActivity.updateHeader();

				} else if (which == 1) {
					// 1 --> Edit
					// AddRecordActivity
					Intent intent = new Intent(getActivity(), AddRecordActivity.class);
					Bundle extra = new Bundle();
					extra.putSerializable("record", selectedRecord);
					intent.putExtras(extra);
					startActivityForResult(intent, 1);
				}

			}
		});

		builder.setNegativeButton("Cancel", null);
		builder.create().show();
	}
}
