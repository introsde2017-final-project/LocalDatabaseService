package introsde.localdatabase.model;

import introsde.localdatabase.dao.LifeCoachDao;
import introsde.localdatabase.model.Person;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * The persistent class for the "Measure" database table.
 * 
 */
@Entity
@Table(name="\"Measure\"")
@NamedQuery(name="Measure.findAll", query="SELECT m FROM Measure m")
@XmlRootElement
public class Measure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_measure")
	@TableGenerator(name="sqlite_measure", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="Measure")
	@Column(name="\"idMeasure\"")
	private Long idMeasure;

	@Temporal(TemporalType.DATE)
	@Column(name="\"dateRegistered\"")
	private Date dateRegistered;

	@Column(name="\"measureType\"")
	private String measureType;

	@Column(name="\"measureValue\"")
	private String measureValue;
	
	@Column(name="\"measureValueType\"")
	private String measureValueType;
	
	@ManyToOne
	@JoinColumn(name="idPerson",referencedColumnName="idPerson")
	private Person person;

	public Measure() {
	}

	public Long getIdMeasure() {
		return this.idMeasure;
	}

	public void setIdMeasure(Long idMeasure) {
		this.idMeasure = idMeasure;
	}

	public String getDateRegistered() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        // Get the date today using Calendar object.
        if (dateRegistered == null) {
            return null;
        }
        return df.format(dateRegistered);
    }


	public void setDateRegistered(String dateRegistered) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date date = format.parse(dateRegistered);
        this.dateRegistered = date;
    }
	
	public String getMeasureType() {
		return this.measureType;
	}

	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}

	public String getMeasureValue() {
		return this.measureValue;
	}

	public void setMeasureValue(String measureValue) {
		this.measureValue = measureValue;
	}

	public String getMeasureValueType() {
		return this.measureValueType;
	}

	public void setMeasureValueType(String measureValueType) {
		this.measureValueType = measureValueType;
	}
	
	// we make this transient for JAXB to avoid and infinite loop on serialization
	@XmlTransient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	

	// database operations
	public static List<Measure> getCurrentMeasuresById(Long personId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		
		List<Measure> list = em.createQuery(
                "SELECT m FROM Measure m WHERE m.person.idPerson = :id AND m.dateRegistered =  " +
                		"(SELECT MAX(m1.dateRegistered) FROM Measure m1 WHERE m1.person.idPerson = :id " +
                		"AND m1.measureType = m.measureType) GROUP BY m.measureType HAVING m.idMeasure = MAX(m.idMeasure)",
                Measure.class)
                .setParameter("id", personId)
                .getResultList();

		LifeCoachDao.instance.closeConnections(em);
		return list;
	}
	
	public static Measure getMeasureById(Long measureId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		Measure p = em.find(Measure.class, measureId);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<Measure> getMeasureByIdAndType(Long personId, String measureType) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		
		List<Measure> list = em.createQuery(
                "SELECT m FROM Measure m WHERE m.person.idPerson = :id AND m.measureType = :measureType",
                Measure.class)
                .setParameter("id", personId)
                .setParameter("measureType", measureType)
                .getResultList();

		LifeCoachDao.instance.closeConnections(em);
		return list;
	}
	
	public static Measure getMeasureByIdTypeAndMid(Long personId, String measureType, Long mid) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		
		Measure m = em.createQuery(
                "SELECT m FROM Measure m WHERE m.idMeasure = :mid AND m.person.idPerson = :id AND m.measureType = :measureType",
                Measure.class)
				.setParameter("mid", mid)
                .setParameter("id", personId)
                .setParameter("measureType", measureType)
                .getSingleResult();

		LifeCoachDao.instance.closeConnections(em);
		return m;
	}
	
	public static List<Measure> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<Measure> list = em.createNamedQuery("Measure.findAll", Measure.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static Measure saveMeasure(Measure m) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(m);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return m;
	}
	
	public static Measure updateMeasure(Measure m) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		m=em.merge(m);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return m;
	}
	
	public static void removeMeasure(Measure p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}
