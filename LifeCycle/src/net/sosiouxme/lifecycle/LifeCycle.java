package net.sosiouxme.lifecycle;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

public class LifeCycle extends Application {
	private static final String TAG = "LC";

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "App.onCreate");
		Toast.makeText(this, "App.onCreate", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		Log.d(TAG, "App.onTerminate");
		Toast.makeText(this, "App.onTerminate", Toast.LENGTH_SHORT).show();
	}
}
