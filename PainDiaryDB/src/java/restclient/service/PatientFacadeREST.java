/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restclient.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import restclient.Patient;

/**
 *
 * @author Bryanyhy
 */
@Stateless
@Path("restclient.patient")
public class PatientFacadeREST extends AbstractFacade<Patient> {

    @PersistenceContext(unitName = "PainDiaryDBPU")
    private EntityManager em;

    public PatientFacadeREST() {
        super(Patient.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Patient entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Patient entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Patient find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Patient> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Patient> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("findByUserId/{userId}")
    @Produces({"application/json"})
    public List<Patient> findByUserId(@PathParam("userId") int userId) {
    Query query = em.createNamedQuery("Patient.findByUserId");
    query.setParameter("userId", userId);
    return query.getResultList();
    }
    
    @GET
    @Path("findByUserFirstname/{userFirstname}")
    @Produces({"application/json"})
    public List<Patient> findByUserFirstname(@PathParam("userFirstname") String userFirstname) {
    Query query = em.createNamedQuery("Patient.findByUserFirstname");
    query.setParameter("userFirstname", userFirstname);
    return query.getResultList();
    }
    
    @GET
    @Path("findByUserSurname/{userSurname}")
    @Produces({"application/json"})
    public List<Patient> findByUserSurname(@PathParam("userSurname") String userSurname) {
    Query query = em.createNamedQuery("Patient.findByUserSurname");
    query.setParameter("userSurname", userSurname);
    return query.getResultList();
    }
       
    @GET
    @Path("findByDob/{dob}")
    @Produces({"application/json"})
    public List<Patient> findByDob(@PathParam("dob") Date dob) {
    Query query = em.createNamedQuery("Patient.findByDob");
    query.setParameter("dob", dob);
    return query.getResultList();
    }
    
    @GET
    @Path("findByHeight/{height}")
    @Produces({"application/json"})
    public List<Patient> findByHeight(@PathParam("height") Double height) {
    Query query = em.createNamedQuery("Patient.findByHeight");
    query.setParameter("height", height);
    return query.getResultList();
    }
    
    @GET
    @Path("findByWeight/{weight}")
    @Produces({"application/json"})
    public List<Patient> findByWeight(@PathParam("weight") Double weight) {
    Query query = em.createNamedQuery("Patient.findByWeight");
    query.setParameter("weight", weight);
    return query.getResultList();
    }    
    
    @GET
    @Path("findByGender/{gender}")
    @Produces({"application/json"})
    public List<Patient> findByGender(@PathParam("gender") String gender) {
    Query query = em.createNamedQuery("Patient.findByGender");
    query.setParameter("gender", gender);
    return query.getResultList();
    }
    
    @GET
    @Path("findByOccupation/{occupation}")
    @Produces({"application/json"})
    public List<Patient> findByOccupation(@PathParam("occupation") String occupation) {
    Query query = em.createNamedQuery("Patient.findByOccupation");
    query.setParameter("occupation", occupation);
    return query.getResultList();
    }
    
    @GET
    @Path("findByUserAddress/{userAddress}")
    @Produces({"application/json"})
    public List<Patient> findByUserAddress(@PathParam("userAddress") String userAddress) {
    Query query = em.createNamedQuery("Patient.findByUserAddress");
    query.setParameter("userAddress", userAddress);
    return query.getResultList();
    }
    
    @GET
    @Path("findByDobAndGender/{dob}/{gender}")
    @Produces({"application/json"})
    public List<Patient> findByDobAndGender(@PathParam("dob") Date dob, @PathParam("gender") String gender) {
    TypedQuery<Patient> q = em.createQuery("SELECT p FROM Patient p WHERE p.dob >= :dob AND UPPER(p.gender) = UPPER(:gender)", Patient.class);        
    q.setParameter("dob", dob);    
    q.setParameter("gender", gender);
    return q.getResultList();
    }
    
    @GET
    @Path("findByFNameAndSNameAndHeightAndWeight/{userFirstname}/{userSurname}/{height}/{weight}")
    @Produces({"application/json"})
    public List<Patient> findByFNameAndSNameAndHeightAndWeight(@PathParam("userFirstname") String userFirstname, @PathParam("userSurname") String userSurname, @PathParam("height") Double height, @PathParam("weight") Double weight) {
    TypedQuery<Patient> q = em.createQuery("SELECT p FROM Patient p WHERE UPPER(p.userFirstname) = UPPER(:userFirstname) AND UPPER(p.userSurname) = UPPER(:userSurname) AND p.height = :height AND p.weight = :weight", Patient.class);        
    q.setParameter("userFirstname", userFirstname);    
    q.setParameter("userSurname", userSurname);
    q.setParameter("height", height);    
    q.setParameter("weight", weight);
    return q.getResultList();
    }
    
    @GET
    @Path("findByDocFirstAndSurname/{docFirstname}/{docSurname}")
    @Produces({"application/json"})
    public List<Patient> findByDocFirstAndSurname(@PathParam("docFirstname") String docFirstname, @PathParam("docSurname") String docSurname) {
    TypedQuery<Patient> q = em.createQuery("SELECT p FROM Patient p WHERE UPPER(p.docId.docFirstname) = UPPER(:docFirstname) AND UPPER(p.docId.docSurname) = UPPER(:docSurname)", Patient.class);        
    q.setParameter("docFirstname", docFirstname);    
    q.setParameter("docSurname", docSurname);
    return q.getResultList();
    }
    
    @GET
    @Path("findByPainLevel/{painLevel}")
    @Produces({"application/json"})
    public List<Patient> findByPainLevel(@PathParam("painLevel") int painLevel) {
    Query query = em.createNamedQuery("Patient.findByPainLevel");
    query.setParameter("painLevel", painLevel);
    return query.getResultList();
    }



    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
