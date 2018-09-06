package tbs.thinkbiz.solutions.iloveurhome;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button button;
    EditText editTextfnm,editTextpass,editTextconfpass,editTextmail,editTextusrname;

     String Actname;
     TextView textname;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.backbar);
        View view =getSupportActionBar().getCustomView();


        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences pref = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        Actname=pref.getString("Actvname","");
        textname=(TextView)findViewById(R.id.textname);
        textname.setText(Actname);

        editTextfnm=(EditText)findViewById(R.id.fname);
        editTextusrname=(EditText)findViewById(R.id.uname);
        editTextmail=(EditText)findViewById(R.id.editTextemail);
        editTextpass=(EditText)findViewById(R.id.passtext);
        editTextconfpass=(EditText)findViewById(R.id.passtext1);

        button=(Button)findViewById(R.id.button_regs);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValidate())
                {
                    Regst();
                }

            }
        });
    }

    private boolean isValidate()
    {
        final String email = editTextmail.getText().toString().trim();

        if (editTextfnm.getText().toString().length() == 0) {
            editTextfnm.setError("Name not entered");
            editTextfnm.requestFocus();
            return false;
        }

        if (editTextusrname.getText().toString().length() == 0) {
            editTextusrname.setError("Username not entered");
            editTextusrname.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            editTextmail.setError("Please enter your email");
            editTextmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextmail.setError("Enter a valid email");
            editTextmail.requestFocus();
            return false;
        }

        if (editTextpass.getText().toString().length() == 0) {
            editTextpass.setError("Password not entered");
            editTextpass.requestFocus();
            return false;
        }
        if (editTextconfpass.getText().toString().length() == 0) {
            editTextconfpass.setError("Please confirm password");
            editTextconfpass.requestFocus();
            return false;
        }
        if (!editTextpass.getText().toString().equals(editTextconfpass.getText().toString())) {
            editTextconfpass.setError("Password Not matched");
            editTextconfpass.requestFocus();
            return false;
        }

        if (editTextpass.getText().toString().length() < 6) {
            editTextpass.setError("Password should be atleast of 6 charactors");
            editTextpass.requestFocus();
            return false;
        }
        return true;
    }

    private void Regst(){
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        final String user_fname = editTextfnm.getText().toString().trim();
        final String user_name = editTextusrname.getText().toString().trim();
        final String user_email = editTextmail.getText().toString().trim();
        final String user_pass = editTextpass.getText().toString().trim();

        //Log.e("resp",Uroll);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,AllUrls.REG_URL ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("resp",response);

                        progressDialog.dismiss();

                        try {
                            JSONObject obj = new JSONObject(response);
                            String success=obj.getString("s");
                            String error=obj.getString("e");
                            String msg=obj.getString("m");

                            if(success.equalsIgnoreCase("1"))
                            {
                                Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                editTextfnm.setText("");
                                editTextusrname.setText("");
                                editTextmail.setText("");
                                editTextpass.setText("");
                                editTextconfpass.setText("");

                            }
                            else {
                                Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_LONG).show();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", user_fname);
                params.put("user_name", user_name);
                params.put("user_email", user_email);
                params.put("user_pass", user_pass);
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    }
