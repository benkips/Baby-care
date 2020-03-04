package mordernfurnitures.co.ke;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

public class Register extends AppCompatActivity {
    private EditText email;
    private EditText phone;
    private EditText location;
    private Button registerbtn;
    private Button lgrefbtn;
    private ProgressDialog progressDialog;
    private Mycommand mycommand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email=(EditText)findViewById(R.id.emailreg);
        phone=(EditText)findViewById(R.id.phonereg);
        location=(EditText)findViewById(R.id.reglocation);
        lgrefbtn=(Button)findViewById(R.id.loginrefbtn);
        registerbtn =(Button)findViewById(R.id.regbtnreg);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("loading..");
        mycommand=new Mycommand(this);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loco=location.getText().toString().trim();
                String em=email.getText().toString().trim();
                String phon=phone.getText().toString().trim();
                userReg(em,phon,loco);
            }
        });
        lgrefbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,Login.class));
                CustomIntent.customType(Register.this,"right-to-left");
            }
        });



    }
    private void userReg(final String e,final String ph,final String loco){
        if(e.isEmpty()){
            email.setError("email is invalid");
            email.requestFocus();
            return;
        }else if(ph.isEmpty()){
            phone.setError("phone is invalid");
            phone.requestFocus();
            return;
        }else if(loco.isEmpty()) {
            location.setError("location is invalid");
            location.requestFocus();
            return;
        } else {
            if (!isphone(ph) || (ph.length() != 10 || !ph.startsWith("07"))) {
                phone.setError("phone is invalid");
                phone.requestFocus();
                return;
            }if(!isvalidemail(e)){
                email.setError("email is invalid");
                email.requestFocus();
                return;
            } else {
                String url = "http://babycare.mordernfurnitures.co.ke/registration.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if(!response.equals("")) {
                            if (response.equals("success")) {

                                Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        if (error instanceof TimeoutError) {
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "error time out ", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NoConnectionError) {
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "error no connection", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError) {
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "error network error", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof AuthFailureError) {
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "errorin Authentication", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "error while parsing", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "error  in server"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ClientError) {
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "error with Client", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "error while loading", Toast.LENGTH_SHORT).show();
                        }

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", e);
                        params.put("phone", ph);
                        params.put("location",loco);
                        return params;
                    }
                };
                mycommand.add(stringRequest);
                progressDialog.show();
                mycommand.execute();
                mycommand.remove(stringRequest);
            }

        }
    }
    public static boolean isphone(CharSequence target){
        return !TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches();
    }
    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(Register.this,"right-to-left");
    }
    private final boolean isvalidemail(String target){
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}
