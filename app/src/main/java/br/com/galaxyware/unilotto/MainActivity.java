package br.com.galaxyware.unilotto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.widget.EditText;

import br.com.galaxyware.unilotto.utils.InputFilterMinMax;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFilterMask();
    }

    protected void setFilterMask(){
        for (int i = 1; i < 5; i++) {
            int resID = getResources().getIdentifier("editText"+i, "id", getPackageName());
            EditText et = (EditText) findViewById(resID);
            et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "50")});
        }

    }
}
