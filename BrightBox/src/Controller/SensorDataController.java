/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Models.BrightBox;
import Models.SensorData;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

/**
 *
 * @author techlogic
 */
public class SensorDataController implements Serializable {

    public SensorDataController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SensorData sensorData) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BrightBox fkBrightBox = sensorData.getFkBrightBox();
            if (fkBrightBox != null) {
                fkBrightBox = em.getReference(fkBrightBox.getClass(), fkBrightBox.getId());
                sensorData.setFkBrightBox(fkBrightBox);
            }
            em.persist(sensorData);
            if (fkBrightBox != null) {
                fkBrightBox.getSensorDataCollection().add(sensorData);
                fkBrightBox = em.merge(fkBrightBox);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SensorData sensorData) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SensorData persistentSensorData = em.find(SensorData.class, sensorData.getId());
            BrightBox fkBrightBoxOld = persistentSensorData.getFkBrightBox();
            BrightBox fkBrightBoxNew = sensorData.getFkBrightBox();
            if (fkBrightBoxNew != null) {
                fkBrightBoxNew = em.getReference(fkBrightBoxNew.getClass(), fkBrightBoxNew.getId());
                sensorData.setFkBrightBox(fkBrightBoxNew);
            }
            sensorData = em.merge(sensorData);
            if (fkBrightBoxOld != null && !fkBrightBoxOld.equals(fkBrightBoxNew)) {
                fkBrightBoxOld.getSensorDataCollection().remove(sensorData);
                fkBrightBoxOld = em.merge(fkBrightBoxOld);
            }
            if (fkBrightBoxNew != null && !fkBrightBoxNew.equals(fkBrightBoxOld)) {
                fkBrightBoxNew.getSensorDataCollection().add(sensorData);
                fkBrightBoxNew = em.merge(fkBrightBoxNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sensorData.getId();
                if (findSensorData(id) == null) {
                    throw new NonexistentEntityException("The sensorData with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SensorData sensorData;
            try {
                sensorData = em.getReference(SensorData.class, id);
                sensorData.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sensorData with id " + id + " no longer exists.", enfe);
            }
            BrightBox fkBrightBox = sensorData.getFkBrightBox();
            if (fkBrightBox != null) {
                fkBrightBox.getSensorDataCollection().remove(sensorData);
                fkBrightBox = em.merge(fkBrightBox);
            }
            em.remove(sensorData);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SensorData> findSensorDataEntities() {
        return findSensorDataEntities(true, -1, -1);
    }

    public List<SensorData> findSensorDataEntities(int maxResults, int firstResult) {
        return findSensorDataEntities(false, maxResults, firstResult);
    }

    private List<SensorData> findSensorDataEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SensorData.class));
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

    public SensorData findSensorData(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SensorData.class, id);
        } finally {
            em.close();
        }
    }

    public List<SensorData> findSensorDataForBrightBox(BrightBox box) {
        EntityManager em = getEntityManager();
        Integer boxID = box.getId();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            Metamodel m = em.getMetamodel();
            EntityType<SensorData> entity = m.entity(SensorData.class);
            Root from = cq.from(SensorData.class);
            cq.select(from);
            cq.where(cb.equal(from.get("fkBrightBox"), box));
            cq.orderBy(cb.desc(from.get("timestamp")));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public int getSensorDataCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SensorData> rt = cq.from(SensorData.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
