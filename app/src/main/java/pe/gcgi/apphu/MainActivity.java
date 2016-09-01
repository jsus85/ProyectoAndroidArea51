package pe.gcgi.apphu;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CheckBox chk_welcome;
    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;
    private String welcome = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_next_welcome).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        final Intent intent = new Intent(MainActivity.this, Register_User.class);
        chk_welcome = (CheckBox) findViewById(R.id.chk_welcome);

        if(!chk_welcome.isChecked()){
            Toast.makeText(MainActivity.this, "Por favor Acepte la Licencia.", Toast.LENGTH_SHORT).show();
        }else{
            startActivity(intent);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences("myloginapp", Context.MODE_PRIVATE);
        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean("loggedin", false);
        welcome = sharedPreferences.getString("welcome", "0");
        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            Intent intent = new Intent(MainActivity.this, MenuMain.class);
            startActivity(intent);

        }else{
                if(welcome.equals("1")){
                    Intent intent = new Intent(MainActivity.this,Login.class);
                    startActivity(intent);
                }
        }



    }
}
