package b10509019.hw1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT="b10509019.hw1.EXTRA_TEXT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1=(Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivatity2();
            }
        });
    }
    public void openActivatity2(){
        EditText editText1=(EditText) findViewById(R.id.edit_text1);
        String text = editText1.getText().toString();
        Intent intent = new Intent(this,Activity2.class);
        intent.putExtra(EXTRA_TEXT,text);
        startActivity(intent);
    }
}
