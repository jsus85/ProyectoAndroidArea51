package pe.gcgi.apphu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddCard extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        findViewById(R.id.button_next_card).setOnClickListener(this) ;
        Spinner spinner = (Spinner) findViewById(R.id.bank);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bank, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_next_card:
                final Intent intent = new Intent(AddCard.this,MenuMain.class);
                startActivity(intent);
                break;
        }

    }
}
