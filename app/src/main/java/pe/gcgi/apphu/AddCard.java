package pe.gcgi.apphu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddCard extends AppCompatActivity implements   Spinner.OnItemSelectedListener,   View.OnClickListener {

    private Spinner spinnerBank;
    private Spinner spinnerProduct;

    private ArrayList<String> banks;
    private ArrayList<String> products;
    private JSONArray resultBank;
    private JSONArray resultProduct;

    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    private Context context;

    public String valueIdBank;
    private String valueIdProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        context = AddCard.this;
        requestQueue = Volley.newRequestQueue(context);

        findViewById(R.id.button_next_card).setOnClickListener(this);

        banks = new ArrayList<String>();
        products = new ArrayList<String>();

        spinnerBank    = (Spinner) findViewById(R.id.spinner_bank);
        spinnerProduct = (Spinner) findViewById(R.id.spinner_product);

        spinnerBank.setOnItemSelectedListener(this);

        getDataBanks();

    }

    // Get Web Service data Banks
    private void getDataBanks() {

        String url = "http://gcgi.pe/apphudemo/CCControl.php?tipo=2002";
        stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response);
                    resultBank = jsonObject.getJSONArray("result");
                    getBanks(resultBank);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(getClass().getName(),error.getMessage());
            }
        });

        requestQueue.add(stringRequest);
    }
    // Get Banks
    private void getBanks(JSONArray resultBank) {

        for(int i=0;i<resultBank.length();i++){
            try {
                JSONObject json = resultBank.getJSONObject(i);
                banks.add(json.getString("ENT_DES"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spinnerBank.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,banks));
    }
    // Get ID BANK
    private String getIdBanks(int position){
        String idBank = "";
        try {
            //Getting object of given index
            JSONObject json = resultBank.getJSONObject(position);
            //Fetching name from that object
            idBank = json.getString("ENT_COD");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return idBank;
    }

    // Get Web Service data Products
    private void getDataProducts(String idBank){

        String url_products = "http://gcgi.pe/apphudemo/CCControl.php?tipo=2003&entidad="+idBank;
        stringRequest = new StringRequest(url_products, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject2 = null;

                try {
                    jsonObject2    = new JSONObject(response);
                    resultProduct = jsonObject2.getJSONArray("result");

                    getProducts(resultProduct);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }


    private void getProducts(JSONArray resultProduct2) {

        products.clear();
        for(int ix=0;ix<resultProduct2.length();ix++){
            try {

                JSONObject json2 = resultProduct2.getJSONObject(ix);
                products.add(json2.getString("CEN_DES"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        spinnerProduct.setAdapter(new ArrayAdapter<String>(AddCard.this,android.R.layout.simple_spinner_dropdown_item,products));

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        Spinner spinner = (Spinner) adapterView;

        switch (spinner.getId()){
            case R.id.spinner_bank:
                //valueIdBank = getIdBanks(position);
                getDataProducts(getIdBanks(position));
                break;
            case R.id.spinner_product:
                Toast.makeText(AddCard.this, "spinne producto", Toast.LENGTH_SHORT).show();
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
