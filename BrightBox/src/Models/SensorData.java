/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author techlogic
 */
@Entity
@Table(name = "sensor_data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SensorData.findAll", query = "SELECT s FROM SensorData s"),
    @NamedQuery(name = "SensorData.findById", query = "SELECT s FROM SensorData s WHERE s.id = :id"),
    @NamedQuery(name = "SensorData.findByTimestamp", query = "SELECT s FROM SensorData s WHERE s.timestamp = :timestamp"),
    @NamedQuery(name = "SensorData.findByBloom", query = "SELECT s FROM SensorData s WHERE s.bloom = :bloom"),
    @NamedQuery(name = "SensorData.findByVega", query = "SELECT s FROM SensorData s WHERE s.vega = :vega"),
    @NamedQuery(name = "SensorData.findByGrow", query = "SELECT s FROM SensorData s WHERE s.grow = :grow"),
    @NamedQuery(name = "SensorData.findByAirTemperatur", query = "SELECT s FROM SensorData s WHERE s.airTemperatur = :airTemperatur"),
    @NamedQuery(name = "SensorData.findByHumidity", query = "SELECT s FROM SensorData s WHERE s.humidity = :humidity"),
    @NamedQuery(name = "SensorData.findByPhValue", query = "SELECT s FROM SensorData s WHERE s.phValue = :phValue"),
    @NamedQuery(name = "SensorData.findByEcValue", query = "SELECT s FROM SensorData s WHERE s.ecValue = :ecValue"),
    @NamedQuery(name = "SensorData.findByWaterTemperatur", query = "SELECT s FROM SensorData s WHERE s.waterTemperatur = :waterTemperatur")})
public class SensorData implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal bloom;
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal vega;
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal grow;
    @Basic(optional = false)
    @Column(name = "air_temperatur", nullable = false, precision = 5, scale = 2)
    private BigDecimal airTemperatur;
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal humidity;
    @Basic(optional = false)
    @Column(name = "ph_value", nullable = false, precision = 5, scale = 2)
    private BigDecimal phValue;
    @Basic(optional = false)
    @Column(name = "ec_value", nullable = false, precision = 5, scale = 2)
    private BigDecimal ecValue;
    @Basic(optional = false)
    @Column(name = "water_temperatur", nullable = false, precision = 5, scale = 2)
    private BigDecimal waterTemperatur;
    @JoinColumn(name = "fk_bright_box", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
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
