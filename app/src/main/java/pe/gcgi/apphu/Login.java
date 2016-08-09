package pe.gcgi.apphu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.button_send_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final Intent intent = new Intent(Login.this,ConfigureAccount.class);

        switch (view.getId()){
            case R.id.button_send_login:
                startActivity(intent);
                break;

        }

    }
}
