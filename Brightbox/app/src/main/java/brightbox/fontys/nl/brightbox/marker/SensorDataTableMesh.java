package brightbox.fontys.nl.brightbox.marker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import brightbox.fontys.nl.brightbox.entities.models.SensorDataType;
import gl.Color;
import gl.GLFactory;
import gl.scenegraph.MeshComponent;
import gl.scenegraph.Shape;
import util.Vec;

public class SensorDataTableMesh {

	static MeshComponent getMesh(Map<SensorDataType,List<Integer>> data) {

        Shape mesh = new Shape();

		SensorDataRenderData r = new SensorDataRenderData();
		r.setDrawModeToLines();
		r.setVertexArray(createVertices());
		r.setCoordIndices(createIndices());
        r.setGraphIndices(createGraphIndices(data));
        mesh.setMyRenderData(r);
        mesh.setColor(Color.green());
		return mesh;
	}


    static float[] createVertices(){
        float[] result = new float[1683];
        for(float i = 0;i<=50;i++){
            for(float j = 0;j<=10;j++){
                int pos = Math.round(i*11 +j)*3;
                result[pos] = j-5;
                result[pos+1]= i/10;
                result[pos+2] = 1f;
            }

        }
        return result;
    }


    static Map<SensorDataType,short[]> createGraphIndices(Map<SensorDataType,List<Integer>>data){
        Map<SensorDataType,short[]> map = new HashMap<>();
        for(Map.Entry<SensorDataType,List<Integer>> entry : data.entrySet()){
            int len = entry.getValue().size()*2;
            if(len > 20){
                len = 20;
            }
            short[] result = new short[len];
            int count = 0;
            for(int i = 0;i<10;i++){
                if(entry.getValue().size() <= i ||entry.getValue().size() <= i+1 ){
                    continue;
                }
                Integer value  = entry.getValue().get(i);
                Integer nextValue = entry.getValue().get(i + 1);

                short index1 = (short)(11 * value + i);
                short index2 = (short)(11*nextValue +i+1);

                result[count] = index1;
                count ++;
                result[count] = index2;
                count++;
            }
            map.put(entry.getKey(),result);
        }
    return  map;
    }

    // 0,0,0 1,0,0 2,0,0 3,0,0 4,0,0 5,0,0 6,0,0 7,0,0 8,0,0 9,0,0 10,0,0
    // 0,1,0 1,1,0 2,1,0 3,1,0 4,1,0 5,1,0 6,1,0 7,1,0 8,1,0 9,1,0 10,1,0
    static short[] createIndices(){
        short[] result = new short[120];
        int count = 0;
        for(short i = 550;i>0;i-=11){
            result[count] = i;
            count++;
            short next = i;
            next -= 11;
            result[count] = next ;
            count++;
        }
        for(short j = 0;j<10;j++){
            result[count] = j;
            count++;
            short next = j;
            next++;
            result[count] = next;
            count++;
        }
        return result;
    }

}
