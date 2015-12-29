package brightbox.fontys.nl.brightbox.marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import brightbox.fontys.nl.brightbox.entities.controllers.SensorDataController;
import brightbox.fontys.nl.brightbox.entities.models.BrightBox;
import brightbox.fontys.nl.brightbox.entities.models.SensorData;
import brightbox.fontys.nl.brightbox.entities.models.SensorDataType;
import gl.GLCamera;
import gl.scenegraph.MeshComponent;
import util.Vec;
import worldData.Obj;
import worldData.World;

/**
 * Created by techlogic on 29.12.15.
 */
public class BrightBoxMarker extends  BasicMarker {

    private  MeshComponent targetMesh;
    private  boolean firstTime = true;
    private BrightBox box;
    private  World world;


    private List<Integer> bloom = new ArrayList<>();
    private List<Integer> vega = new ArrayList<>();
    private List<Integer> grow = new ArrayList<>();
    private List<Integer> air = new ArrayList<>();
    private List<Integer> humidity = new ArrayList<>();
    private List<Integer> ph = new ArrayList<>();
    private List<Integer> ec = new ArrayList<>();
    private List<Integer> water = new ArrayList<>();

    private Obj aNewObject;



    public BrightBoxMarker(BrightBox box, GLCamera camera, World world) {
        super(box.getId(), camera);
        this.box = box;
        this.world = world;
    }

    @Override
    public void setObjectPos(Vec positionVec) {
				/*
				 * the first time this method is called an object could be
				 * created and added to the world
				 */
        if (firstTime) {
            firstTime = false;
             aNewObject = new Obj();

            try {
                List<SensorData> sensorDataList = SensorDataController.getINSTANCE().findByBrightBox(box);
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
                targetMesh = SensorDataTableMesh.getMesh(createMap(SensorDataType.ALL));
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


    private Map<SensorDataType,List<Integer>> createMap(SensorDataType type) {
        Map<SensorDataType, List<Integer>> data = new HashMap<>();
        switch (type) {
            case ALL: {
                data.put(SensorDataType.BLOOM, bloom);
                data.put(SensorDataType.VEGA, vega);
                data.put(SensorDataType.AIR_TEMP, air);
                data.put(SensorDataType.GROW, grow);
                data.put(SensorDataType.HUMIDITY, humidity);
                data.put(SensorDataType.PH_VALUE, ph);
                data.put(SensorDataType.EC_VALUE, ec);
                data.put(SensorDataType.WATER_TEMP, water);
                return data;
            }
            case BLOOM: {
                data.put(SensorDataType.BLOOM, bloom);
                return data;
            }

            case VEGA: {
                data.put(SensorDataType.VEGA, vega);
                return data;
            }

            case AIR_TEMP: {
                data.put(SensorDataType.AIR_TEMP, air);
                return data;
            }

            case WATER_TEMP: {
                data.put(SensorDataType.WATER_TEMP, water);
                return data;
            }

            case GROW: {
                data.put(SensorDataType.GROW, grow);
                return data;
            }

            case HUMIDITY: {
                data.put(SensorDataType.HUMIDITY, humidity);
                return data;
            }

            case PH_VALUE: {
                data.put(SensorDataType.PH_VALUE, ph);
                return data;
            }

            case EC_VALUE: {
                data.put(SensorDataType.EC_VALUE, ec);
                return data;
            }
        }
        return  data;
    }


    public void filterData(SensorDataType type){
        targetMesh = SensorDataTableMesh.getMesh(createMap(type));
        aNewObject.setComp(targetMesh);
    }


}
