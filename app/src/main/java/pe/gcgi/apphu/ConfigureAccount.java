package pe.gcgi.apphu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ConfigureAccount extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_account);

        findViewById(R.id.button_next_configure_account).setOnClickListener(this);

        Spinner spinner = (Spinner) findViewById(R.id.coin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.coin, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        final Intent intent = new Intent(ConfigureAccount.this, MenuMain.class);

        switch (view.getId()){
            case R.id.button_next_configure_account:
                startActivity(intent);
                break;

        }
    }
}
