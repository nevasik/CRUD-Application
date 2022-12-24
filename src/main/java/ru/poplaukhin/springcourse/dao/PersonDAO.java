package ru.poplaukhin.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.poplaukhin.springcourse.models.Person;
import java.util.List;
import java.util.Optional;
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
    public Optional<Person> show(String address) {
        return jdbcTemplate.query("SELECT * FROM person WHERE address=?", new Object[]{address},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny(); // сделали в этой строке условие, что если человек не найден, будет null
    }
    public Person show(int id) { // вытягиваем человека по его id
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?", new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }
    public void save(Person person) { // сохранение человека
        jdbcTemplate.update("INSERT INTO Person(name, age, email, address) VALUES(?, ?, ?, ?)", person.getName(), person.getAge(),
                person.getEmail(), person.getAddress());
    }
    public void update(int id, Person updatePerson) { // обновление(перезапись) человека
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?",
                updatePerson.getName(), updatePerson.getAge(), updatePerson.getEmail(), id);
    }
    public void delete(int id) { // удаление
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}








//    public void testMultipleUpdate() {
//        List<Person> people = create1000People();
//
//        long before = System.currentTimeMillis(); // время до вставки в миллисекундах
//
//        for (Person person: people) {
//            jdbcTemplate.update("INSERT INTO Person VALUES(?, ?, ?, ?)", person.getId(), person.getName(), person.getAge(),
//                    person.getEmail());
//        }
//
//        long after = System.currentTimeMillis(); // время после вставки в миллисекундах
//        System.out.println("Time: " + (after - before));
//    }

//    private List<Person> create1000People() {
//        List<Person> people = new ArrayList<>();
//        for (int i = 0; i < 1000; i++) {
//            people.add(new Person(i, "Name" + i, 30, "test"+i + "mail.ru"));
//        }
//
//        return people;
//    }
//    public void testBatchUpdate() {
//        List<Person> people = create1000People();
//
//        long before = System.currentTimeMillis();
//
//        jdbcTemplate.batchUpdate("INSERT INTO Person VALUES(?, ?, ?, ?)",
//                new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
//                preparedStatement.setInt(1, people.get(i).getId());
//                preparedStatement.setString(2, people.get(i).getName());
//                preparedStatement.setInt(3, people.get(i).getAge());
//                preparedStatement.setString(4, people.get(i).getEmail());
//            }
//            @Override
//            public int getBatchSize() {
//                return people.size(); // здесь нужно возвращать размер нашего предстоящего Batch Update
//            }
//        });
//
//        long after = System.currentTimeMillis();
//        System.out.println("Time: " + (after - before));
//    }

    ////////////////////////////////////
    ////////// Тестируем производительность пакетное вставки
    ////////////////////////////////////
