package com.kagii.clickcounter;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.kagii.clickcounter.data.ClickCount;
import com.kagii.clickcounter.data.DatabaseHelper;
import com.kagii.clickcounter.data.SimpleQueryAdapter;

/**
 * Lists the counters and allows you to select one for usage or create a new one.
 * 
 * @author kevingalligan
 */
public class ClickConfig extends OrmLiteBaseActivity<DatabaseHelper> {

	private final DateFormat df = new SimpleDateFormat("M/dd/yy HH:mm");

	private ListView listView;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		findViewById(R.id.createCounter).setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				CreateCounter.callMe(ClickConfig.this);
			}
		});

		listView = (ListView) findViewById(R.id.clickList);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				ClickCount count = (ClickCount) listView.getAdapter().getItem(i);
				CounterScreen.callMe(ClickConfig.this, count.getId());
			}
		});

		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
				ClickCount count = (ClickCount) listView.getAdapter().getItem(i);
				CreateCounter.callMe(ClickConfig.this, count.getId());
				return true;
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			fillList();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void fillList() throws SQLException {
		Log.i(ClickConfig.class.getName(), "Show list again");
		Dao<ClickCount, Integer> dao = getHelper().getClickDao();
		QueryBuilder<ClickCount, Integer> builder = dao.queryBuilder();
		builder.orderBy(ClickCount.DATE_FIELD_NAME, false).limit(30);
		SimpleQueryAdapter<ClickCount> queryAdapter = new SimpleQueryAdapter<ClickCount>(this, R.layout.count_row, builder.prepare(), getHelper(), new CountsViewer());
		listView.setAdapter(queryAdapter);
	}

	private class CountsViewer implements SimpleQueryAdapter.ListItemBinder<ClickCount> {

		@Override
		public void setItemContent(View v, ClickCount count) {

			try {
				if (count.getGroup().getId() != null) {
					getHelper().getGroupDao().refresh(count.getGroup());
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			fillText(v, R.id.clickGroup, count.getGroup().getName());
			fillText(v, R.id.clickName, count.getName());
			if (count.getLastClickDate() == null) {
				fillText(v, R.id.clickDate, "");
			} else {
				fillText(v, R.id.clickDate, df.format(count.getLastClickDate()));
			}
			fillText(v, R.id.clickCount, Integer.toString(count.getValue()));
		}

		private void fillText(View v, int id, String text) {
			TextView textView = (TextView) v.findViewById(id);
			textView.setText(text == null ? "" : text);
		}

	}
}
