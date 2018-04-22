package b10509019.hw2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toast mToast;
    int array[]=new int[100];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Item> myDataset = new ArrayList<>();
        for(int i=0;i<=99;i++){
            array[i]=0;
        }
        for(int i = 1; i <= 100; i++){
            Item item = new Item();

            item.setCheck(false);
            item.setText(i+"");
            myDataset.add(item);
        }
        MyAdapter myAdapter = new MyAdapter(myDataset);
        RecyclerView mList = (RecyclerView) findViewById(R.id.list_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(myAdapter);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<Item> mData;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public CheckBox mCheckBox;
            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.info_text);
                mCheckBox = (CheckBox) v.findViewById(R.id.info_chcekbox);
            }
        }

        public MyAdapter(List<Item> data) {
            mData = data;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            Item item = mData.get(position);
            holder.mTextView.setText(item.getText());
            holder.mCheckBox.setChecked(item.isCheck());
            holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean b = ((CheckBox) view).isChecked();
                    holder.mCheckBox.setChecked(b);
                    mData.get(position).setCheck(b);
                    if(array[position]==1){
                        array[position]=0;
                    }else{
                        array[position]=1;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

    }
    private static class Item{
        String text;
        boolean check;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            /*
             * When you click the reset menu item, we want to start all over
             * and display the pretty gradient again. There are a few similar
             * ways of doing this, with this one being the simplest of those
             * ways. (in our humble opinion)
             */
            case R.id.action_select:
                // COMPLETED (14) Pass in this as the ListItemClickListener to the GreenAdapter constructor
                //mAdapter = new GreenAdapter(NUM_LIST_ITEMS, this);
                //mNumbersList.setAdapter(mAdapter);
                String message="You select ";
                for(int i= 0;i<=99;i++){
                    if(array[i]==1){
                        message = message + String.valueOf(i+1)+",";
                    }
                }
                mToast = Toast.makeText(this,message,Toast.LENGTH_SHORT);
                mToast.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
