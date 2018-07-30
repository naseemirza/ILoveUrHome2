package com.example.user.iloveurhome;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

public class ContactUsActivity extends AppCompatActivity {

    LinearLayout linearLayout;

    Button button;
    EditText editTextfnm,editTextlnm,editTextmail,editTextphone,editTextmsg;

    ProgressDialog progressDialog;
    private static String REG_URL="http://demotbs.com/dev/love/mobileapp/contactus.php";
    String Uroll;
    int success;
    int error;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);



        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.contactusbar);
        View view = getSupportActionBar().getCustomView();

        linearLayout = (LinearLayout) findViewById(R.id.li1);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "734-972-6003";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                intent.setData(Uri.parse("tel:" + number));
                if (ActivityCompat.checkSelfPermission(ContactUsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                }
                startActivity(intent);
            }
        });

        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//        final EditText name   = (EditText)findViewById(R.id.fname);
//        final EditText mail   = (EditText)findViewById(R.id.editTextemail);
//        final EditText phone   = (EditText)findViewById(R.id.phone);
//        final EditText msg   = (EditText)findViewById(R.id.editText2);
        final Button button=(Button)findViewById(R.id.buttonsbmt);

        editTextfnm=(EditText)findViewById(R.id.fname);
        //editTextlnm=(EditText)findViewById(R.id.lname);
        editTextphone=(EditText)findViewById(R.id.phone);
        editTextmsg=(EditText)findViewById(R.id.editText2);
        editTextmail=(EditText)findViewById(R.id.editTextemail);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String mail = editTextmail.getText().toString().trim();

                if (editTextfnm.getText().toString().length() == 0) {
                    editTextfnm.setError("First name not entered");
                    editTextfnm.requestFocus();
                }


                if (TextUtils.isEmpty(mail)) {
                    editTextmail.setError("Please enter your email");
                    editTextmail.requestFocus();
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                    editTextmail.setError("Enter a valid email");
                    editTextmail.requestFocus();
                }
                else {
                    Regst();
                }

//                StringBuilder body = new StringBuilder();
//                body.append("Name: "+name.getText().toString());
//                body.append("\n\n\nMail: "+mail.getText().toString());
//                body.append("\n\n\nPhone: "+phone.getText().toString());
//                body.append("\n\n\nProduct: "+msg.getText().toString());
//
//
//                Intent i = new Intent(Intent.ACTION_SEND);
//                i.setType("text/plain");
//                i.putExtra(Intent.EXTRA_EMAIL, new String[] {"naseemirza786@gmail.com"} );
//                i.putExtra(Intent.EXTRA_SUBJECT, "Customer Details");
//                i.putExtra(Intent.EXTRA_TEXT, body.toString());
//                startActivity(i);

            }
        });

    }

    private void Regst(){
        progressDialog = new ProgressDialog(ContactUsActivity.this);
        progressDialog.setMessage("Information Sending...");
        progressDialog.show();

        final String first_name = editTextfnm.getText().toString().trim();
        //final String last_name = editTextlnm.getText().toString().trim();
        final String email = editTextmail.getText().toString().trim();
        final String phone_no = editTextphone.getText().toString().trim();
        final String your_message = editTextmsg.getText().toString().trim();


        //Log.e("resp",Uroll);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,REG_URL ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("resp",response);
                        progressDialog.dismiss();

                        try {
                            JSONObject obj = new JSONObject(response);
                            success= Integer.parseInt(obj.getString("s"));
                            error= Integer.parseInt(obj.getString("e"));
                            msg=obj.getString("m");

                            if (success==1||success==0)
                            {
                                Toast.makeText(ContactUsActivity.this, msg, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                editTextfnm.setText("");
                                //editTextlnm.setText("");
                                editTextmail.setText("");
                                editTextphone.setText("");
                                editTextmsg.setText("");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ContactUsActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ContactUsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("first_name", first_name);
               // params.put("last_name", "");
                params.put("email", email);
                params.put("phone_no", phone_no);
                params.put("your_message", your_message);
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}


