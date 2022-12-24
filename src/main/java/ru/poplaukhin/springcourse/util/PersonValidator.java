package ru.poplaukhin.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.poplaukhin.springcourse.dao.PersonDAO;
import ru.poplaukhin.springcourse.models.Person;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass); // приравниваем только класс Person к валидации
    }
    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (personDAO.show(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "This email is already taken"); // первое это на каком поле произошла ошибка, второй это код ошибки
        }
        // нужно посмотреть есть ли такой же email в базе данных
    }
}
