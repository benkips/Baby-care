package mordernfurnitures.co.ke;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import maes.tech.intentanim.CustomIntent;

public class childage extends AppCompatActivity {
    private ListView index_menulv;
    private String person;
    final String Tag=this.getClass().getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_childage);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            person = bundle.getString("person");
            Log.d(Tag, person);

        }


        index_menulv=(ListView)findViewById(R.id.lvindex);
        String [] titlee=getResources().getStringArray(R.array.indexmenuone);
        SimpleAdapter adapter=new SimpleAdapter(childage.this,titlee);
        index_menulv.setAdapter(adapter);
        index_menulv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                Intent intent=new Intent(childage.this,childmenu.class);
                switch(position){

                    case 0:{
                        bundle.putString("person", person);
                        String age="1";
                        bundle.putString("age", age);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    }
                    case  1:{
                        bundle.putString("person", person);
                        String age="3";
                        bundle.putString("age", age);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    }
                    case 2:{
                        bundle.putString("person", person);
                        String age="4";
                        bundle.putString("age", age);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    }
                    case 3:{
                        bundle.putString("person", person);
                        String age="5";
                        bundle.putString("age", age);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    }

                }
            }
        });










    }

    public class SimpleAdapter extends BaseAdapter {
        private Context mcontext;
        private LayoutInflater layoutInflater;
        private TextView title,description;
        private String [] titleArray;
        private ImageView imv;

        public SimpleAdapter(Context context,String [] title ){
            mcontext=context;
            titleArray=title;
            layoutInflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return titleArray.length;

        }

        @Override
        public Object getItem(int i) {
            return titleArray[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view==null){
                view=layoutInflater.inflate(R.layout.childinf,null);

            }
            title=(TextView)view.findViewById(R.id.tvchild);
            imv=(ImageView)view.findViewById(R.id.ivchild);
            title.setText(titleArray[i]);
            if(titleArray[i].equalsIgnoreCase("1month-1 year old infant")){
                imv.setImageResource(R.drawable.onemonth);
            }else if(titleArray[i].equalsIgnoreCase("1year-2 year old child")){
                imv.setImageResource(R.drawable.oneyear);
            }else if(titleArray[i].equalsIgnoreCase("3year-4 year old child")){
                imv.setImageResource(R.drawable.threeyear);
            }else{
                imv.setImageResource(R.drawable.fiveyear);
            }
            return view;

        }
    }
    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(childage.this,"left-to-right");
    }
}
