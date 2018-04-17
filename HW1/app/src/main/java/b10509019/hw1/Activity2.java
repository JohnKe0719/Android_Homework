package b10509019.hw1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Intent intent = getIntent();
        String text =intent.getStringExtra(MainActivity.EXTRA_TEXT);
        TextView textView1 =(TextView) findViewById(R.id.text_view1);
        textView1.setText("B10509019："+text);

    }
}
