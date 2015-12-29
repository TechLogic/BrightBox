package brightbox.fontys.nl.brightbox.entities.models;

import gl.Color;

/**
 * Created by techlogic on 29.12.15.
 */
public enum SensorDataType {
   ALL(new Color("#000000")), BLOOM(Color.green()),VEGA(new Color("#49311C")),GROW(new Color("#FFFF00")),AIR_TEMP(new Color("#00FFFF")),HUMIDITY(Color.white()),PH_VALUE(new Color("#800080")),EC_VALUE(new Color("#FFC0CB")),WATER_TEMP(Color.blue());


    private Color color;


    private SensorDataType(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

}
