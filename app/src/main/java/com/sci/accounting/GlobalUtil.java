package com.sci.accounting;

import android.content.Context;

import java.util.LinkedList;

public class GlobalUtil {

	private static GlobalUtil instance;

	public RecordDatabaseHelper databaseHelper;
	private Context context;
	public MainActivity mainActivity;

	public LinkedList<CategoryResBean> costRes = new LinkedList<>();
	public LinkedList<CategoryResBean> earnRes = new LinkedList<>();

	private static String[] costTitle = {"General", "Food", "Drink", "Groceries", "Shopping",
			"Personal", "Entertain", "App Store", "Mobile", "Computer", "Gifts",
			"Housing", "Travel", "Books", "Medical", "Transfer"};

	private static int[] costIconBlack =
			{R.drawable.icon_general, R.drawable.icon_food, R.drawable.icon_juice, R.drawable.icon_groceries,
			R.drawable.icon_shoppingcart, R.drawable.icon_user, R.drawable.icon_microphone, R.drawable.icon_store,
			R.drawable.icon_phone, R.drawable.icon_computer, R.drawable.icon_things, R.drawable.icon_house,
			R.drawable.icon_travel, R.drawable.icon_books, R.drawable.icon_medicine, R.drawable.icon_bus};

	private static String[] earnTitle = {"General", "Reimburse", "Salary", "RedPocket", "Part-time", "Bonus", "Investment"};

	private static int[] earnIconBlack =
			{R.drawable.icon_general1, R.drawable.icon_reimburse, R.drawable.icon_salary, R.drawable.icon_redbag,
					R.drawable.icon_parttime, R.drawable.icon_bonus, R.drawable.icon_investment,};
	public GlobalUtil () {

	}

	public static GlobalUtil getInstance() {
		if (instance == null) {
			instance = new GlobalUtil();
		}
		return instance;
	}

	public int getResourceIcon(String category) {
		for (CategoryResBean res: costRes) {
			if (res.title.equals(category)) {
				return res.resBlack;
			}
		}

		for (CategoryResBean res: earnRes) {
			if (res.title.equals(category)) {
				return res.resBlack;
			}
		}

		return costRes.get(0).resBlack;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {

		this.context = context;
		databaseHelper = new RecordDatabaseHelper(context, RecordDatabaseHelper.DB_NAME, null, 1);

		for (int i = 0; i < costTitle.length; i++) {
			CategoryResBean res = new CategoryResBean();
			res.title = costTitle[i];
			res.resBlack = costIconBlack[i];
			costRes.add(res);
		}

		for (int i = 0; i < earnTitle.length; i++) {
			CategoryResBean res = new CategoryResBean();
			res.title = earnTitle[i];
			res.resBlack = earnIconBlack[i];
			earnRes.add(res);
		}
	}
}
