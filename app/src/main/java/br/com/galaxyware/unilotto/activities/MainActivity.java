package br.com.galaxyware.unilotto.activities;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.galaxyware.unilotto.R;
import br.com.galaxyware.unilotto.utils.InputFilterMinMax;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private final int MIN = 1;
    private  final int MAX = 50;

    private List<EditText> inputNumbers =  new ArrayList<>();
    private List<TextView> resultNumbers =  new ArrayList<>();
    private Integer rights = 0;

    @BindView(R.id.button) Button button;
    @BindView(R.id.containerResult) LinearLayout containerResult;
    @BindView(R.id.resultText) TextView resultText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getViews();
    }

    protected void getViews(){
        for (int i = 1; i <= 5; i++) {
            int idInput = getResources().getIdentifier("editText"+i, "id", getPackageName());
            EditText et = findViewById(idInput);
            et.setFilters(new InputFilter[]{ new InputFilterMinMax(MIN, MAX)});
            inputNumbers.add(et);

            int idText = getResources().getIdentifier("resultText"+i, "id", getPackageName());
            TextView textView = findViewById(idText);
            resultNumbers.add(textView);
        }
    }

    public void sendNumber(View view){
        closeKeyBoard();

        List<Integer> numbers = new ArrayList<>();
        int i = 0;

        for(EditText editText : inputNumbers){
            if (verifyNullNumber(editText)) return;
            setNumbersResult(numbers, i, Integer.parseInt(editText.getText().toString()));
            i++;
            editText.clearFocus();
        }

        resultText.setText(String.valueOf(rights));
        containerResult.setVisibility(View.VISIBLE);

        //changeButtonToReset();
    }

    private void changeButtonToReset() {
        button.setText(R.string.button_reset);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetData();
            }
        });
    }

    private void changeButtonToRaffle() {
        button.setText(R.string.button_action);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNumber(v);
            }
        });
    }


    private void closeKeyBoard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private boolean verifyNullNumber(EditText editText) {
        if(editText.getText().toString().equals("")){
            Toast.makeText(this, "Digite valores entre 1 e 50", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private void setNumbersResult(List<Integer> numbers, int i, int parseInt) {
        int randomNumber;
        do{
            randomNumber = new Random().nextInt((MAX - MIN) + 1) + MIN;
        }while (verifyRepeatNumber(numbers,randomNumber));

        numbers.add(randomNumber);

        resultNumbers.get(i).setText(String.valueOf(randomNumber));

        for(Integer num : numbers){
            if(num.equals(parseInt)) rights++;
        }
    }

    private boolean verifyRepeatNumber(List<Integer> numbers , Integer number){
        for(Integer i : numbers){ if(i.equals(number)) return true; }
        return false;
    }

    public void resetData(){
        rights = 0 ;
        for(TextView tv : resultNumbers) tv.setText("");
        for(EditText editText : inputNumbers) editText.setText("");
        containerResult.setVisibility(View.INVISIBLE);

        changeButtonToRaffle();
    }


}
