package brightbox.fontys.nl.brightbox.marker;

import android.app.Activity;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import actions.Action;
import actions.ActionMoveCameraBuffered;
import actions.ActionRotateCameraBuffered;
import brightbox.fontys.nl.brightbox.GestureController;
import brightbox.fontys.nl.brightbox.GestureDirection;
import brightbox.fontys.nl.brightbox.GestureListener;
import brightbox.fontys.nl.brightbox.entities.controllers.BrightBoxController;
import brightbox.fontys.nl.brightbox.entities.models.BrightBox;
import brightbox.fontys.nl.brightbox.entities.models.SensorDataType;
import geo.GeoObj;
import gl.CustomGLSurfaceView;
import gl.GL1Renderer;
import gl.GLCamera;
import gl.GLFactory;
import gui.GuiSetup;
import markerDetection.MarkerDetectionSetup;
import markerDetection.MarkerObjectMap;
import markerDetection.UnrecognizedMarkerListener;
import system.EventManager;
import util.Vec;
import worldData.SystemUpdater;
import worldData.World;

public class MultiMarkerSetup extends MarkerDetectionSetup implements GestureListener {

	private GLCamera camera;
	private World world;
    private  GestureController controller;


    public MultiMarkerSetup(GestureController controller) {
        this.controller = controller;
        controller.addListener(this);
    }


      @Override
    public void onResume(Activity a) {
         controller.register();
     }

     @Override
     public void onPause(Activity a) {
         controller.unregister();
     }
    @Override
	public void _a_initFieldsIfNecessary() {
		camera = new GLCamera(new Vec(0, 0, 10));
		world = new World(camera);
	}

	@Override
	public UnrecognizedMarkerListener _a2_getUnrecognizedMarkerListener() {
		return new UnrecognizedMarkerListener() {

			@Override
			public void onUnrecognizedMarkerDetected(int markerCode,
					float[] mat, int startIdx, int endIdx, int rotationValue) {
				System.out.println("unrecognized markerCode=" + markerCode);
			}
		};

	}

    private List<BrightBoxMarker> markerList = new ArrayList<>();

	@Override
	public void _a3_registerMarkerObjects(MarkerObjectMap markerObjectMap) {

        try {
            List<BrightBox> all = BrightBoxController.getINSTANCE().findAll();
            for(int i = 0;i<all.size();i++){
                final BrightBox box = all.get(i);
				Log.d("MARKERADD", "Add marker :" + box.getId());
                BrightBoxMarker marker = new BrightBoxMarker(box,camera,world);
                markerList.add(marker);
                markerObjectMap.put(marker);
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
}

	@Override
	public void _b_addWorldsToRenderer(GL1Renderer renderer,
			GLFactory objectFactory, GeoObj currentPosition) {
		renderer.addRenderElement(world);
	}

	@Override
	public void _c_addActionsToEvents(EventManager eventManager,
			CustomGLSurfaceView arView, SystemUpdater updater) {
		arView.addOnTouchMoveListener(new ActionMoveCameraBuffered(camera, 5,
				25));
		Action rot = new ActionRotateCameraBuffered(camera);
		updater.addObjectToUpdateCycle(rot);
		eventManager.addOnOrientationChangedAction(rot);
		eventManager.addOnTrackballAction(new ActionMoveCameraBuffered(camera,
				1, 25));

	}

	@Override
	public void _d_addElementsToUpdateThread(SystemUpdater updater) {
		updater.addObjectToUpdateCycle(world);

	}

	@Override
	public void _e2_addElementsToGuiSetup(GuiSetup guiSetup, Activity activity) {
		}


    private int filterPosition;
    private static SensorDataType[] filter = {SensorDataType.ALL,SensorDataType.VEGA,SensorDataType.BLOOM,SensorDataType.AIR_TEMP,SensorDataType.WATER_TEMP,SensorDataType.PH_VALUE,SensorDataType.EC_VALUE,SensorDataType.HUMIDITY,SensorDataType.GROW};


    private void updateMarkerObjects(){
        SensorDataType type = filter[filterPosition];
        for(BrightBoxMarker marker : markerList){
            marker.filterData(type);
        }
    }

    @Override
    public void executeGesture(GestureDirection direction) {
        switch(direction){
            case FORWARD:{
                Log.d("GESTUREDETECTION","Forward");
                filterPosition = (filterPosition+1)%filter.length;
                updateMarkerObjects();
                return;
            }
            case BACKWARD:{
                Log.d("GESTUREDETECTION","Backward");
                filterPosition--;
                if(filterPosition < 0){
                filterPosition = 8;
                }
                updateMarkerObjects();
                return;
            }
            default:{
                Log.d("GESTUREDETECTION","Other");
            }

        }


    }
}
