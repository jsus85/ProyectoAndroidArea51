package pe.gcgi.apphu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private RequestQueue requestQueue;
    private Context context;

    private EditText password;
    private EditText email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.button_send_login).setOnClickListener(this);

        context = Login.this;
        requestQueue = Volley.newRequestQueue(context);

        //input controls
        password = (EditText) findViewById(R.id.password);
        email    = (EditText) findViewById(R.id.email);
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){
            case R.id.button_send_login:

                Register_User register_user  = new Register_User();

                if(password.length()<3){
                    password.setError("Error en Password.");
                    password.requestFocus();
                }else if(email.length()<3){
                    email.setError("Error en Email.");
                    email.requestFocus();
                }else if(!register_user.validateEmail(email.getText().toString())){
                    email.setError("Error en Email invalido");
                    email.requestFocus();
                }else{

                    login();
                }

                break;

        }

    }

    public void login(){

        String url = "http://gcgi.pe/apphudemo/CCLogin.php?usermail="+email.getText().toString()+"&password="+password.getText().toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new
                Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {


                            if(response.getString("status").equalsIgnoreCase("1") ){
                                JSONObject jsonObject = new JSONObject(response.toString());
                                JSONArray jsonArray = jsonObject.getJSONArray("info");

                                Toast.makeText(Login.this, "Bienvenido "+jsonArray.getJSONObject(0).getString("cli_nom"), Toast.LENGTH_SHORT).show();

                               //Creating a shared preference
                                SharedPreferences sharedPreferences = Login.this.getSharedPreferences("myloginapp", Context.MODE_PRIVATE);
                                //Creating editor to store values to shared preferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("loggedin", true);
                                editor.putString("email", email.getText().toString());
                                //Saving values to editor
                                editor.commit();


                                final Intent intent = new Intent(Login.this,ConfigureAccount.class);
                                startActivity(intent);

                            }else{
                                Toast.makeText(Login.this, "Error en Email y Clave incorrecto", Toast.LENGTH_SHORT).show();
                            }


                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(getClass().getName(),error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
