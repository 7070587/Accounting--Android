package com.sci.accounting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.Date;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

	private ViewPager mViewPager;
	private MainViewPagerAdapter pagerAdapter;		// 填充ViewPager
	private Context context = MainActivity.this;	// 上下文對象
	private TickerView amountText;					// 總金額
	private TextView dateText;						// 日期

	private int currentPagerPosition = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 必須在最開始初始化數據庫
		GlobalUtil.getInstance().setContext(getApplicationContext());
//		GlobalUtil globalUtil = GlobalUtil.getInstance();
//		globalUtil.databaseHelper = new RecordDatabaseHelper(context, RecordDatabaseHelper.DB_NAME, null, 1);

		GlobalUtil.getInstance().mainActivity = this;

		handleView();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		pagerAdapter.reload();
		updateHeader();
	}

	private void handleView() {
		getSupportActionBar().setElevation(0);		// 去除ActionBar的陰影

		amountText = findViewById(R.id.amount_text);
		amountText.setCharacterLists(TickerUtils.provideNumberList());		// 設置amountText使用數字切換效果

		dateText = findViewById(R.id.date_text);

		mViewPager = findViewById(R.id.view_pager);
		pagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
		pagerAdapter.notifyDataSetChanged();		// 根據PagerAdapter的更改自動更新
		mViewPager.setAdapter(pagerAdapter);
		mViewPager.addOnPageChangeListener(this);
		mViewPager.setCurrentItem(pagerAdapter.getLastIndex());		// 顯示最後面的Fragment

		findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 跳轉到AddRecordActivity
				Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
				startActivityForResult(intent, 1);		// 設置請求標記
			}
		});


	}

	@Override
	public void onPageScrolled(int i, float v, int i1) {

	}

	@Override
	public void onPageSelected(int i) {
		currentPagerPosition = i;
		updateHeader();
	}

	// 更新總金額
	public void updateHeader() {
		String amount = String.valueOf(pagerAdapter.getTotalCost(currentPagerPosition));
		amountText.setText(amount);
		String date = pagerAdapter.getDateStr(currentPagerPosition);
		dateText.setText(DateUtil.getWeekDay(date));
	}

	@Override
	public void onPageScrollStateChanged(int i) {

	}
}



