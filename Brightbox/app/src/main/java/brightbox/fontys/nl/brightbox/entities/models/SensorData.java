/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brightbox.fontys.nl.brightbox.entities.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author techlogic
 */

public class SensorData {

    private Integer id;

    private Timestamp timestamp;
    private Double bloom;
    private Double vega;
    private Double grow;

    private Double airTemperatur;
    private Double humidity;
    private Double phValue;
    private Double ecValue;
    private Double waterTemperatur;
    private BrightBox fkBrightBox;

    public SensorData() {
    }

    public SensorData(Integer id) {
        this.id = id;
    }

    public SensorData(Integer id,  Timestamp timestamp, Double bloom, Double vega, Double grow, Double airTemperatur, Double humidity, Double phValue, Double ecValue, Double waterTemperatur) {
        this.id = id;
        this.timestamp = timestamp;
        this.bloom = bloom;
        this.vega = vega;
        this.grow = grow;
        this.airTemperatur = airTemperatur;
        this.humidity = humidity;
        this.phValue = phValue;
        this.ecValue = ecValue;
        this.waterTemperatur = waterTemperatur;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getAirTemperatur() {
        return airTemperatur;
    }

    public void setAirTemperatur(Double airTemperatur) {
        this.airTemperatur = airTemperatur;
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

    public Double getWaterTemperatur() {
        return waterTemperatur;
    }

    public void setWaterTemperatur(Double waterTemperatur) {
        this.waterTemperatur = waterTemperatur;
    }

    public BrightBox getFkBrightBox() {
        return fkBrightBox;
    }

    public void setFkBrightBox(BrightBox fkBrightBox) {
        this.fkBrightBox = fkBrightBox;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SensorData)) {
            return false;
        }
        SensorData other = (SensorData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.SensorData[ id=" + id + " ]";
    }
    
}
