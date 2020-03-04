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
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

public class Login extends AppCompatActivity {
    private EditText email;
    private EditText phone;
    private Button  lgbtn;
    private Button rgbtnref;
    private CheckBox cb;
    private ProgressDialog progressDialog;
    private Mycommand mycommand;
    private Boolean checked;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=(EditText)findViewById(R.id.emaillogin);
        phone=(EditText)findViewById(R.id.phonelogin);
        lgbtn=(Button)findViewById(R.id.loginbtnlog);
        rgbtnref=(Button)findViewById(R.id.regrefbtn);
        cb=(CheckBox)findViewById(R.id.cbL);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("loading..");
        mycommand=new Mycommand(this);

        preferences=getSharedPreferences("logininfo.conf",MODE_PRIVATE);
        String phones=preferences.getString("phone","");
        String emaliz =preferences.getString("emailz","");

        if(!phones.equals("") && !emaliz.equals("")){
            /*startActivity here*/

            startActivity(new Intent(Login.this,home.class));
            Login.this.finish();
        }
        checked=cb.isChecked();
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                checked=b;

            }
        });
        rgbtnref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   startActivity(new Intent(Login.this,Register.class));
                CustomIntent.customType(Login.this,"right-to-left");
            }
        });
        lgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailtxt=email.getText().toString().trim();
                String phonetxt=phone.getText().toString().trim();

                userlogin(emailtxt,phonetxt);

            }
        });




    }
    private void userlogin(final String e,final String ph){
        if(e.isEmpty()){
            email.setError("email is invalid");
            email.requestFocus();
            return;
        }else if(ph.isEmpty()){
            phone.setError("phone is invalid");
            phone.requestFocus();
            return;
        }else {
            if (!isphone(ph) || (ph.length() != 10 || !ph.startsWith("07"))) {
                phone.setError("phone is invalid");
                phone.requestFocus();
                return;
            }if(!isvalidemail(e)){
                email.setError("email is invalid");
                email.requestFocus();
                return;
            } else {
                String url = "http://babycare.mordernfurnitures.co.ke/login.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if(!response.equals("")) {
                            if (response.equals("success")) {
                                if (checked) {
                                    preferences = getSharedPreferences("logininfo.conf", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("phone", ph);
                                    editor.putString("emailz", e);
                                    editor.apply();
                                }
                                Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, home.class));
                                Login.this.finish();
                                CustomIntent.customType(Login.this, "left-to-right");
                            } else {
                                Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        if (error instanceof TimeoutError) {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "error time out ", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NoConnectionError) {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "error no connection", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError) {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "error network error", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof AuthFailureError) {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "errorin Authentication", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "error while parsing", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "error  in server"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ClientError) {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "error with Client", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "error while loading", Toast.LENGTH_SHORT).show();
                        }

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", e);
                        params.put("phone", ph);
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
        CustomIntent.customType(Login.this,"right-to-left");
    }
    private final boolean isvalidemail(String target){
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
