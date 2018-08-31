package tbs.thinkbiz.solutions.iloveurhome;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import tbs.thinkbiz.solutions.iloveurhome.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactUsActivity extends AppCompatActivity {

    LinearLayout linearLayoutcall,linearLayoutmail;

    Button button;
    EditText editTextfnm,editTextlnm,editTextmail,editTextphone,editTextmsg;

    ProgressDialog progressDialog;
    private static String REG_URL="http://demotbs.com/dev/love/mobileapp/contactus.php";
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


        //Call

        linearLayoutcall = (LinearLayout) findViewById(R.id.li1);
        linearLayoutcall.setOnClickListener(new View.OnClickListener() {
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


        //Mail

        linearLayoutmail = (LinearLayout) findViewById(R.id.li2);
        linearLayoutmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"));
                PackageManager pm = getPackageManager();

                List<ResolveInfo> resInfo = pm.queryIntentActivities(emailIntent, 0);
                if (resInfo.size() > 0) {
                    ResolveInfo ri = resInfo.get(0);
                    // First create an intent with only the package name of the first registered email app
                    // and build a picked based on it
                    Intent intentChooser = pm.getLaunchIntentForPackage(ri.activityInfo.packageName);
                    Intent openInChooser =
                            Intent.createChooser(intentChooser,
                                    getString(R.string.mail));

                    // Then create a list of LabeledIntent for the rest of the registered email apps
                    List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
                    for (int i = 1; i < resInfo.size(); i++) {
                        // Extract the label and repackage it in a LabeledIntent
                        ri = resInfo.get(i);
                        String packageName = ri.activityInfo.packageName;
                        Intent intent = pm.getLaunchIntentForPackage(packageName);
                        intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
                    }

                    LabeledIntent[] extraIntents = intentList.toArray(new LabeledIntent[intentList.size()]);
                    // Add the rest of the email apps to the picker selection
                    openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
                    startActivity(openInChooser);
                }

//                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
//                emailIntent.setType("plain/text");
//                startActivity(emailIntent);

            }
        });

        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        final Button button=(Button)findViewById(R.id.buttonsbmt);

        editTextfnm=(EditText)findViewById(R.id.fname);
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
                if (editTextphone.getText().toString().length() == 0) {
                    editTextphone.setError("Phone number not entered");
                    editTextphone.requestFocus();
                }
                if (editTextmsg.getText().toString().length() == 0) {
                    editTextmsg.setError("Please type message here");
                    editTextmsg.requestFocus();
                }
                else {
                    SubmitData();
                }

            }
        });

    }

    private void SubmitData(){
        progressDialog = new ProgressDialog(ContactUsActivity.this);
        progressDialog.setMessage("Information Sending...");
        progressDialog.show();

        final String first_name = editTextfnm.getText().toString().trim();
        final String email = editTextmail.getText().toString().trim();
        final String phone_no = editTextphone.getText().toString().trim();
        final String your_message = editTextmsg.getText().toString().trim();

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


