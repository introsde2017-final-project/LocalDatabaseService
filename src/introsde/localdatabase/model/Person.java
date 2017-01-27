package introsde.localdatabase.model;

import introsde.localdatabase.dao.LifeCoachDao;
import introsde.localdatabase.model.Measure;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * The persistent class for the "Person" database table.
 * 
 */
@Entity
@Table(name="\"Person\"")
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
@XmlRootElement
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// For sqlite in particular, you need to use the following @GeneratedValue annotation
	// This holds also for the other tables
	// SQLITE implements auto increment ids through named sequences that are stored in a 
	// special table named "sqlite_sequence"
	@GeneratedValue(generator="sqlite_person")
	@TableGenerator(name="sqlite_person", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="Person")
	@Column(name="\"idPerson\"")
	private Long idPerson;

	@Column(name="\"firstname\"")
	private String firstname;
	
	@Column(name="\"lastname\"")
	private String lastname;
	
	@Temporal(TemporalType.DATE)
	@Column(name="\"birthdate\"")
	private Date birthdate;
	
	@Column(name="\"email\"")
	private String email;

	// mappedBy must be equal to the name of the attribute in LifeStatus that maps this relation
	@OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<Measure> currentHealth;
	
	@OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<Measure> healthHistory;
	
	public Person() {
	}
	
	public String getBirthdate() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        // Get the date today using Calendar object.
        if (birthdate == null) {
            return null;
        }
        return df.format(birthdate);
    }


	public void setBirthdate(String bd) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date date = format.parse(bd);
        this.birthdate = date;
    }

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getIdPerson() {
		return this.idPerson;
	}

	public void setIdPerson(Long idPerson) {
		this.idPerson = idPerson;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	// the XmlElementWrapper defines the name of node in which the list of LifeStatus elements
	// will be inserted
	@XmlElementWrapper(name = "currentHealth")
	@XmlElement(name = "measure")
	public List<Measure> getCurrentHealth() {
	    return currentHealth;
	}

	public void setCurrentHealth(List<Measure> currentHealth) {
	    this.currentHealth = currentHealth;
	}
	
	@XmlTransient
	public List<Measure> getHealthHistory() {
	    return healthHistory;
	}

	public void setHealthHistory(List<Measure> healthHistory) {
	    this.healthHistory = healthHistory;
	}
	
    @Override
    public String toString() {
        return "Name: '" + this.firstname + " " + this.lastname + "', Birthday: '" + this.birthdate + "'";
    }
    
	// Database operations
	// Notice that, for this example, we create and destroy and entityManager on each operation. 
	// How would you change the DAO to not having to create the entity manager every time? 
	public static Person getPersonById(Long personId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		Person p = em.find(Person.class, personId);
		LifeCoachDao.instance.closeConnections(em);
		p.setCurrentHealth(Measure.getCurrentMeasuresById(personId));
		return p;
	}
	
	public static List<Person> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<Person> list = em.createNamedQuery("Person.findAll", Person.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    for(Person p : list) {
			p.setCurrentHealth(Measure.getCurrentMeasuresById(p.getIdPerson()));
	    }
	    return list;
	}
	
	public static Person savePerson(Person p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    
	    //after creating the person, set the person id in the Measure row
        List<Measure> list = p.getCurrentHealth();
        if(list != null) {

        	for (Measure m : list) {
            	System.out.println("The id is " + p.getIdPerson());

                m.setPerson(p);
                Measure.updateMeasure(m);
            }
        }
	    return p;
	}
	
	public static Person updatePerson(Person p) {
		p.setCurrentHealth(Measure.getCurrentMeasuresById(p.getIdPerson()));
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removePerson(Person p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}
