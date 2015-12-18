package brightbox.fontys.nl.brightbox;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.vuzix.hardware.GestureSensor;

import java.util.List;
import java.util.concurrent.ExecutionException;

import brightbox.fontys.nl.brightbox.entities.controllers.BrightBoxController;
import brightbox.fontys.nl.brightbox.entities.controllers.SensorDataController;
import brightbox.fontys.nl.brightbox.entities.models.SensorData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

       BrightBoxController controller = BrightBoxController.getINSTANCE();
        try {
            controller.findAll();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SensorDataController sensorDataController = SensorDataController.getINSTANCE();
        try {
       List<SensorData> data =     sensorDataController.findAll();
            Log.d("Test","Test");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    class mySensor extends GestureSensor {

        public mySensor(Context context) {
            super(context);
        }

        @Override
        protected void onBackSwipe(int i) {

        }

        @Override
        protected void onForwardSwipe(int i) {

        }

        @Override
        protected void onUp(int i) {

        }

        @Override
        protected void onDown(int i) {

        }

        @Override
        protected void onNear() {

        }

        @Override
        protected void onFar() {

        }
    }
}
