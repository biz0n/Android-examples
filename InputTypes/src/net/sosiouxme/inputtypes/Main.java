package net.sosiouxme.inputtypes;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        LinearLayout ll = (LinearLayout) findViewById(R.id.container);
        Field[] fields = InputType.class.getDeclaredFields();
		LayoutParams lparams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		for (Field f : fields) {
			if (!f.getType().equals(int.class))
				continue;
			int mods = f.getModifiers();
			if ((mods & (Modifier.FINAL | Modifier.STATIC)) == 0)
				continue;
			
			TextView v = new TextView(this);
			EditText e = new EditText(this);
			v.setLayoutParams(lparams);
			e.setPadding(5, 5, 5, 15);
			e.setLayoutParams(lparams);
			
			try {
				e.setInputType(f.getInt(null));
				v.setText(f.getName());
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			ll.addView(v);
			ll.addView(e);
		}
    }
}