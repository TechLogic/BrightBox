package brightbox.fontys.nl.brightbox;

import android.content.Context;
import android.vuzix.gesture.GestureControl;

import com.vuzix.hardware.GestureSensor;

import java.util.ArrayList;
import java.util.List;

import util.Log;

/**
 * Created by techlogic on 29.12.15.
 */
public class GestureController extends GestureSensor  {

    private List<GestureListener> listeners = new ArrayList<>();



    public GestureController(Context context) {
        super(context);
    }

    public void  addListener(GestureListener listener){
        listeners.add(listener);
    }

    public void removeListener(GestureListener listener){
        listeners.remove(listener);
    }


    private void executeGesture(GestureDirection direction){
        for(GestureListener l : listeners){
            l.executeGesture(direction);
        }
    }


    @Override
    protected void onBackSwipe(int i) {


        Log.d("GESTUREDETECTION", "Backward");
        executeGesture(GestureDirection.BACKWARD);
    }

    @Override
    protected void onForwardSwipe(int i) {
        Log.d("GESTUREDETECTION","Forward");
        executeGesture(GestureDirection.FORWARD);
    }

    @Override
    protected void onUp(int i) {
        Log.d("GESTUREDETECTION", "Up");
        executeGesture(GestureDirection.UP);
    }

    @Override
    protected void onDown(int i) {
        Log.d("GESTUREDETECTION", "Down");
        executeGesture(GestureDirection.DOWN);
    }

    @Override
    protected void onNear() {
        Log.d("GESTUREDETECTION","Near");
    }

    @Override
    protected void onFar() {
        Log.d("GESTUREDETECTION","Far");
        }
}
