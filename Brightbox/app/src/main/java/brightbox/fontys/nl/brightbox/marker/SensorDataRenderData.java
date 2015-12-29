package brightbox.fontys.nl.brightbox.marker;

import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.microedition.khronos.opengles.GL10;

import brightbox.fontys.nl.brightbox.entities.models.SensorDataType;
import gl.Color;
import gl.GLFactory;
import gl.GLUtilityClass;
import gl.scenegraph.RenderData;
import util.Vec;

/**
 * Created by techlogic on 29.12.15.
 */
public class SensorDataRenderData extends RenderData {
    protected ShortBuffer indexBuffer;
    protected int indiceCount;

    protected Map<SensorDataType,ShortBuffer> graphIndex;

    public SensorDataRenderData() {
        graphIndex = new HashMap<>();
    }

    @Override
    public void updateShape(ArrayList<Vec> shapeArray) {
        throw new UnsupportedOperationException();
    }

    public void setCoordIndices(short[] s) {
        indexBuffer = GLUtilityClass.createAndInitShortBuffer(s);
    }

    public void setGraphIndices(Map<SensorDataType,short[]> data){
        for(Map.Entry<SensorDataType,short[]> s : data.entrySet()){
            ShortBuffer buffer = GLUtilityClass.createAndInitShortBuffer(s.getValue());
            graphIndex.put(s.getKey(),buffer);
        }
    }

    /**
     * every verticle has to be defined at least one time in the indicesArray,
     * for example there are 4 verticles than you will have to add all numbers 0
     * to 3 to the indiceArray: like this: short[] indices = { 0, 1, 2, 0, 2, 3
     * };
     *
     * this algo works only for convex 2d shapes and should be replaced by a
     * better one (for example this
     * http://www.cs.unc.edu/~dm/CODE/GEM/chapter.html )
     *
     * TODO replace this by a real 3d trianguation algorithm
     *
     * @param shape
     * @return
     */
    protected short[] triangulationOfShape(ArrayList<Vec> shape) {
        System.out.println("shape.size() " + shape.size());
        indiceCount = (shape.size() - 2) * 3;
        if (indiceCount < 1)
            return null;
        short[] indices = new short[indiceCount];
        short a = 1;
        short b = 2;

        for (int i = 0; i < indiceCount - 2; i += 3) {
            indices[i] = 0;
            indices[i + 1] = a;
            indices[i + 2] = b;
            a = b;
            b++;
        }
        return indices;
    }

    @Override
    public void draw(GL10 gl) {

        gl.glLineWidth(3f);
        // Enabled the vertices buffer for writing and to be used during
        // rendering.
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // Specifies the location and data format of an array of vertex
        // coordinates to use when rendering.
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        if (normalsBuffer != null) {
            // Enable normals array (for lightning):
            gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
            gl.glNormalPointer(GL10.GL_FLOAT, 0, normalsBuffer);
        }

        gl.glDrawElements(drawMode, indexBuffer.limit(),
                GL10.GL_UNSIGNED_SHORT, indexBuffer);

        drawGraphs(gl);

        // Disable the vertices buffer.
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }



    private void drawGraphs(GL10 gl){

        for(Map.Entry<SensorDataType,ShortBuffer> entry : graphIndex.entrySet()){
            Color color = entry.getKey().getColor();
            gl.glLineWidth(2f);
            gl.glColor4f(color.red, color.green,color.blue,color.alpha);
            gl.glDrawElements(drawMode, entry.getValue().limit(),
                    GL10.GL_UNSIGNED_SHORT, entry.getValue());
        }


    }



}
