/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.brightbox.entities.models;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author techlogic
 */

public class SensorData {

    private Integer id;

    private Date timestamp;
    private BigDecimal bloom;
    private BigDecimal vega;
    private BigDecimal grow;

    private BigDecimal airTemperatur;
    private BigDecimal humidity;
    private BigDecimal phValue;
    private BigDecimal ecValue;
    private BigDecimal waterTemperatur;
    private BrightBox fkBrightBox;

    public SensorData() {
    }

    public SensorData(Integer id) {
        this.id = id;
    }

    public SensorData(Integer id, Date timestamp, BigDecimal bloom, BigDecimal vega, BigDecimal grow, BigDecimal airTemperatur, BigDecimal humidity, BigDecimal phValue, BigDecimal ecValue, BigDecimal waterTemperatur) {
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getBloom() {
        return bloom;
    }

    public void setBloom(BigDecimal bloom) {
        this.bloom = bloom;
    }

    public BigDecimal getVega() {
        return vega;
    }

    public void setVega(BigDecimal vega) {
        this.vega = vega;
    }

    public BigDecimal getGrow() {
        return grow;
    }

    public void setGrow(BigDecimal grow) {
        this.grow = grow;
    }

    public BigDecimal getAirTemperatur() {
        return airTemperatur;
    }

    public void setAirTemperatur(BigDecimal airTemperatur) {
        this.airTemperatur = airTemperatur;
    }

    public BigDecimal getHumidity() {
        return humidity;
    }

    public void setHumidity(BigDecimal humidity) {
        this.humidity = humidity;
    }

    public BigDecimal getPhValue() {
        return phValue;
    }

    public void setPhValue(BigDecimal phValue) {
        this.phValue = phValue;
    }

    public BigDecimal getEcValue() {
        return ecValue;
    }

    public void setEcValue(BigDecimal ecValue) {
        this.ecValue = ecValue;
    }

    public BigDecimal getWaterTemperatur() {
        return waterTemperatur;
    }

    public void setWaterTemperatur(BigDecimal waterTemperatur) {
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
