package mordernfurnitures.co.ke;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

import static android.content.ContentValues.TAG;

public class calendar extends AppCompatActivity {
    private EditText endate;
    private Button addbtn;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    private Mycommand mycommand;
    final String Tag=this.getClass().getName();
    private ProgressDialog pd;
    private RecyclerView  rvdates;
    private SharedPreferences preferences;
    private String phones;
    private String emaliz;
    private String Age;
    private String e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        preferences=getSharedPreferences("logininfo.conf",MODE_PRIVATE);
        phones=preferences.getString("phone","");
        emaliz =preferences.getString("emailz","");


        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            Age = bundle.getString("Age");


        }




        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        endate=(EditText)findViewById(R.id.etev);
        addbtn=(Button)findViewById(R.id.eventbtn);

        rvdates=(RecyclerView)findViewById(R.id.rvev);
        mycommand=new Mycommand(this);
        pd=new ProgressDialog(this);
        pd.setMessage("Loading");

        rvdates.setHasFixedSize(true);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rvdates.setLayoutManager(manager);



        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e=endate.getText().toString().trim();
                if(e.equals("")){
                    endate.setError("event can not be empty");
                    endate.requestFocus();
                    return;
                }else{
                    setDate();

                }
            }
        });
        loadates();



    }
    @SuppressWarnings("deprecation")
    public void setDate() {
        showDialog(999);

    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    String datess=String.valueOf(arg3)+"."+"0"+ String.valueOf(arg2+1)+"."+String.valueOf(arg1);

                    sendates(datess);
                }
            };

   /* private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }*/
   private void loadates(){
       String url="http://babycare.mordernfurnitures.co.ke/getclinicdates.php";
       StringRequest strrq=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               pd.dismiss();
               Log.d(Tag,response);
               if(!response.isEmpty()){
                   if(response.contains("no dates")){
                       Toast.makeText(calendar.this, response, Toast.LENGTH_SHORT).show();
                   } else {
                       Log.d(Tag, response);
                       final ArrayList<datz> mgmtlist = new JsonConverter<datz>().toArrayList(response,datz.class);
                        dateadapter adapter = new  dateadapter( mgmtlist,calendar.this);
                       rvdates.setAdapter(adapter);
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
                       AlertDialog.Builder alert=new AlertDialog.Builder(calendar.this);
                       alert.setMessage("please check your internet connectivity");
                       alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               startActivity(new Intent(calendar.this,childmenu.class));
                           }
                       });
                       alert.show();
                   } else if (error instanceof NoConnectionError) {
                       pd.dismiss();
                       AlertDialog.Builder alert=new AlertDialog.Builder(calendar.this);
                       alert.setMessage("please check your internet connectivity");
                       alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               startActivity(new Intent(calendar.this,childmenu.class));
                           }
                       });
                       alert.show();
                   } else if (error instanceof NetworkError) {
                       pd.dismiss();
                       AlertDialog.Builder alert=new AlertDialog.Builder(calendar.this);
                       alert.setMessage("please check your internet connectivity");
                       alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               startActivity(new Intent(calendar.this,childmenu.class));
                           }
                       });
                       alert.show();
                   } else if (error instanceof AuthFailureError) {
                       pd.dismiss();
                       Toast.makeText(calendar.this, "error time out ", Toast.LENGTH_SHORT).show();
                   } else if (error instanceof ParseError) {
                       pd.dismiss();
                       Toast.makeText(calendar.this, "error time out ", Toast.LENGTH_SHORT).show();
                   } else if (error instanceof ServerError) {
                       pd.dismiss();
                       Toast.makeText(calendar.this, "error time out ", Toast.LENGTH_SHORT).show();
                   } else if (error instanceof ClientError) {
                       pd.dismiss();
                       Toast.makeText(calendar.this, "error time out ", Toast.LENGTH_SHORT).show();
                   } else {
                       pd.dismiss();
                       Toast.makeText(calendar.this, "error time out ", Toast.LENGTH_SHORT).show();
                   }
               }
           }
       }){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> params = new HashMap<>();
               params.put("year",Age);
               params.put("email",emaliz);

               return params;
           }
       };
       mycommand.add(strrq);
       pd.show();
       mycommand.execute();
       mycommand.remove(strrq);
   }
    private void sendates(final String d){
        String url="http://babycare.mordernfurnitures.co.ke/savedates.php";
        StringRequest strrq=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                Log.d(Tag,response);
                if(!response.isEmpty()){
                    if(!response.contains("success")){
                        Toast.makeText(calendar.this, response, Toast.LENGTH_SHORT).show();
                    } else {
                        loadates();
                        endate.setText("");
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
                        AlertDialog.Builder alert=new AlertDialog.Builder(calendar.this);
                        alert.setMessage("please check your internet connectivity");
                        alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(calendar.this,childmenu.class));
                            }
                        });
                        alert.show();
                    } else if (error instanceof NoConnectionError) {
                        pd.dismiss();
                        AlertDialog.Builder alert=new AlertDialog.Builder(calendar.this);
                        alert.setMessage("please check your internet connectivity");
                        alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(calendar.this,childmenu.class));
                            }
                        });
                        alert.show();
                    } else if (error instanceof NetworkError) {
                        pd.dismiss();
                        AlertDialog.Builder alert=new AlertDialog.Builder(calendar.this);
                        alert.setMessage("please check your internet connectivity");
                        alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(calendar.this,childmenu.class));
                            }
                        });
                        alert.show();
                    } else if (error instanceof AuthFailureError) {
                        pd.dismiss();
                        Toast.makeText(calendar.this, "error time out ", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ParseError) {
                        pd.dismiss();
                        Toast.makeText(calendar.this, "error time out ", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        pd.dismiss();
                        Toast.makeText(calendar.this, "error time out ", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ClientError) {
                        pd.dismiss();
                        Toast.makeText(calendar.this, "error time out ", Toast.LENGTH_SHORT).show();
                    } else {
                        pd.dismiss();
                        Toast.makeText(calendar.this, "error time out ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("year",d);
                params.put("childyear",Age);
                params.put("email",emaliz);
                params.put("event",e);

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
        CustomIntent.customType(calendar.this,"right-to-left");
    }
}
