package tbs.thinkbiz.solutions.iloveurhome;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.function.BooleanSupplier;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLogin;
    private TextView reg;

    EditText editTextpass, editTextuname;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (isOnline()) {
            //do whatever you want to do
        } else {
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();

                alertDialog.setTitle("Info");
                alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setIcon(R.drawable.ic_warning_black_24dp);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
                        startActivity(new Intent(LoginActivity.this,LoginActivity.class));

                    }
                });

                alertDialog.show();
            } catch (Exception e) {
                //Log.d(SyncStateContract.Constants.TAG, "Show Dialog: " + e.getMessage());
            }
        }


        editTextuname = (EditText) findViewById(R.id.editTextemail);
        editTextpass = (EditText) findViewById(R.id.passtext);
        buttonLogin=(Button)findViewById(R.id.button_regs);


        reg = (TextView) findViewById(R.id.textViewRgs);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String actname="Register Here";
                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();

                edit.putString("Actvname",actname);
                edit.apply();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
//                if(isValidate())
//                {
//                    Loginbtn();
//                }
            }
        });
    }

    private boolean isValidate()
    {

        if (editTextuname.getText().toString().length() == 0) {
            editTextuname.setError("Username not entered");
            editTextuname.requestFocus();
            return false;
        }

        if (editTextpass.getText().toString().length() == 0) {
            editTextpass.setError("Password not entered");
            editTextpass.requestFocus();
            return false;
        }
        if (editTextpass.getText().toString().length() < 6) {
            editTextpass.setError("Password should be atleast of 6 charactors");
            editTextpass.requestFocus();
            return false;
        }
        return true;
    }

    private void Loginbtn() {

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Signing In...");
        progressDialog.show();

        final String username = editTextuname.getText().toString().trim();
        final String password = editTextpass.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrls.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("resp", response);
                        progressDialog.dismiss();

                        try {
                           JSONObject obj = new JSONObject(response);
                            String success=obj.getString("s");
                            String error=obj.getString("e");
                            String msg=obj.getString("m");
                            if(success.equalsIgnoreCase("1")){

                                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = pref.edit();
                            edit.putString("Username", username);

                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                                editTextuname.setText("");
                                editTextpass.setText("");
                            edit.apply();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);


                        }else {
                                Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
                        }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }) {
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_name", username);
                params.put("user_pass", password);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(stringRequest);
    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
           // Toast.makeText(this, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
