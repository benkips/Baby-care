package mordernfurnitures.co.ke;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import java.util.List;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

import static android.content.ContentValues.TAG;

public class chat extends AppCompatActivity {

    private RecyclerView recyclerV;
    private EditText msgg;
    private FloatingActionButton flsend;
    List<chatmodel> list_modell=new ArrayList<>();
    private SharedPreferences preff;
    private Mycommand mycommand;
    private String phone;
    private String vid;
    private ProgressDialog pd;
    final String Tag=this.getClass().getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerV=(RecyclerView)findViewById(R.id.rvchats);
        msgg=(EditText)findViewById(R.id.etchat);
        flsend=(FloatingActionButton)findViewById(R.id.flchats);
        mycommand=new Mycommand(chat.this);
        preff=getSharedPreferences("logininfo.conf",MODE_PRIVATE);
        phone=preff.getString("phone","");
        Log.d(Tag,phone);
        pd=new ProgressDialog(chat.this);
        pd.setMessage("Loading");

   /*     Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            vid=bundle.getString("vid");

        }*/
        loadchats();

        flsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String text=msgg.getText().toString();
                if(text.equals("")) {
                    msgg.requestFocus();
                    return;

                }else{
                    sendmessag(text);
                    msgg.setText("");

                }

            }
        });


    }
    private  void sendmessag(final String message) {
        String url = "http://babycare.mordernfurnitures.co.ke/chatsaver.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadchats();
                /* Toast.makeText(chat.this, response, Toast.LENGTH_SHORT).show();*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError) {

                    Toast.makeText(chat.this, "error time out ", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {

                    Toast.makeText(chat.this, "error no connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {

                    Toast.makeText(chat.this, "error network error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {

                    Toast.makeText(chat.this, "errorin Authentication", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {

                    Toast.makeText(chat.this, "error while parsing", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {

                    Toast.makeText(chat.this, "error  in server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ClientError) {

                    Toast.makeText(chat.this, "error with Client", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(chat.this, "error while loading", Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<>();
                params.put("sender",phone);
                params.put("mssg",message);
                return params;
            }
        };
        mycommand.add(stringRequest);
        mycommand.execute();
        mycommand.remove(stringRequest);
    }
    private void loadchats(){
        String url="http://babycare.mordernfurnitures.co.ke/getchats.php";
        StringRequest strrq=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                Log.d(Tag,response);

                if(!response.isEmpty()) {
                    if (response.equals("no chats")) {
                        Toast.makeText(chat.this, "No chats  currently", Toast.LENGTH_SHORT).show();
                    } else {
                        ArrayList<chatitems> navdetails = new JsonConverter<chatitems>().toArrayList(response, chatitems.class);
                        ArrayList<String> title = new ArrayList<String>();
                        list_modell.clear();
                        for (chatitems value : navdetails) {
                            if (value.sender.equals(phone)) {
                                title.add(value.message);
                                chatmodel chatmodel = new chatmodel(value.message, true);
                                list_modell.add(chatmodel);
                                recyclerV.setHasFixedSize(true);
                                chatAdapter chatAdapter = new chatAdapter(chat.this, list_modell);
                                recyclerV.setAdapter(chatAdapter);
                            } else {
                                chatmodel chatmodell = new chatmodel(value.message, false);

                                list_modell.add(chatmodell);
                                recyclerV.setHasFixedSize(true);
                                chatAdapter chatAdapter = new chatAdapter(chat.this, list_modell);
                                recyclerV.setAdapter(chatAdapter);
                            }
                            LinearLayoutManager manager = new LinearLayoutManager(chat.this);
                          /*  manager.setReverseLayout(true);*/
                            recyclerV.setLayoutManager(manager);
                            recyclerV.scrollToPosition(0);
                        }
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
                        AlertDialog.Builder alert=new AlertDialog.Builder(chat.this);
                        alert.setMessage("please check your internet connectivity");
                        alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(chat.this,home.class));
                            }
                        });
                        alert.show();
                    } else if (error instanceof NoConnectionError) {
                        pd.dismiss();
                        AlertDialog.Builder alert=new AlertDialog.Builder(chat.this);
                        alert.setMessage("please check your internet connectivity");
                        alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(chat.this,home.class));
                            }
                        });
                        alert.show();
                    } else if (error instanceof NetworkError) {
                        pd.dismiss();
                        AlertDialog.Builder alert=new AlertDialog.Builder(chat.this);
                        alert.setMessage("please check your internet connectivity");
                        alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(chat.this,home.class));
                            }
                        });
                        alert.show();
                    } else if (error instanceof AuthFailureError) {
                        pd.dismiss();
                        Toast.makeText(chat.this, "error time out ", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ParseError) {
                        pd.dismiss();
                        Toast.makeText(chat.this, "error time out ", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        pd.dismiss();
                        Toast.makeText(chat.this, "error time out ", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ClientError) {
                        pd.dismiss();
                        Toast.makeText(chat.this, "error time out ", Toast.LENGTH_SHORT).show();
                    } else {
                        pd.dismiss();
                        Toast.makeText(chat.this, "error time out ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("phone", phone);
                return params;
            }

            ;
        };
        mycommand.add(strrq);
        pd.show();
        mycommand.execute();
        mycommand.remove(strrq);
    }
    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(chat.this,"right-to-left");
    }
}
