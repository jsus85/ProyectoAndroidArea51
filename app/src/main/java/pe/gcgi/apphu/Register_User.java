package pe.gcgi.apphu;

import android.content.Intent;
import android.media.MediaCodec;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Register_User extends AppCompatActivity implements View.OnClickListener {

     EditText names;
    EditText password;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        findViewById(R.id.button_redirect_login).setOnClickListener(this);
        findViewById(R.id.button_register_user).setOnClickListener(this);

        //input controls
        names    = (EditText) findViewById(R.id.names);
        password = (EditText) findViewById(R.id.password);
        email    = (EditText) findViewById(R.id.email);
    }

    @Override
    public void onClick(View view) {
        final Intent intent = new Intent(Register_User.this, Login.class);
        switch (view.getId()){
            case R.id.button_redirect_login:
                startActivity(intent);
                break;
            case R.id.button_register_user:

                if(names.getText().length()<2){
                    names.setError("Error en nombres.");
                    names.requestFocus();
                }else if(password.length()<3){
                    password.setError("Error en Password.");
                    password.requestFocus();
                }else if(email.length()<3){
                    email.setError("Error en Email.");
                    email.requestFocus();
                }else if(!validateEmail(email.getText().toString())){
                    email.setError("Error en Email invalido");
                    email.requestFocus();
                }else{

                    startActivity(intent);

                }

                break;
        }
    }


    //Return true if email is valid and false if email is invalid
    protected boolean validateEmail(String email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

}
