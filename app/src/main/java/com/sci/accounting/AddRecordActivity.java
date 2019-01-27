package com.sci.accounting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddRecordActivity extends AppCompatActivity implements View.OnClickListener, CategoryRecyclerAdapter.OnCategoryClickListener {

	private TextView mTvAmount;
	private String userInput = "";
	private EditText mEditText;

	private RecyclerView recyclerView;
	private CategoryRecyclerAdapter adapter;

	private String category = "General";
	private RecordBean.RecordType type = RecordBean.RecordType.RECORD_TYPE_EXPENSE;
	private String remark = category;

	RecordBean record = new RecordBean();

	private boolean inEdit = false;

	int flag = 1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_record);

		handleView();

	}

	private void handleView() {
		findViewById(R.id.keyboard_one).setOnClickListener(this);
		findViewById(R.id.keyboard_two).setOnClickListener(this);
		findViewById(R.id.keyboard_three).setOnClickListener(this);
		findViewById(R.id.keyboard_four).setOnClickListener(this);
		findViewById(R.id.keyboard_five).setOnClickListener(this);
		findViewById(R.id.keyboard_six).setOnClickListener(this);
		findViewById(R.id.keyboard_seven).setOnClickListener(this);
		findViewById(R.id.keyboard_eight).setOnClickListener(this);
		findViewById(R.id.keyboard_nine).setOnClickListener(this);
		findViewById(R.id.keyboard_zero).setOnClickListener(this);

		mTvAmount = findViewById(R.id.textView_amount);
		mEditText = findViewById(R.id.editText);
		mEditText.setText(remark);

		handleDot();			// 處理小數點點擊事件
		handleBackspace();		// 處理點擊back事件
		handleDone();			// 處理點擊完成事件
		handleTypeChange();		// 處理點擊類型改變事件

		recyclerView = findViewById(R.id.recyclerView);
		adapter = new CategoryRecyclerAdapter(getApplicationContext());
		recyclerView.setAdapter(adapter);
		GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
		recyclerView.setLayoutManager(gridLayoutManager);
		adapter.notifyDataSetChanged();

		adapter.setOnCategoryClickListener(this);

		RecordBean record = (RecordBean) getIntent().getSerializableExtra("record");

		if (record != null) {
			inEdit = true;
			this.record = record;
		}
	}

	// 處理小數點點擊事件
	private void handleDot() {
		findViewById(R.id.keyboard_dot).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (!userInput.contains(".")) {
					userInput += ".";
				}
			}
		});

	}

	// 處理點擊back事件
	private void handleBackspace() {
		findViewById(R.id.keyboard_backspace).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (userInput.length() > 0) {
					userInput = userInput.substring(0, userInput.length() - 1);
				}

				// 11.
				if (userInput.length() > 0 && userInput.charAt(userInput.length() - 1) == '.') {
					userInput = userInput.substring(0, userInput.length() - 1);
				}

				updateAmountText();

			}
		});

	}

	// 處理點擊完成事件
	private void handleDone() {
		findViewById(R.id.keyboard_done).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!userInput.equals("")) {
					double amount = Double.valueOf(userInput);


					record.setAmount(amount);

					if (type == RecordBean.RecordType.RECORD_TYPE_EXPENSE) {
						record.setType(1);
					} else {
						record.setType(2);
					}
					record.setCategory(adapter.getSelected());  // record.setCategory(category);
					record.setRemark(mEditText.getText().toString());

					if (inEdit) {
						GlobalUtil.getInstance().databaseHelper.editRecord(record.getUuid(), record);
					} else {
						GlobalUtil.getInstance().databaseHelper.addRecord(record);
					}

					finish();

					Log.d("AddRecordActivity", "final amount is" + amount);

				} else {
					Toast.makeText(AddRecordActivity.this, "Error!Please Input numbers!!", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	// 處理點擊類型改變事件
	private void handleTypeChange() {
		findViewById(R.id.keyboard_type).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (type == RecordBean.RecordType.RECORD_TYPE_EXPENSE) {
					type = RecordBean.RecordType.RECORD_TYPE_INCOME;
				} else {
					type = RecordBean.RecordType.RECORD_TYPE_EXPENSE;
				}

				adapter.changeType(type);
				category = adapter.getSelected();

				// 判斷圖標不要無限增加
				if (flag == 0){
					((ImageButton) findViewById(R.id.keyboard_type)).setImageResource(R.drawable.icon_add_white);
					flag=1;
				}else {
					((ImageButton) findViewById(R.id.keyboard_type)).setImageResource(R.drawable.icon_backspace);
					flag=0;
				}
			}
		});

	}


	@Override
	public void onClick(View v) {
		Button button = (Button) v;
		String input = button.getText().toString();

		if (userInput.contains(".")) {
			// 11. 11.1  11.11
			if (userInput.split("\\.").length == 1 || userInput.split("\\.")[1].length() < 2) {   // 11.   // 11.12
				userInput += input;
			}
		} else {
			userInput += input;
		}

		updateAmountText();
	}

	private void updateAmountText() {
		if (userInput.contains(".")) {
			// 11.
			if (userInput.split("\\.").length == 1) {
				mTvAmount.setText(userInput + "00");
			} else if (userInput.split("\\.")[1].length() == 1) {   // 11.1
				mTvAmount.setText(userInput + "0");
			} else if (userInput.split("\\.")[1].length() == 2) {    // 11.11
				mTvAmount.setText(userInput);
			}
		} else {
			// ""
			if (userInput.equals("")) {
				mTvAmount.setText("0.00");
			} else {
				mTvAmount.setText(userInput + ".00");

			}
		}
	}

	@Override
	public void onClick(String category) {
		this.category = category;
		mEditText.setText(category);
	}
}
