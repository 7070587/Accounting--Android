package com.sci.accounting;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.LinkedList;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

	LinkedList<MainFragment> fragments = new LinkedList<>();
	LinkedList<String> dates = new LinkedList<>();

	public MainViewPagerAdapter(FragmentManager fm) {
		super(fm);
		initFragments();
	}

	private void initFragments() {

//		//dates.add("2018-10-07");

		dates = GlobalUtil.getInstance().databaseHelper.getAvailableDate();

		if (!dates.contains(DateUtil.getFormattedDate())) {
			dates.addLast(DateUtil.getFormattedDate());
		}

		for (String date : dates) {
			MainFragment fragment = new MainFragment(date);
			fragments.add(fragment);
		}

	}

	public void reload() {
//		fragments.clear();
//		dates.clear();
//
//		dates = GlobalUtil.getInstance().databaseHelper.getAvailableDate();
//
//		for (String date : dates) {
//			MainFragment fragment = new MainFragment(date);
//			fragments.add(fragment);
//		}

		for (MainFragment fragment : fragments) {
			fragment.reload();
		}
	}

	public int getLastIndex() {
		return fragments.size() - 1;
	}

	@Override
	public Fragment getItem(int i) {
		return fragments.get(i);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	public String getDateStr(int index) {
		return dates.get(index);
	}


	public int getTotalCost(int index) {
		int amount = fragments.get(index).getTotalCost();
		return amount;
	}
}
