package net.sosiouxme.lifecycle;

import android.app.Activity;
import android.os.Bundle;

public class Test extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		setResult(RESULT_OK);
	}
}
