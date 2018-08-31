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
    EditText editTextfnm,editTextlnm,editTextpass,editTextconfpass,editTextmail;
     RadioGroup radioGroup;
     RadioButton radioButton;

     String Actname;
     TextView textname;

    CheckBox checkBoxPrvy,checkBoxTnc;
    TextView textViewPrvcy ,tctext;
    ProgressDialog progressDialog;
    private static String REG_URL="https://www.itshades.com/appwebservices/register.php";
    String Uroll;
    int success;
    int error;
    String msg;

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
//
//
//        checkBoxPrvy=(CheckBox)findViewById(R.id.checkBoxP);
//        checkBoxTnc=(CheckBox)findViewById(R.id.checkBoxTc);
//
//
//
//        editTextfnm=(EditText)findViewById(R.id.editTextfnm);
//        editTextlnm=(EditText)findViewById(R.id.editTextlnm);
//        editTextmail=(EditText)findViewById(R.id.editTextemail);
//        editTextpass=(EditText)findViewById(R.id.editTextps);
//        editTextconfpass=(EditText)findViewById(R.id.editTextps1);
//        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
//        radioButton = (RadioButton) findViewById(R.id.rb1);
//
//        button=(Button)findViewById(R.id.button_regs);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String email = editTextmail.getText().toString().trim();
//
//
//                if (editTextfnm.getText().toString().length() == 0) {
//                    editTextfnm.setError("First name not entered");
//                    editTextfnm.requestFocus();
//                }
//                if (editTextlnm.getText().toString().length() == 0) {
//                    editTextlnm.setError("Last name not entered");
//                    editTextlnm.requestFocus();
//                }
//
//                if (TextUtils.isEmpty(email)) {
//                    editTextmail.setError("Please enter your email");
//                    editTextmail.requestFocus();
//                }
//
//                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                    editTextmail.setError("Enter a valid email");
//                    editTextmail.requestFocus();
//                }
//
//                if (editTextpass.getText().toString().length() == 0) {
//                    editTextpass.setError("Password not entered");
//                    editTextpass.requestFocus();
//                }
//                if (editTextconfpass.getText().toString().length() == 0) {
//                    editTextconfpass.setError("Please confirm password");
//                    editTextconfpass.requestFocus();
//                }
//                if (!editTextpass.getText().toString().equals(editTextconfpass.getText().toString())) {
//                    editTextconfpass.setError("Password Not matched");
//                    editTextconfpass.requestFocus();
//                }
//                if (editTextpass.getText().toString().length() < 6) {
//                    editTextpass.setError("Password should be atleast of 6 charactors");
//                    editTextpass.requestFocus();
//
//                }
//                if (radioGroup.getCheckedRadioButtonId()==-1) {
//                    radioButton.setError("Please select one option");
//                    radioGroup.requestFocus();
//
//                }
//
//
//                if (!checkBoxPrvy.isChecked()) {
//                    checkBoxPrvy.setError("Please Checked Privacy Policy");
//                    checkBoxPrvy.requestFocus();
//                }
//                if (!checkBoxTnc.isChecked()) {
//                    checkBoxTnc.setError("Please Checked Terms and conditions");
//                    checkBoxTnc.requestFocus();
//
//                }
//                else {
//                   Regst();
//                }
//
//            }
//        });
    }

//    private void Regst(){
//        progressDialog = new ProgressDialog(RegisterActivity.this);
//        progressDialog.setMessage("Signing Up...");
//        progressDialog.show();
//
//        final String fname = editTextfnm.getText().toString().trim();
//        final String lname = editTextlnm.getText().toString().trim();
//        final String email = editTextmail.getText().toString().trim();
//        final String password = editTextpass.getText().toString().trim();
//        final String usertype = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
//
//        if (usertype.equals("Individual User"))
//        {
//             Uroll="1";
//        }
//        else if (usertype.equals("Corporate Customer")){
//             Uroll="2";
//        }
//
//        //Log.e("resp",Uroll);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST,REG_URL ,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("resp",response);
//                        progressDialog.dismiss();
//
//                        try {
//                            JSONObject obj = new JSONObject(response);
//                            success= Integer.parseInt(obj.getString("s"));
//                            error= Integer.parseInt(obj.getString("e"));
//                            msg=obj.getString("m");
//
//                            if (success==1||success==0)
//                            {
//                                Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
//                                progressDialog.dismiss();
//                                editTextfnm.setText("");
//                                editTextlnm.setText("");
//                                editTextmail.setText("");
//                                editTextpass.setText("");
//                                editTextconfpass.setText("");
//                                radioGroup.clearCheck();
//                                checkBoxPrvy.setChecked(false);
//                                checkBoxTnc.setChecked(false);
//
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(RegisterActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
//                    }
//                }) {
//            @Override
//
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("fname", fname);
//                params.put("lname", lname);
//                params.put("email", email);
//                params.put("password", password);
//                params.put("usertype", Uroll);
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue= Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//    }

    }
