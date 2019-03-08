package com.anirudh.user.getfit;

  import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
  import android.support.v4.view.GravityCompat;
  import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.widget.DrawerLayout;
public class MainActivity extends AppCompatActivity implements SensorEventListener{

     TextView count ;
     boolean running = false  ;
     SensorManager sensor ;
     float cvalue = 0;
     Button reset ;
     private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout_id);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        count = (TextView) findViewById(R.id.count);

        sensor = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        reset = (Button) findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sensor.unregisterListener(MainActivity.this);
                cvalue = Float.parseFloat(count.getText().toString());
                count.setText ("0");
                }
            }
        );


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor Count = sensor.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (Count!= null) {
            sensor.registerListener(this,Count,SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this,"Sensor Not Found!" , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //running = false ;

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running){
            count.setText(String.valueOf(event.values[0] - cvalue));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
