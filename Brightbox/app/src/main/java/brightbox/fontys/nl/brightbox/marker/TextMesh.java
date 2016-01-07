package brightbox.fontys.nl.brightbox.marker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.widget.TextView;

import gl.Color;
import gl.scenegraph.MeshComponent;
import gl.textures.TexturedShape;
import util.Log;
import util.Vec;

/**
 * Created by techlogic on 04.01.16.
 */
public class TextMesh {

    private static final String LOG_TAG = "TEXTMESH";


    public static MeshComponent createTextMesh(Context context,String textToDisplay) {
        float textSize = 0.5f;

        TextView v = new TextView(context);
        v.setTypeface(null, Typeface.BOLD);
        // Set textcolor to black:
        v.setTextColor(new Color(1, 1, 1, 1).toIntARGB());
        v.setText(textToDisplay);

        MeshComponent mesh = newTexturedSquare("textBitmap"
                + textToDisplay, util.IO.loadBitmapFromView(v), textSize);
        return mesh;
    }

    private static  MeshComponent newTexturedSquare(String bitmapName, Bitmap bitmap,
                                                          float heightInMeters) {

        if (bitmapName == null) {
            Log.e(LOG_TAG,
                    "No bitmap id set, can't be added to Texture Manager!");
            return null;
        }

        if (bitmap == null) {
            Log.e(LOG_TAG, "Passed bitmap was null!");
            return null;
        }

        TexturedShape s = new TexturedShape(bitmapName, bitmap);
        float f = (float) bitmap.getHeight() / (float) bitmap.getWidth();
        float x = heightInMeters / f;

        float w2 = -x / 2;
        float h2 = -heightInMeters / 2;

        Log.d(LOG_TAG, "Creating textured mesh for " + bitmapName);
        Log.v(LOG_TAG, "   > bitmap.getHeight()=" + bitmap.getHeight());
        Log.v(LOG_TAG, "   > bitmap.getWidth()=" + bitmap.getWidth());
        Log.v(LOG_TAG, "   > height/width factor=" + f);
        Log.v(LOG_TAG, "   > w2=" + w2);
        Log.v(LOG_TAG, "   > h2=" + h2);

        s.add(new Vec(-w2, 0, -h2), 0, 0);
        s.add(new Vec(-w2, 0, h2), 0, 1);
        s.add(new Vec(w2, 0, -h2), 1, 0);

        s.add(new Vec(w2, 0, h2), 1, 1);
        s.add(new Vec(-w2, 0, h2), 0, 1);
        s.add(new Vec(w2, 0, -h2), 1, 0);

        return s;
    }

}
