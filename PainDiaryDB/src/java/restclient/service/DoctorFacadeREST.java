/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restclient.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import restclient.Doctor;

/**
 *
 * @author Bryanyhy
 */
@Stateless
@Path("restclient.doctor")
public class DoctorFacadeREST extends AbstractFacade<Doctor> {

    @PersistenceContext(unitName = "PainDiaryDBPU")
    private EntityManager em;

    public DoctorFacadeREST() {
        super(Doctor.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Doctor entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Doctor entity) {
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
    public Doctor find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Doctor> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Doctor> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("findByDocId/{docId}")
    @Produces({"application/json"})
    public List<Doctor> findByDocId(@PathParam("docId") int docId) {
    Query query = em.createNamedQuery("Doctor.findByDocId");
    query.setParameter("docId", docId);
    return query.getResultList();
    }
    
    @GET
    @Path("findByDocFirstname/{docFirstname}")
    @Produces({"application/json"})
    public List<Doctor> findByDocFirstname(@PathParam("docFirstname") String docFirstname) {
    Query query = em.createNamedQuery("Doctor.findByDocFirstname");
    query.setParameter("docFirstname", docFirstname);
    return query.getResultList();
    }
    
    @GET
    @Path("findByDocSurname/{docSurname}")
    @Produces({"application/json"})
    public List<Doctor> findByDocSurname(@PathParam("docSurname") String docSurname) {
    Query query = em.createNamedQuery("Doctor.findByDocSurname");
    query.setParameter("docSurname", docSurname);
    return query.getResultList();
    }
    
    @GET
    @Path("findByDocPhone/{docPhone}")
    @Produces({"application/json"})
    public List<Doctor> findByDocPhone(@PathParam("docPhone") String docPhone) {
    Query query = em.createNamedQuery("Doctor.findByDocPhone");
    query.setParameter("docPhone", docPhone);
    return query.getResultList();
    }
    
    @GET
    @Path("findByClinicAddress/{clinicAddress}")
    @Produces({"application/json"})
    public List<Doctor> findByClinicAddress(@PathParam("clinicAddress") String clinicAddress) {
    Query query = em.createNamedQuery("Doctor.findByClinicAddress");
    query.setParameter("clinicAddress", clinicAddress);
    return query.getResultList();
    }
    
    @GET
    @Path("findByClinicPhone/{clinicPhone}")
    @Produces({"application/json"})
    public List<Doctor> findByClinicPhone(@PathParam("clinicPhone") String clinicPhone) {
    Query query = em.createNamedQuery("Doctor.findByClinicPhone");
    query.setParameter("clinicPhone", clinicPhone);
    return query.getResultList();
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
