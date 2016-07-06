package com.marcin.dao;

/**
 * Created by marcin on 06.07.16.
 */
import com.marcin.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PersonService {

    @Autowired
    private PersonDao personDao;

    public PersonDao getPersonDao() {
        return personDao;
    }

//    @Autowired
//    public void setPersonDao(PersonDao personDao) {
//        this.personDao = personDao;
//    }

    public void addPerson(Person person) {
        getPersonDao().insert(person);
    }
}