/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Models.BrightBox;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Models.SensorData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author techlogic
 */
public class BrightBoxController implements Serializable {

    public BrightBoxController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BrightBox brightBox) {
        if (brightBox.getSensorDataCollection() == null) {
            brightBox.setSensorDataCollection(new ArrayList<SensorData>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<SensorData> attachedSensorDataCollection = new ArrayList<SensorData>();
            for (SensorData sensorDataCollectionSensorDataToAttach : brightBox.getSensorDataCollection()) {
                sensorDataCollectionSensorDataToAttach = em.getReference(sensorDataCollectionSensorDataToAttach.getClass(), sensorDataCollectionSensorDataToAttach.getId());
                attachedSensorDataCollection.add(sensorDataCollectionSensorDataToAttach);
            }
            brightBox.setSensorDataCollection(attachedSensorDataCollection);
            em.persist(brightBox);
            for (SensorData sensorDataCollectionSensorData : brightBox.getSensorDataCollection()) {
                BrightBox oldFkBrightBoxOfSensorDataCollectionSensorData = sensorDataCollectionSensorData.getFkBrightBox();
                sensorDataCollectionSensorData.setFkBrightBox(brightBox);
                sensorDataCollectionSensorData = em.merge(sensorDataCollectionSensorData);
                if (oldFkBrightBoxOfSensorDataCollectionSensorData != null) {
                    oldFkBrightBoxOfSensorDataCollectionSensorData.getSensorDataCollection().remove(sensorDataCollectionSensorData);
                    oldFkBrightBoxOfSensorDataCollectionSensorData = em.merge(oldFkBrightBoxOfSensorDataCollectionSensorData);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BrightBox brightBox) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BrightBox persistentBrightBox = em.find(BrightBox.class, brightBox.getId());
            Collection<SensorData> sensorDataCollectionOld = persistentBrightBox.getSensorDataCollection();
            Collection<SensorData> sensorDataCollectionNew = brightBox.getSensorDataCollection();
            List<String> illegalOrphanMessages = null;
            for (SensorData sensorDataCollectionOldSensorData : sensorDataCollectionOld) {
                if (!sensorDataCollectionNew.contains(sensorDataCollectionOldSensorData)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SensorData " + sensorDataCollectionOldSensorData + " since its fkBrightBox field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<SensorData> attachedSensorDataCollectionNew = new ArrayList<SensorData>();
            for (SensorData sensorDataCollectionNewSensorDataToAttach : sensorDataCollectionNew) {
                sensorDataCollectionNewSensorDataToAttach = em.getReference(sensorDataCollectionNewSensorDataToAttach.getClass(), sensorDataCollectionNewSensorDataToAttach.getId());
                attachedSensorDataCollectionNew.add(sensorDataCollectionNewSensorDataToAttach);
            }
            sensorDataCollectionNew = attachedSensorDataCollectionNew;
            brightBox.setSensorDataCollection(sensorDataCollectionNew);
            brightBox = em.merge(brightBox);
            for (SensorData sensorDataCollectionNewSensorData : sensorDataCollectionNew) {
                if (!sensorDataCollectionOld.contains(sensorDataCollectionNewSensorData)) {
                    BrightBox oldFkBrightBoxOfSensorDataCollectionNewSensorData = sensorDataCollectionNewSensorData.getFkBrightBox();
                    sensorDataCollectionNewSensorData.setFkBrightBox(brightBox);
                    sensorDataCollectionNewSensorData = em.merge(sensorDataCollectionNewSensorData);
                    if (oldFkBrightBoxOfSensorDataCollectionNewSensorData != null && !oldFkBrightBoxOfSensorDataCollectionNewSensorData.equals(brightBox)) {
                        oldFkBrightBoxOfSensorDataCollectionNewSensorData.getSensorDataCollection().remove(sensorDataCollectionNewSensorData);
                        oldFkBrightBoxOfSensorDataCollectionNewSensorData = em.merge(oldFkBrightBoxOfSensorDataCollectionNewSensorData);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = brightBox.getId();
                if (findBrightBox(id) == null) {
                    throw new NonexistentEntityException("The brightBox with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BrightBox brightBox;
            try {
                brightBox = em.getReference(BrightBox.class, id);
                brightBox.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The brightBox with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<SensorData> sensorDataCollectionOrphanCheck = brightBox.getSensorDataCollection();
            for (SensorData sensorDataCollectionOrphanCheckSensorData : sensorDataCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This BrightBox (" + brightBox + ") cannot be destroyed since the SensorData " + sensorDataCollectionOrphanCheckSensorData + " in its sensorDataCollection field has a non-nullable fkBrightBox field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(brightBox);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BrightBox> findBrightBoxEntities() {
        return findBrightBoxEntities(true, -1, -1);
    }

    public List<BrightBox> findBrightBoxEntities(int maxResults, int firstResult) {
        return findBrightBoxEntities(false, maxResults, firstResult);
    }

    private List<BrightBox> findBrightBoxEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BrightBox.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public BrightBox findBrightBox(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BrightBox.class, id);
        } finally {
            em.close();
        }
    }

    public int getBrightBoxCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BrightBox> rt = cq.from(BrightBox.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
