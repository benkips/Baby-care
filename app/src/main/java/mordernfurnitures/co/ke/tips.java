package mordernfurnitures.co.ke;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

import static android.content.ContentValues.TAG;

public class tips extends AppCompatActivity {
    private RecyclerView rvtip;
    private Mycommand mycommand;
    final String Tag=this.getClass().getName();
    private ProgressDialog pd;
    private  String yr;
    private  String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        rvtip=(RecyclerView)findViewById(R.id.rvtips);
        mycommand=new Mycommand(this);
        pd=new ProgressDialog(this);
        pd.setMessage("Loading");


        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            yr= bundle.getString("Age");
            category= bundle.getString("bf");

        }

        rvtip.setHasFixedSize(true);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rvtip.setLayoutManager(manager);
        loadtips();
    }
    private void loadtips(){
        String url="http://babycare.mordernfurnitures.co.ke/gettips.php";
        StringRequest strrq=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                Log.d(Tag,response);
                if(!response.isEmpty()){
                    if(response.contains("no tips entry")){
                        Toast.makeText(tips.this, response, Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(Tag, response);
                        final ArrayList<tipz> mgmtlist = new JsonConverter<tipz>().toArrayList(response,tipz.class);
                        tipsadapter adapter = new  tipsadapter( mgmtlist,tips.this);
                        rvtip.setAdapter(adapter);
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error!=null) {
                    Log.d(TAG, error.toString());
                    if (error instanceof TimeoutError) {
                        pd.dismiss();
                        AlertDialog.Builder alert=new AlertDialog.Builder(tips.this);
                        alert.setMessage("please check your internet connectivity");
                        alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(tips.this,childmenu.class));
                            }
                        });
                        alert.show();
                    } else if (error instanceof NoConnectionError) {
                        pd.dismiss();
                        AlertDialog.Builder alert=new AlertDialog.Builder(tips.this);
                        alert.setMessage("please check your internet connectivity");
                        alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(tips.this,childmenu.class));
                            }
                        });
                        alert.show();
                    } else if (error instanceof NetworkError) {
                        pd.dismiss();
                        AlertDialog.Builder alert=new AlertDialog.Builder(tips.this);
                        alert.setMessage("please check your internet connectivity");
                        alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(tips.this,childmenu.class));
                            }
                        });
                        alert.show();
                    } else if (error instanceof AuthFailureError) {
                        pd.dismiss();
                        Toast.makeText(tips.this, "error time out ", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ParseError) {
                        pd.dismiss();
                        Toast.makeText(tips.this, "error time out ", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        pd.dismiss();
                        Toast.makeText(tips.this, "error time out ", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ClientError) {
                        pd.dismiss();
                        Toast.makeText(tips.this, "error time out ", Toast.LENGTH_SHORT).show();
                    } else {
                        pd.dismiss();
                        Toast.makeText(tips.this, "error time out ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("category", category);
                params.put("year", yr);
                return params;
            }
        };
        mycommand.add(strrq);
        pd.show();
        mycommand.execute();
        mycommand.remove(strrq);
    }
    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(tips.this,"right-to-left");
    }
}
