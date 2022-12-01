package ch.makery.address.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class PersonListWrapper {

    //Listado de personas
    private List<Person> persons;

    @XmlElement(name = "person")
    //Creación del método para guardar datos de la clase Person
    public List<Person> getPersons() {
        return persons;
    }

    //Metodo para pasa datos a la clase person.xml
    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
