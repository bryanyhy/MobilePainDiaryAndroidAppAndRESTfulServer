/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restclient.service;

import java.sql.Timestamp;
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
import restclient.Registration;

/**
 *
 * @author Bryanyhy
 */
@Stateless
@Path("restclient.registration")
public class RegistrationFacadeREST extends AbstractFacade<Registration> {

    @PersistenceContext(unitName = "PainDiaryDBPU")
    private EntityManager em;

    public RegistrationFacadeREST() {
        super(Registration.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Registration entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Registration entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Registration find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Registration> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Registration> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("findByUsername/{username}")
    @Produces({"application/json"})
    public List<Registration> findByUsername(@PathParam("username") String username) {
    Query query = em.createNamedQuery("Registration.findByUsername");
    query.setParameter("username", username);
    return query.getResultList();
    }
    
    @GET
    @Path("findByPassword/{password}")
    @Produces({"application/json"})
    public List<Registration> findByPassword(@PathParam("password") String password) {
    Query query = em.createNamedQuery("Registration.findByPassword");
    query.setParameter("password", password);
    return query.getResultList();
    }
    
    @GET
    @Path("findByRegDatetime/{regDatetime}")
    @Produces({"application/json"})
    public List<Registration> findByRegDatetime(@PathParam("regDatetime") Timestamp regDatetime) {
    Query query = em.createNamedQuery("Registration.findByRegDatetime");
    query.setParameter("regDatetime", regDatetime);
    return query.getResultList();
    }
    
    @GET
    @Path("findByRegId/{regId}")
    @Produces({"application/json"})
    public List<Registration> findByUserId(@PathParam("regId") int regId) {
    Query query = em.createNamedQuery("Registration.findByUserId");
    query.setParameter("regId", regId);
    return query.getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
