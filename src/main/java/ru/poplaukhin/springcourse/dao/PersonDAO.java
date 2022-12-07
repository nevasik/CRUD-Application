package ru.poplaukhin.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.poplaukhin.springcourse.models.Person;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() { // вывод всей коллекции людей
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }
    public Person show(int id) { // вытягиваем человека по его id
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null); // сделали в этой строке условие, что если человек не найден, будет null
    }
    public void save(Person person) { // сохранение человека
        jdbcTemplate.update("INSERT INTO Person VALUES(1, ?, ?, ?)", person.getName(), person.getAge(),
                person.getEmail());
    }
    public void update(int id, Person updatePerson) { // обновление(перезапись) человека
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?",
                updatePerson.getName(), updatePerson.getAge(), updatePerson.getEmail(), id);
    }
    public void delete(int id) { // удаление
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}