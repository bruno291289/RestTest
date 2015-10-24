package com.br.resttest;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.br.resttest.com.br.resttest.AsyncActivityService;
import com.br.resttest.com.br.resttest.tasks.RestOperation;

public class MainActivity extends AppCompatActivity implements AsyncActivityService<String> {

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText txtUrl = (EditText) findViewById(R.id.url);
        txtUrl.setText("http://www.webserviceX.NET");
        EditText txtRoute = (EditText) findViewById(R.id.route);
        txtRoute.setText("/globalweather.asmx/GetWeather?CityName=New+York&CountryName=United+States");
    }

    public void startService(View view) {
        EditText txtUrl = (EditText) findViewById(R.id.url);
        EditText txtRoute = (EditText) findViewById(R.id.route);
        progress = ProgressDialog.show(this, "Searching information",
                "Talking to the server", true);
        new RestOperation(txtUrl.getText().toString(), txtRoute.getText().toString(), this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startOperation() {
        TextView t = (TextView) findViewById(R.id.result);
        t.setText("INICIANDO A OPERAÇÃO");
    }

    @Override
    public void endOperation(String content, String error) {
        TextView t = (TextView) findViewById(R.id.result);
        if(error != null & !error.isEmpty()){
            t.setText(error);
        } else{
            t.setText(content);
        }
        progress.dismiss();
    }
}
