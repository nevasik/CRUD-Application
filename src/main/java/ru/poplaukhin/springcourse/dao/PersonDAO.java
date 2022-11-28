package ru.poplaukhin.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.poplaukhin.springcourse.models.Person;

import java.util.ArrayList;
import java.util.List;

// Этот класс пока что заменяет СУБД, но потом мы создадим СУБД
@Component
public class PersonDAO {
    private final List<Person> people;
    private static int PEOPLE_COUNT;

    {
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT, "Tom", 24, "tom@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Bob", 52, "bob@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Mike", 21, "mike@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Katy", 35, "katy@mail.ru"));
    }
    public List<Person> index() { // вывод всей коллекции людей
        return people;
    }
    public Person show(int id) { // вытягиваем человека по его id
        for (Person person : people) {
            if (id == person.getId()) {
                return person;
            }
        }
        return null;
//        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }
    public void save(Person person) { // сохранение человека
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }
    public void update(int id, Person updatePerson) { // обновление(перезапись) человека
        Person personToBeUpdated = show(id);
        personToBeUpdated.setName(updatePerson.getName());
        personToBeUpdated.setAge(updatePerson.getAge());
        personToBeUpdated.setEmail(updatePerson.getEmail());
    }
    public void delete(int id) { // удаление
        people.removeIf(p -> p.getId() == id);
    }
}
