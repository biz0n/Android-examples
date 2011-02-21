package com.kagii.clickcounter.data;

import java.sql.SQLException;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;

import com.j256.ormlite.android.AndroidCompiledStatement;
import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.stmt.PreparedQuery;

public class SimpleQueryAdapter<T> extends ResourceCursorAdapter {

	protected PreparedQuery<T> mQuery;
	protected ListItemBinder<T> mVb;

	public SimpleQueryAdapter(Activity caller, int layout, PreparedQuery<T> q, OrmLiteSqliteOpenHelper dbh, ListItemBinder<T> vb) throws SQLException {
		super(caller, layout, ((AndroidCompiledStatement) q.compile(dbh.getConnectionSource().getReadOnlyConnection())).getCursor());
		mQuery = q;
		caller.startManagingCursor(getCursor());
		mVb = vb;
	}

	@Override
	public void bindView(View listItem, Context context, Cursor cursor) {
		try {
			T dto = mQuery.mapRow(new AndroidDatabaseResults(cursor));
			mVb.setItemContent(listItem, dto);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public interface ListItemBinder<T> {
        void setItemContent(View listItem, T dto);
	}
}
