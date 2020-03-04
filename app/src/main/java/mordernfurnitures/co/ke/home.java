package mordernfurnitures.co.ke;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import maes.tech.intentanim.CustomIntent;

public class home extends AppCompatActivity {
    private CardView cardmum;
    private CardView cardnanny;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cardmum=(CardView)findViewById(R.id.mummybtn);
        cardnanny=(CardView)findViewById(R.id.househelpbtn);

        preferences=getSharedPreferences("logininfo.conf",MODE_PRIVATE);


        cardmum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                String mum="mum";
                bundle.putString("person", mum);
                Intent intent=new Intent(home.this,childage.class);
                intent.putExtras(bundle);
                startActivity(intent);
                CustomIntent.customType(home.this,"left-to-right");
            }
        });
        cardnanny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                String househelp="househelp";
                bundle.putString("person", househelp);
                Intent intent=new Intent(home.this,childage.class);
                intent.putExtras(bundle);
                startActivity(intent);
                CustomIntent.customType(home.this,"left-to-right");
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menul, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout) {
            editor = preferences.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(home.this, Login.class));
            home.this.finish();
            return true;
        }if(id==R.id.pforum){
            startActivity(new Intent(home.this,chat.class));
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(home.this,"left-to-right");
    }
}
