package ru.poplaukhin.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.poplaukhin.springcourse.models.Person;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Этот класс пока что заменяет СУБД, но потом мы создадим СУБД(но так мы теряем сохранённые данные, данные надо хранить на жёстком диске а не в ОЗУ)
@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    // делаем соединение с нашей БД через JDBC
     private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Person> index() { // вывод всей коллекции людей
        List<Person> people = new ArrayList<>();

        // Statement - это объект, который в себе содержит запросы к Базе данных
        try {
            Statement statement = connection.createStatement();
            String SQLQuery = "SELECT * FROM Person";
            ResultSet resultSet = statement.executeQuery(SQLQuery); // для получение данных

            while (resultSet.next()) {
                Person person = new Person();

                person.setId(resultSet.getInt("id")); // мы получаем текущее значения колонки с названием "id" текущей строки
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                person.setAge(resultSet.getInt("age"));

                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return people;
    }
    public Person show(int id) { // вытягиваем человека по его id
        Person person = null;
//        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM Person WHERE id = ?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery(); // для получения данных

            resultSet.next();

            person = new Person(); // из метода выше, сделали так, что бы получить только первую строчку, так как у нас всегда выводит такая выборка только 1 строку

            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setEmail(resultSet.getString("email"));
            person.setAge(resultSet.getInt("age"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }
    public void save(Person person) { // сохранение человека
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO Person VALUES(1, ?, ?, ?)");
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());

            preparedStatement.executeUpdate(); // выполняем запрос

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(int id, Person updatePerson) { // обновление(перезапись) человека
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE Person SET name=?, age=?, email=? WHERE id=?");

            preparedStatement.setString(1, updatePerson.getName());
            preparedStatement.setInt(2, updatePerson.getAge());
            preparedStatement.setString(3, updatePerson.getEmail());

            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(int id) { // удаление
//        people.removeIf(p -> p.getId() == id);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM Person WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
