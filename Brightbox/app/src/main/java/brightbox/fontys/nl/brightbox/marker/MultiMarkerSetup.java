package brightbox.fontys.nl.brightbox.marker;

import brightbox.fontys.nl.brightbox.entities.controllers.BrightBoxController;
import brightbox.fontys.nl.brightbox.entities.controllers.SensorDataController;
import brightbox.fontys.nl.brightbox.entities.models.BrightBox;
import brightbox.fontys.nl.brightbox.entities.models.SensorData;
import geo.GeoObj;
import gl.CustomGLSurfaceView;
import gl.GL1Renderer;
import gl.GLCamera;
import gl.GLFactory;
import gl.scenegraph.MeshComponent;
import gui.GuiSetup;
import markerDetection.MarkerDetectionSetup;
import markerDetection.MarkerObjectMap;
import markerDetection.UnrecognizedMarkerListener;
import system.EventManager;
import util.Vec;
import worldData.Obj;
import worldData.SystemUpdater;
import worldData.World;
import actions.Action;
import actions.ActionMoveCameraBuffered;
import actions.ActionRotateCameraBuffered;
import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MultiMarkerSetup extends MarkerDetectionSetup {

	private GLCamera camera;
	private World world;

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

	@Override
	public void _a3_registerMarkerObjects(MarkerObjectMap markerObjectMap) {

        try {
            List<BrightBox> all = BrightBoxController.getINSTANCE().findAll();
            for(int i = 0;i<all.size();i++){
                final BrightBox box = all.get(i);
				Log.d("MARKER", "Add marker :" + box.getId());

                markerObjectMap.put(new BasicMarker(box.getId(), camera) {

                    MeshComponent targetMesh;
                    boolean firstTime = true;

                    @Override
                    public void setObjectPos(Vec positionVec) {
				/*
				 * the first time this method is called an object could be
				 * created and added to the world
				 */
                        if (firstTime) {
                            firstTime = false;
                            Obj aNewObject = new Obj();

                            try {
                                List<SensorData> sensorDataList = SensorDataController.getINSTANCE().findByBrightBox(box);
                                List<List<Integer>> data = new ArrayList<List<Integer>>();

                                List<Integer> bloom = new ArrayList<>();
                                List<Integer> vega = new ArrayList<>();
                                List<Integer> grow = new ArrayList<>();
                                List<Integer> air = new ArrayList<>();
                                List<Integer> humidity = new ArrayList<>();
                                List<Integer> ph = new ArrayList<>();
                                List<Integer> ec = new ArrayList<>();
                                List<Integer> water = new ArrayList<>();
                                for(SensorData s : sensorDataList){
                                    bloom.add(s.getBloom().intValue());
                                    vega.add(s.getVega().intValue());
                                    air.add(s.getAirTemperatur().intValue());
                                    grow.add(s.getGrow().intValue());
                                    humidity.add(s.getHumidity().intValue());
                                    ph.add(s.getPhValue().intValue());
                                    ec.add(s.getEcValue().intValue());
                                    water.add(s.getWaterTemperatur().intValue());
                                }
                                data.add(bloom);
                                data.add(vega);
                                data.add(air);
                                data.add(grow);
                                data.add(humidity);
                                data.add(ph);
                                data.add(ec);
                                data.add(water);

                                targetMesh = SensorDataTableMesh.getMesh(data);
                                aNewObject.setComp(targetMesh);
                                world.add(aNewObject);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                        }
                        targetMesh.setPosition(positionVec);
                    }

                    @Override
                    public void setObjRotation(float[] rotMatrix) {
                        if (targetMesh != null) {
                            targetMesh.setRotationMatrix(rotMatrix);
                        }
                    }
                });
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
}
