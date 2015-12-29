package brightbox.fontys.nl.brightbox.entities.models;

import gl.Color;

/**
 * Created by techlogic on 29.12.15.
 */
public enum SensorDataType {
   ALL(new Color("#000000"),"  All"), BLOOM(Color.green(),"  Bloom"),VEGA(new Color("#49311C"),"  Vega"),GROW(new Color("#FFFF00"),"  Grow"),AIR_TEMP(new Color("#00FFFF"),"  Air temperature"),HUMIDITY(Color.white(),"  Humidity"),PH_VALUE(new Color("#800080"),"  PH Value"),EC_VALUE(new Color("#FFC0CB"),"  EC Value"),WATER_TEMP(Color.blue(),"  Water temperatur");


    private Color color;
    private String name;

     SensorDataType(Color color,String name){
        this.color = color;
         this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}
