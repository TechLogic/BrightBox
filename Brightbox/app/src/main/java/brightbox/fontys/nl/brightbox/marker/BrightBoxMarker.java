package brightbox.fontys.nl.brightbox.marker;

import android.content.Context;
import android.util.Log;

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
import gl.GLFactory;
import gl.scenegraph.MeshComponent;
import util.Vec;
import worldData.Obj;
import worldData.RenderableEntity;
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
    private List<String> timestamps = new ArrayList<>();
    private Obj aNewObject;
    private Context context;
    private List<MeshComponent> textMeshes = new ArrayList<>();


    public BrightBoxMarker(BrightBox box, GLCamera camera, World world,Context context) {
        super(box.getId(), camera);
        this.box = box;
        this.world = world;
        aNewObject = new Obj();
        this.context = context;
    }

    @Override
    public void setObjectPos(Vec positionVec) {
				/*
				 * the first time this method is called an object could be
				 * created and added to the world
				 */
        if (firstTime) {
            firstTime = false;

            try {
                List<SensorData> sensorDataList = SensorDataController.getINSTANCE().findByBrightBox(box);
                for (SensorData s : sensorDataList) {
                    bloom.add(s.getBloom().intValue());
                    vega.add(s.getVega().intValue());
                    air.add(s.getAirTemperatur().intValue());
                    grow.add(s.getGrow().intValue());
                    humidity.add(s.getHumidity().intValue());
                    ph.add(s.getPhValue().intValue());
                    ec.add(s.getEcValue().intValue());
                    water.add(s.getWaterTemperatur().intValue());
                    timestamps.add(s.getTimestamp().toString());
                }
                targetMesh = SensorDataTableMesh.getMesh(createMap(actType));
                addXAxisValues();
                addYAxisValues(timestamps.toArray(new String[0]));
                aNewObject.setComp(targetMesh);
                world.add(aNewObject);

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        targetMesh.getRotation();
        targetMesh.setPosition(positionVec);
    }


    private void addXAxisValues(){
        for (int i = 0; i < 55; i += 5) {
            MeshComponent mesh =   TextMesh.createTextMesh(context, Integer.toString(i));
            textMeshes.add(mesh);
            mesh.setPosition(new Vec(-6.1f, (i / 10f) + 0.5f, 0.2f));
            targetMesh.addChild(mesh);


            float zAngle = 85;
            float xAngle = -92.5f;
            float yAngle = 0;


            float xCos =(float)(Math.cos(xAngle));
            float xSin = (float)(Math.sin(xAngle));
            float yCos =(float)(Math.cos(yAngle));
            float ySin = (float)(Math.sin(yAngle));
            float zCos =(float)(Math.cos(zAngle));
            float zSin = (float)(Math.sin(zAngle));
            float[] rotMatrix = {yCos*zCos,zCos*xSin*ySin - xCos*zSin, xCos*zCos*ySin+xSin*zSin  ,0,
                                 yCos*zSin,xCos*zCos+xSin*ySin*zSin  , -zCos*xSin+xCos*ySin*zSin ,0,
                                 -ySin    ,yCos*xSin                 , xCos*yCos                 ,0,
                                 0        ,0                         ,0                          ,1};
            mesh.setRotationMatrix(rotMatrix);

        }

    }

    private void addYAxisValues(String[] timestamps) {
        for (int i = 0; i < timestamps.length; i++) {
            MeshComponent mesh = TextMesh.createTextMesh(context, timestamps[i]);
            textMeshes.add(mesh);
            mesh.setPosition(new Vec(i-5f,-1.1f, 0.2f));
            targetMesh.addChild(mesh);


            float zAngle = 85;
            float xAngle = -92.5f;
            float yAngle = -45f;


            float xCos = (float) (Math.cos(xAngle));
            float xSin = (float) (Math.sin(xAngle));
            float yCos = (float) (Math.cos(yAngle));
            float ySin = (float) (Math.sin(yAngle));
            float zCos = (float) (Math.cos(zAngle));
            float zSin = (float) (Math.sin(zAngle));
            float[] rotMatrix = {yCos * zCos, zCos * xSin * ySin - xCos * zSin, xCos * zCos * ySin + xSin * zSin, 0,
                    yCos * zSin, xCos * zCos + xSin * ySin * zSin, -zCos * xSin + xCos * ySin * zSin, 0,
                    -ySin, yCos * xSin, xCos * yCos, 0,
                    0, 0, 0, 1};
            mesh.setRotationMatrix(rotMatrix);

        }
    }


        private float[] lastRotMatrix;

    @Override
    public void setObjRotation(float[] rotMatrix) {
        if (targetMesh != null) {
            targetMesh.setRotationMatrix(rotMatrix);
        }
        String s = "";
        for(float f : rotMatrix){
            s+= ", "+ Float.toString(f);
        }

        lastRotMatrix = rotMatrix;
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


    private SensorDataType actType = SensorDataType.ALL;

    public void filterData(SensorDataType type){
        actType = type;
        Vec  pos = targetMesh.getPosition();
        targetMesh = SensorDataTableMesh.getMesh(createMap(type));
        targetMesh.setPosition(pos);
        targetMesh.setRotationMatrix(lastRotMatrix);
        addXAxisValues();
        addYAxisValues(timestamps.toArray(new String[0]));
        aNewObject.setComp(targetMesh);
    }


}
