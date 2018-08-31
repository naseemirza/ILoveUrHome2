package tbs.thinkbiz.solutions.iloveurhome;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLogin;
    private TextView reg, forgotpass;

    EditText editTextpass, editTextmail;
    private static String LGN_URL = "https://www.itshades.com/appwebservices/login.php";
    final Context context = this;
    ProgressDialog progressDialog;
    String userrole;

    int success;
    int error;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //editTextmail = (EditText) findViewById(R.id.editTextU);
        //editTextpass = (EditText) findViewById(R.id.editTextP);

        //forgotpass = (TextView) findViewById(R.id.textViewfrgt);

//        forgotpass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String actname="Forgot Password";
//                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//                SharedPreferences.Editor edit = pref.edit();
//
//                edit.putString("Actvname",actname);
//
//                edit.commit();
//                Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
//                startActivity(intent);
//            }
//        });


        reg = (TextView) findViewById(R.id.textViewRgs);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                 startActivity(intent);
                String actname="Register Here";
                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();

                edit.putString("Actvname",actname);

                edit.commit();

            }
        });

        buttonLogin = (Button) findViewById(R.id.buttonL);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);

//                Intent intent=new Intent(LoginActivity.this,CorpMainActivity.class);
//                startActivity(intent);

//                final String email = editTextmail.getText().toString().trim();
//
//                if (TextUtils.isEmpty(email)) {
//                    editTextmail.setError("Please enter your email");
//                    editTextmail.requestFocus();
//                }
//
//                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                    editTextmail.setError("Enter a valid email");
//                    editTextmail.requestFocus();
//                }
//
//                if (editTextpass.getText().toString().length() == 0) {
//                    editTextpass.setError("Password not entered");
//                    editTextpass.requestFocus();
//                }
//                if (editTextpass.getText().toString().length() < 6) {
//                    editTextpass.setError("Password should be atleast of 6 charactors");
//                    editTextpass.requestFocus();
//                } else {
//
//                    Loginbtn();
//                }

            }
        });
    }
}
