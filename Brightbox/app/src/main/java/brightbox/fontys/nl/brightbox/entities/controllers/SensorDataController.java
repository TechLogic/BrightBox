/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.brightbox.entities.controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.SQLException;

import nl.fontys.brightbox.entities.models.SensorData;

/**
 *
 * @author techlogic
 */

public class SensorDataController{

    private final Dao<SensorData,?> dao;

    public SensorDataController(ConnectionSource source) throws SQLException {
        this.dao = DaoManager.createDao(source, SensorData.class);
    }
}
