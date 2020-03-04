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

public class childmenu extends AppCompatActivity {
    private ListView index_menulv;
    private String person;
    private String Age;
    final String Tag=this.getClass().getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_childmenu);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            person = bundle.getString("person");
            Age = bundle.getString("age");
            Log.d(Tag, person);

        }
        index_menulv = (ListView) findViewById(R.id.lvindextwo);
        if(person.equals("mum")) {
            String[] titlee = getResources().getStringArray(R.array.mumumenu);
            String [] Description=getResources().getStringArray(R.array.mumdescription);
            SimpleAdaptertwo adapter=new SimpleAdaptertwo(this,titlee,Description);
            index_menulv.setAdapter(adapter);
            index_menulv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle=new Bundle();

                    switch(position){
                        case 0:{
                            String bf="breastfeeding";
                            bundle.putString("bf", bf);
                            bundle.putString("Age", Age);
                            Intent intent=new Intent(childmenu.this,tips.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            break;
                        }
                        case  1:{
                            String bf="feeding";
                            bundle.putString("bf", bf);
                            bundle.putString("Age", Age);
                            Intent intent=new Intent(childmenu.this,tips.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            break;

                        }
                        case 2:{
                            bundle.putString("Age", Age);
                            Intent intent=new Intent(childmenu.this,calendar.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            break;
                        }

                    }
                }
            });

        }else{
            String[] titlee = getResources().getStringArray(R.array.househelpmenu);
            String [] Description=getResources().getStringArray(R.array.househelpdescription);
            SimpleAdaptertwo adapter=new SimpleAdaptertwo(this,titlee,Description);
            index_menulv.setAdapter(adapter);
            index_menulv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle=new Bundle();
                    switch(position){
                        case 0:{
                            String bf="feeding";
                            bundle.putString("bf", bf);
                            Intent intent=new Intent(childmenu.this,tips.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            break;
                        }
                        case  1:{
                            bundle.putString("Age", Age);
                            Intent intent=new Intent(childmenu.this,calendar.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            break;
                        }


                    }
                }
            });

        }

    }

    public class SimpleAdaptertwo extends BaseAdapter {
        private Context mcontext;
        private LayoutInflater layoutInflater;
        private TextView title,description;
        private String [] titleArray;
        private String [] Description;
        private ImageView imv;

        public SimpleAdaptertwo(Context context,String [] title,String [] description ){
            mcontext=context;
            titleArray=title;
            Description=description;
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
                view=layoutInflater.inflate(R.layout.chidminf,null);

            }
            title=(TextView)view.findViewById(R.id.tvcategory);
            description=(TextView)view.findViewById(R.id.tvdesc);
            imv=(ImageView)view.findViewById(R.id.ivcm);


            title.setText(titleArray[i]);
            description.setText(Description[i]);

            if(titleArray[i].equalsIgnoreCase("Breastfeeding")){
                imv.setImageResource(R.drawable.breastfeed);
            }else if(titleArray[i].equalsIgnoreCase("Infant feeding")){
                imv.setImageResource(R.drawable.infant);
            }else if(titleArray[i].equalsIgnoreCase("Clinic Dates")){
                imv.setImageResource(R.drawable.calendarr);
            }else{
                imv.setImageResource(R.drawable.fiveyear);
            }
            return view;

        }
    }
    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(childmenu.this,"left-to-right");
    }
}
