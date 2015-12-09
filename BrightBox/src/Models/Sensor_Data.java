package Models;

import java.sql.Timestamp;

/**
 *
 * @author Christian Neumann
 */
public class Sensor_Data {
    private int id;
    private BrightBox brightbox;
    private Timestamp timestamp;
    private Double bloom;
    private Double vega;
    private Double grow;
    private Double airtemperatur;
    private Double humidity;
    private Double phValue;
    private Double ecValue;
    private Double watertemperatur;

    public Sensor_Data() {
    }

    public Sensor_Data(int id, BrightBox brightbox) {
        this.id = id;
        this.brightbox = brightbox;
    }

    public Sensor_Data(int id, BrightBox brightbox, Timestamp timestamp, Double bloom, Double vega, Double grow, Double airtemperatur, Double humidity, Double phValue, Double ecValue, Double watertemperatur) {
        this.id = id;
        this.brightbox = brightbox;
        this.timestamp = timestamp;
        this.bloom = bloom;
        this.vega = vega;
        this.grow = grow;
        this.airtemperatur = airtemperatur;
        this.humidity = humidity;
        this.phValue = phValue;
        this.ecValue = ecValue;
        this.watertemperatur = watertemperatur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BrightBox getBrightbox() {
        return brightbox;
    }

    public void setBrightbox(BrightBox brightbox) {
        this.brightbox = brightbox;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Double getBloom() {
        return bloom;
    }

    public void setBloom(Double bloom) {
        this.bloom = bloom;
    }

    public Double getVega() {
        return vega;
    }

    public void setVega(Double vega) {
        this.vega = vega;
    }

    public Double getGrow() {
        return grow;
    }

    public void setGrow(Double grow) {
        this.grow = grow;
    }

    public Double getAirtemperatur() {
        return airtemperatur;
    }

    public void setAirtemperatur(Double airtemperatur) {
        this.airtemperatur = airtemperatur;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getPhValue() {
        return phValue;
    }

    public void setPhValue(Double phValue) {
        this.phValue = phValue;
    }

    public Double getEcValue() {
        return ecValue;
    }

    public void setEcValue(Double ecValue) {
        this.ecValue = ecValue;
    }

    public Double getWatertemperatur() {
        return watertemperatur;
    }

    public void setWatertemperatur(Double watertemperatur) {
        this.watertemperatur = watertemperatur;
    }
    
    
    
}
