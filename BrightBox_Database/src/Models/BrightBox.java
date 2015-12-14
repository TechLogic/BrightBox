/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author techlogic
 */
@Entity
@Table(name = "bright_box", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"identifier"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BrightBox.findAll", query = "SELECT b FROM BrightBox b"),
    @NamedQuery(name = "BrightBox.findById", query = "SELECT b FROM BrightBox b WHERE b.id = :id"),
    @NamedQuery(name = "BrightBox.findByName", query = "SELECT b FROM BrightBox b WHERE b.name = :name"),
    @NamedQuery(name = "BrightBox.findByDescription", query = "SELECT b FROM BrightBox b WHERE b.description = :description"),
    @NamedQuery(name = "BrightBox.findByIdentifier", query = "SELECT b FROM BrightBox b WHERE b.identifier = :identifier")})
public class BrightBox implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Column(length = 45)
    private String name;
    @Column(length = 45)
    private String description;
    @Column(length = 45)
    private String identifier;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkBrightBox")
    private Collection<SensorData> sensorDataCollection;

    public BrightBox() {
    }

    public BrightBox(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @XmlTransient
    public Collection<SensorData> getSensorDataCollection() {
        return sensorDataCollection;
    }

    public void setSensorDataCollection(Collection<SensorData> sensorDataCollection) {
        this.sensorDataCollection = sensorDataCollection;
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
        if (!(object instanceof BrightBox)) {
            return false;
        }
        BrightBox other = (BrightBox) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.BrightBox[ id=" + id + " ]";
    }
    
}
