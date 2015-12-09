
import Controller.BrightBoxController;
import Controller.SensorDataController;
import Models.BrightBox;
import Models.SensorData;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author techlogic
 */
public class main {

    @PersistenceContext
    static EntityManager em;

    public static void main(String[] args) {
        EntityManagerFactory entiyManagerFactory = Persistence.createEntityManagerFactory("BrightBoxPU");
        BrightBoxController brightBoxController = new BrightBoxController(entiyManagerFactory);
        SensorDataController sensorDataController = new SensorDataController(entiyManagerFactory);
        List<BrightBox> brightboxList = brightBoxController.findBrightBoxEntities();
        brightboxList.stream().forEach((b) -> {
            System.out.println(b.toString());
            List<SensorData> sensorData = sensorDataController.findSensorDataForBrightBox(b);
            sensorData.stream().forEach((s) -> {
                System.out.println(s);
            });
        });
    }

}
