/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restclient.service;

import java.sql.Date;
import java.sql.Time;
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
import restclient.DailyRecord;
import restclient.Result;
import restclient.Result3;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import restclient.Result2;

/**
 *
 * @author Bryanyhy
 */
@Stateless
@Path("restclient.dailyrecord")
public class DailyRecordFacadeREST extends AbstractFacade<DailyRecord> {

    @PersistenceContext(unitName = "PainDiaryDBPU")
    private EntityManager em;

    public DailyRecordFacadeREST() {
        super(DailyRecord.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(DailyRecord entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, DailyRecord entity) {
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
    public DailyRecord find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<DailyRecord> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<DailyRecord> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("findByRecordId/{recordId}")
    @Produces({"application/json"})
    public List<DailyRecord> findByRecordId(@PathParam("recordId") int recordId) {
    Query query = em.createNamedQuery("DailyRecord.findByRecordId");
    query.setParameter("recordId", recordId);
    return query.getResultList();
    }
    
    @GET
    @Path("findByRecordDate/{recordDate}")
    @Produces({"application/json"})
    public List<DailyRecord> findByRecordDate(@PathParam("recordDate") Date recordDate) {
    Query query = em.createNamedQuery("DailyRecord.findByRecordDate");
    query.setParameter("recordDate", recordDate);
    return query.getResultList();
    }
    
    @GET
    @Path("findByRecordTime/{recordTime}")
    @Produces({"application/json"})
    public List<DailyRecord> findByRecordTime(@PathParam("recordTime") Time recordTime) {
    Query query = em.createNamedQuery("DailyRecord.findByRecordTime");
    query.setParameter("recordTime", recordTime);
    return query.getResultList();
    }
    
    @GET
    @Path("findByPainLevel/{painLevel}")
    @Produces({"application/json"})
    public List<DailyRecord> findByPainLevel(@PathParam("painLevel") int painLevel) {
    Query query = em.createNamedQuery("DailyRecord.findByPainLevel");
    query.setParameter("painLevel", painLevel);
    return query.getResultList();
    }
    
    @GET
    @Path("findByPainLocation/{painLocation}")
    @Produces({"application/json"})
    public List<DailyRecord> findByPainLocation(@PathParam("painLocation") String painLocation) {
    Query query = em.createNamedQuery("DailyRecord.findByPainLocation");
    query.setParameter("painLocation", painLocation);
    return query.getResultList();
    }
    
    @GET
    @Path("findByMoodLevel/{moodLevel}")
    @Produces({"application/json"})
    public List<DailyRecord> findByMoodLevel(@PathParam("moodLevel") String moodLevel) {
    Query query = em.createNamedQuery("DailyRecord.findByMoodLevel");
    query.setParameter("moodLevel", moodLevel);
    return query.getResultList();
    }
    
    @GET
    @Path("findByPainTrigger/{painTrigger}")
    @Produces({"application/json"})
    public List<DailyRecord> findByPainTrigger(@PathParam("painTrigger") String painTrigger) {
    Query query = em.createNamedQuery("DailyRecord.findByPainTrigger");
    query.setParameter("painTrigger", painTrigger);
    return query.getResultList();
    }
    
    @GET
    @Path("findByLatitude/{latitude}")
    @Produces({"application/json"})
    public List<DailyRecord> findByLatitude(@PathParam("latitude") String latitude) {
    Query query = em.createNamedQuery("DailyRecord.findByLatitude");
    query.setParameter("latitude", latitude);
    return query.getResultList();
    }
    
    @GET
    @Path("findByLongitude/{longitude}")
    @Produces({"application/json"})
    public List<DailyRecord> findByLongitude(@PathParam("longitude") String longitude) {
    Query query = em.createNamedQuery("DailyRecord.findByLongitude");
    query.setParameter("longitude", longitude);
    return query.getResultList();
    }
    
    @GET
    @Path("findByTemperature/{temperature}")
    @Produces({"application/json"})
    public List<DailyRecord> findByTemperature(@PathParam("temperature") Double temperature) {
    Query query = em.createNamedQuery("DailyRecord.findByTemperature");
    query.setParameter("temperature", temperature);
    return query.getResultList();
    }
    
    @GET
    @Path("findByHumidity/{humidity}")
    @Produces({"application/json"})
    public List<DailyRecord> findByHumidity(@PathParam("humidity") Double humidity) {
    Query query = em.createNamedQuery("DailyRecord.findByHumidity");
    query.setParameter("humidity", humidity);
    return query.getResultList();
    }
    
    @GET
    @Path("findByWindSpeed/{windSpeed}")
    @Produces({"application/json"})
    public List<DailyRecord> findByWindSpeed(@PathParam("windSpeed") Double windSpeed) {
    Query query = em.createNamedQuery("DailyRecord.findByWindSpeed");
    query.setParameter("windSpeed", windSpeed);
    return query.getResultList();
    }
    
    @GET
    @Path("findByAtmosphericPressure/{atmosphericPressure}")
    @Produces({"application/json"})
    public List<DailyRecord> findByAtmosphericPressure(@PathParam("atmosphericPressure") Double atmosphericPressure) {
    Query query = em.createNamedQuery("DailyRecord.findByAtmosphericPressure");
    query.setParameter("atmosphericPressure", atmosphericPressure);
    return query.getResultList();
    }
      
    @GET
    @Path("findByStartAndEndDateAndWeatherVariable/{startDate}/{endDate}/{attribute}")
    @Produces({"application/json"})
    public List<Result> findByStartAndEndDateAndWeatherVariable(@PathParam("startDate") Date startDate, @PathParam("endDate") Date endDate, @PathParam("attribute") String attribute) {
    String attributeInLowerCase = attribute.toLowerCase();
    switch (attributeInLowerCase) {
        case "windspeed": 
        case "wind speed":  attributeInLowerCase = "windSpeed";
                            break;
        case "atmosphericpressure":
        case "atmospheric pressure":    attributeInLowerCase = "atmosphericPressure";
                                        break;
        default:    break;
    }     
    String sth = "NEW restclient.Result(d.painLevel, d.recordDate, d." + attributeInLowerCase + ")";
    String jpql = "SELECT " + sth + " FROM restclient.DailyRecord d WHERE d.recordDate >= :startDate AND d.recordDate <= :endDate";
    TypedQuery<Result> q = em.createQuery(jpql, Result.class);  
    q.setParameter("startDate", startDate);    
    q.setParameter("endDate", endDate);
    return q.getResultList();
    }
    
    @GET
    @Path("findCorrelationByStartAndEndDateAndWeatherVariable/{startDate}/{endDate}/{attribute}")
    @Produces({"application/json"})
    public String findCorrelationByStartAndEndDateAndWeatherVariable(@PathParam("startDate") Date startDate, @PathParam("endDate") Date endDate, @PathParam("attribute") String attribute) {
    String attributeInLowerCase = attribute.toLowerCase();
    switch (attributeInLowerCase) {
        case "windspeed": 
        case "wind speed":  attributeInLowerCase = "windSpeed";
                            break;
        case "atmosphericpressure":
        case "atmospheric pressure":    attributeInLowerCase = "atmosphericPressure";
                                        break;
        default:    break;
    }     
    String sth = "NEW restclient.Result2(d.painLevel, d." + attributeInLowerCase + ")";
    String jpql = "SELECT " + sth + " FROM restclient.DailyRecord d WHERE d.recordDate >= :startDate AND d.recordDate <= :endDate";
    TypedQuery<Result2> q = em.createQuery(jpql, Result2.class);  
    q.setParameter("startDate", startDate);    
    q.setParameter("endDate", endDate);
    List<Result2> result = q.getResultList();   
    double data[][] = new double [result.size()][];
    for (int i = 0; i < result.size(); i++) {
        data[i] = new double[] {result.get(i).painLevel, result.get(i).weather};
    }      
    RealMatrix m = MatrixUtils.createRealMatrix(data);
    String first = "";
    for (int i = 0; i < m.getColumnDimension(); i++)
        for (int j = 0; j < m.getColumnDimension(); j++) {
            PearsonsCorrelation pc = new PearsonsCorrelation();
            double cor = pc.correlation(m.getColumn(i), m.getColumn(j));
            first += (i + "," + j + "=[" + String.format(".%2f", cor) + "," + "]" + ";   ");
        }
    PearsonsCorrelation pc = new PearsonsCorrelation(m);
    RealMatrix corM = pc.getCorrelationMatrix();
    String second = ("!correlation:" + corM.getEntry(0, 1) + "   ");
    RealMatrix pM = pc.getCorrelationPValues();
    String third = ("!p value:" + pM.getEntry(0, 1));   
    return first + second + third;
    }
    
    @GET
    @Path("findByStartAndEndDate/{startDate}/{endDate}")
    @Produces({"application/json"})
    public List<Result3> findByStartAndEndDate(@PathParam("startDate") Date startDate, @PathParam("endDate") Date endDate) {
    String sth = "NEW restclient.Result3(d.painLocation, COUNT(d))";
    String jpql = "SELECT " + sth + " FROM restclient.DailyRecord d WHERE d.recordDate >= :startDate AND d.recordDate <= :endDate GROUP BY d.painLocation";
    TypedQuery<Result3> q = em.createQuery(jpql, Result3.class);  
    q.setParameter("startDate", startDate);    
    q.setParameter("endDate", endDate);
    return q.getResultList();
    }
    
    @GET
    @Path("q4b/{userId}/{startDate}/{endDate}/{attribute}")
    @Produces({"application/json"})
    public List<Result> q4b(@PathParam("userId") int userId, @PathParam("startDate") Date startDate, @PathParam("endDate") Date endDate, @PathParam("attribute") String attribute) {
    String sth = "NEW restclient.Result(d.painLevel, d.recordDate, d." + attribute + ")";
    String jpql = "SELECT " + sth + " FROM Patient p INNER JOIN p.dailyRecordCollection d WHERE p.userId = :userId AND d.recordDate >= :startDate AND d.recordDate <= :endDate";
    TypedQuery<Result> q = em.createQuery(jpql, Result.class);  
    q.setParameter("userId", userId);  
    q.setParameter("startDate", startDate);    
    q.setParameter("endDate", endDate);
    return q.getResultList();
    }
    
    @GET
    @Path("q4c/{userId}/{startDate}/{endDate}")
    @Produces({"application/json"})
    public List<Result3> q4c(@PathParam("userId") int userId, @PathParam("startDate") Date startDate, @PathParam("endDate") Date endDate) {
    String sth = "NEW restclient.Result3(d.painLocation, COUNT(d))";
    String jpql = "SELECT " + sth + " FROM Patient p INNER JOIN p.dailyRecordCollection d WHERE p.userId = :userId AND d.recordDate >= :startDate AND d.recordDate <= :endDate GROUP BY d.painLocation";
    TypedQuery<Result3> q = em.createQuery(jpql, Result3.class);  
    q.setParameter("userId", userId);  
    q.setParameter("startDate", startDate);    
    q.setParameter("endDate", endDate);
    return q.getResultList();
    }
    
    @GET
    @Path("findByUserId/{userId}")
    @Produces({"application/json"})
    public List<DailyRecord> findByUserId(@PathParam("userId") int userId) {
    Query query = em.createNamedQuery("DailyRecord.findByUserId");
    query.setParameter("userId", userId);
    return query.getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
