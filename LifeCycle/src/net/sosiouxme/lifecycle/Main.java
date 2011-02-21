package net.sosiouxme.lifecycle;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener {
    private static final String TAG = "LC";

	protected static final int DIALOG_ABOUT = 0;

	/** Called when the activity is first created. */
	
    Map<String,Toast> toaster = new HashMap<String,Toast>();
	
    private void loggit(String str) {
    	Log.d(TAG, str);
    	showToast(str);
    }
    private void showToast(String str) {
    	Toast t = (Toast) toaster.get(str);
    	if(t == null)
    		toaster.put(str, t = Toast.makeText(this, str, Toast.LENGTH_SHORT));
		t.show();
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
    	return new AlertDialog.Builder(this)
    	.setTitle("This is a dialog")
    	.setMessage("Here is a dialog for showing some text")
    	.setPositiveButton("OK", null)
    	.create();
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // wire up the buttons to do stuff
        findViewById(R.id.show_dialog).setOnClickListener(this);
        findViewById(R.id.show_transparent).setOnClickListener(this);
        findViewById(R.id.show_activity).setOnClickListener(this);
        findViewById(R.id.show_result).setOnClickListener(this);
        findViewById(R.id.restart).setOnClickListener(this);
        findViewById(R.id.finish).setOnClickListener(this);
        
        loggit("onCreate");
    }
    
    @Override
    protected void onRestart() {
    	super.onRestart();
    	loggit("onRestart");
    }

    @Override
    protected void onStart() {
    	super.onStart();
        loggit("onStart");
    }

    @Override
    protected void onResume() {
    	super.onResume();
        loggit("onResume");
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	outState.putBoolean("foo", true);
    	super.onSaveInstanceState(outState);
        loggit("onSaveInstanceState");
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	loggit("onRestoreInstanceState");
    }

	@Override
	protected void onPause() {
		super.onPause();
        loggit("onPause");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
        loggit("onStop");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
        loggit("onDestroy");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
        loggit("onActivityResult");
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.show_dialog:
			showDialog(DIALOG_ABOUT);
			break;
		case R.id.show_activity:
			startActivity(new Intent(Main.this, Test.class));
			break;
		case R.id.show_transparent:
			startActivity(new Intent(Main.this, TestTranslucent.class));
			break;
		case R.id.show_result:
			startActivityForResult(new Intent(Main.this, Test.class), 0);
			break;	
		case R.id.restart:
			Intent intent = getIntent();
			finish();
			startActivity(intent);
			break;
		case R.id.finish:
			finish();
			break;
		}
	}
}