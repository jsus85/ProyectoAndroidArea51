package pe.gcgi.apphu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Register_User extends AppCompatActivity implements View.OnClickListener {

    EditText names;
    EditText password;
    EditText email;

    private String url = "http://gcgi.pe/apphudemo/CCAccount.php";
    private StringRequest stringRequest;
    private Context context;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        context = Register_User.this;
        requestQueue = Volley.newRequestQueue(context);

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
                    registerUser();
                }

                break;
        }
    }

    public void registerUser(){

        stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Log.d("rta_servidor", response);
                        if(response.equalsIgnoreCase("succes") ){
                            final Intent intent = new Intent(Register_User.this, Login.class);
                            Toast.makeText(context, "Gracias, ha sido registrado exitosamente, por favor Inicie Sesión.", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }else{
                            //Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                            // Error Email registrado
                            new AlertDialog.Builder(context)
                                    .setTitle("Lo Sentimos")
                                    .setMessage("Email registrado , desea recuperar la clave? ")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            // Some stuff to do when ok got clicked
                                        }
                                    })
                                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            // Some stuff to do when cancel got clicked
                                        }
                                    })
                                    .show();
                        }


                    }
                },  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error_servidor", error.toString());
                    }
        })
        {
            @Override
            protected Map<String, String> getParams()  {

                Map<String, String> parametros = new HashMap<>();

                parametros.put("tipo", "1001");
                parametros.put("username", names.getText().toString());
                parametros.put("password", password.getText().toString());
                parametros.put("usermail", email.getText().toString());

                return parametros;
            }
        };

        requestQueue.add(stringRequest);
    }

    //Return true if email is valid and false if email is invalid
    public boolean validateEmail(String email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }


}
