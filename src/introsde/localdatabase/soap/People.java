package introsde.localdatabase.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import introsde.localdatabase.model.Measure;
import introsde.localdatabase.model.Person;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) //optional
public interface People {
    @WebMethod(operationName="readPerson")
    @WebResult(name="person") 
    public Person readPerson(@WebParam(name="personId") Long id);
 
    @WebMethod(operationName="getPeopleList")
    @WebResult(name="people")
    public List<Person> getPeople();
 
    @WebMethod(operationName="createPerson")
    @WebResult(name="person") 
    public Person addPerson(@WebParam(name="person") Person person);
 
    @WebMethod(operationName="updatePerson")
    @WebResult(name="person") 
    public Person updatePerson(@WebParam(name="person") Person person);
    
    @WebMethod(operationName="deletePerson")
    @WebResult(name="success") 
    public int deletePerson(@WebParam(name="personId") Long id);
    
    @WebMethod(operationName="readPersonHistory")
    @WebResult(name="measure") 
    public List<Measure> readPersonHistory(@WebParam(name="personId") Long id, @WebParam(name="measureType") String measureType);
    
    @WebMethod(operationName="readMeasureTypes")
    @WebResult(name="measure") 
    public List<Measure> readMeasureTypes();
    
    @WebMethod(operationName="readPersonMeasure")
    @WebResult(name="measure") 
    public Measure readPersonMeasure(@WebParam(name="personId") Long id, @WebParam(name="measureType") String measureType, @WebParam(name="measureId") Long mid);
    
    @WebMethod(operationName="savePersonMeasure")
    @WebResult(name="measure") 
    public Measure savePersonMeasure(@WebParam(name="personId") Long id, @WebParam(name="measure") Measure measure);
   
    @WebMethod(operationName="updatePersonMeasure")
    @WebResult(name="measure") 
    public Measure updatePersonMeasure(@WebParam(name="personId") Long id, @WebParam(name="measure") Measure measure);
}