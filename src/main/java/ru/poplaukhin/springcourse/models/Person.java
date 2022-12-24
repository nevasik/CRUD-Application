package ru.poplaukhin.springcourse.models;

import javax.validation.constraints.*;

public class Person {
    private int id;
    @NotEmpty(message = "Name should not be empty") // обеспечивает, что бы name не было пустым или null
    @Size(min = 2, max = 60, message = "Name should be between 2 and 60 characters")
    private String name;

    @Min(value = 0, message = "Age should be greater than 0")
    @Max(value = 100, message = "Age must be less than 100")
    private int age;
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    // Страна, Город, почтовый индекс(6 цифр) * Russia, Moscow, 123456
    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Your address should be in this format^ Country, City, Postal Code (6 digits)") // задаём регулярное выражение
    private String address;

    public Person() {
    }
    public Person(int id, String name, int age, String email, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
