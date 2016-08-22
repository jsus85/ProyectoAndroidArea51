package pe.gcgi.apphu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConfigureAccount extends AppCompatActivity implements Spinner.OnItemSelectedListener ,View.OnClickListener{

    //Declarar el Spinner
    private Spinner spinner;
    // ArrayList para el Spinner Items
    private ArrayList<String> monedas;
    //JSON Array
    private JSONArray result;
    private StringRequest stringRequest;

    private String valueSpinner;
    private String url = "http://gcgi.pe/apphudemo/CCMonedas.php";
    private String url_register_capacity = "http://gcgi.pe/apphudemo/CCAccount.php";
    //Creating a request queue
    private RequestQueue requestQueue;
    private Context context;
    EditText edittex_capacity_monthly;

    private String cod_cli ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_account);

        context = ConfigureAccount.this;
        requestQueue = Volley.newRequestQueue(context);

        edittex_capacity_monthly = (EditText) findViewById(R.id.capacity_monthly);

        //Inicializar el ArrayList
        monedas = new ArrayList<String>();
        //Inicializar Spinner
        spinner = (Spinner) findViewById(R.id.coin);
        spinner.setOnItemSelectedListener(this);
        findViewById(R.id.button_next_configure_account).setOnClickListener(this);

        getDataCoins();
    }

    private void getDataCoins() {

        stringRequest = new StringRequest(url,new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JSONObject j = null;

                try {
                    //Parsing the fetched Json String to JSON Object
                    j = new JSONObject(response);
                    //Storing the Array of JSON String to our JSON Array
                    result = j.getJSONArray("result");
                    //Calling method getStudents to get the students from the JSON Array
                    getCoins(result);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getCoins(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);
                //Adding the name of the student to array list
                monedas.add(json.getString("moneda"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner.setAdapter(new ArrayAdapter<String>(ConfigureAccount.this, android.R.layout.simple_spinner_dropdown_item, monedas));
    }


    //Method to get monedas -> valor of a particular position
    private String getName(int position){
        String name="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            name = json.getString("valor");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return name;
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){
            case R.id.button_next_configure_account:
                 accountSettingsIndebtedness();
                break;
        }
    }

    // Update: c_cliente -> cli_cun
    public void accountSettingsIndebtedness(){

            stringRequest = new StringRequest(Request.Method.POST, url_register_capacity, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if(response.equalsIgnoreCase("succes") ){
                        Toast.makeText(ConfigureAccount.this, "Capacidad de Endeudamiento se registro correctamente", Toast.LENGTH_SHORT).show();
                        final Intent intent = new Intent(ConfigureAccount.this, AddCard.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(ConfigureAccount.this, "Error en Guardar.", Toast.LENGTH_SHORT).show();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams()  {
                    Map<String, String> parametros = new HashMap<>();

                    // fetching value from sharedpreference
                    SharedPreferences sharedPreferences = getSharedPreferences("myloginapp", Context.MODE_PRIVATE);
                    //Fetching the boolean value form sharedpreferences
                    cod_cli = sharedPreferences.getString("cli_cod","");

                    parametros.put("tipo","1002");
                    parametros.put("codigo",cod_cli);
                    parametros.put("capendeuda", edittex_capacity_monthly.getText().toString());
                    parametros.put("acccont","1");
                    parametros.put("tipmon",valueSpinner);

                    return parametros;

                }
            };
        //Adding request to the queue
        requestQueue.add(stringRequest);

    }


    //this method will execute when we pic an item from the spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        valueSpinner = getName(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
