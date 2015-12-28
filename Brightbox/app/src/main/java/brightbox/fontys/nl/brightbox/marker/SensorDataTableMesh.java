package brightbox.fontys.nl.brightbox.marker;

import java.util.List;
import gl.Color;
import gl.scenegraph.MeshComponent;
import gl.scenegraph.Shape;
import gl.scenegraph.TriangulatedRenderData;

public class SensorDataTableMesh {

	static MeshComponent getMesh(List<List<Integer>> data) {

        Shape mesh = new Shape();

		TriangulatedRenderData r = new TriangulatedRenderData();
		r.setDrawModeToLines();
		r.setVertexArray(createVertices());
		r.setIndeceArray(createIndices(data));
        mesh.setMyRenderData(r);
        mesh.setColor(Color.green());
		return mesh;

	}


    static float[] createVertices(){
        float[] result = new float[1683];
        for(float i = 0;i<=50;i++){
            for(float j = 0;j<=10;j++){
                int pos = Math.round(i*11 +j)*3;
                result[pos] = j;
                result[pos+1]= i/10;
                result[pos+2] = 1f;
            }

        }
        return result;
    }

    // 0,0,0 1,0,0 2,0,0 3,0,0 4,0,0 5,0,0 6,0,0 7,0,0 8,0,0 9,0,0 10,0,0
    // 0,1,0 1,1,0 2,1,0 3,1,0 4,1,0 5,1,0 6,1,0 7,1,0 8,1,0 9,1,0 10,1,0
    static short[] createIndices(List<List<Integer>> data){
        short[] result = new short[120+(data.size()*20)];
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

        for(List<Integer> l : data){
            for(int i = 0;i<10;i++){
                if(l.size() <= i ||l.size() <= i+1 ){
                    continue;
                }
                Integer value  = l.get(i);
                Integer nextValue = l.get(i+1);

                short index1 = (short)(11 * value + i);
                short index2 = (short)(11*nextValue +i+1);

                result[count] = index1;
                count ++;
                result[count] = index2;
                count++;
            }
        }


        return result;
    }

}
