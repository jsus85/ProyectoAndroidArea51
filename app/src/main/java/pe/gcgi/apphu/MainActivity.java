package pe.gcgi.apphu;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CheckBox chk_welcome;

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
            Toast.makeText(MainActivity.this, "Por favor acepte la Licencia.", Toast.LENGTH_SHORT).show();
        }else{
            startActivity(intent);
        }

    }



}
