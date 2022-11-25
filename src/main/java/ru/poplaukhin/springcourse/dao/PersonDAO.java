package ru.poplaukhin.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.poplaukhin.springcourse.models.Person;

import java.util.ArrayList;
import java.util.List;

// Этот класс пока что заменяет СУБД, но потом мы создадим СУБД
@Component
public class PersonDAO {
    private List<Person> people;
    private static int PEOPLE_COUNT;

    {
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT, "Tom"));
        people.add(new Person(++PEOPLE_COUNT, "Bob"));
        people.add(new Person(++PEOPLE_COUNT, "Mike"));
        people.add(new Person(++PEOPLE_COUNT, "Katy"));
    }

    public List<Person> index() { // вывод всех людей
        return people;
    }

    public Person show(int id) {
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }
}
