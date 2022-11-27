package ru.poplaukhin.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.poplaukhin.springcourse.dao.PersonDAO;
import ru.poplaukhin.springcourse.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    // внедрение нашего PersonDAO через конструктор
    private final PersonDAO personDAO;
    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
    @GetMapping() // get method in HTTP request
    public String index(Model model) {
        // получим всех людей из DAO и передадим на отображение в представлении(View)
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) { // тут мы извлекаем id из нашего метода
        // Получим одного человека по его id из DAO и передадим этого человека на отображение в представление(View)
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }
    @GetMapping("/new") // возвращает html форму для создания нового человека, и мы также можем создать Model и положить туда new Person в значение
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }
    @PostMapping()// принимает на вход POST запрос(ничего не передаём в аргумент, потому что на странице "/people" мы будем создавать нового пользователя) будет брать данные из POST запроса и добавлять в базу данных с помощью DAO
    public String create(@ModelAttribute("person") @Valid Person person, // что бы создать new Person() и положить в него данные с формы мы используем @ModelAttribute и проверяем Валиддность
                         BindingResult bindingResult) { // BindingResult должен обязательно идти, если у нас есть @Valid
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDAO.save(person);
        // тут используем redirect - это механизм, который говорит браузеру перейти на какую-то другую страницу, и после ":" мы указываем браузеру куда ему нужно перейти
        return "redirect:/people"; // когда человек будет добавлен в базу данных мы отправляем браузеру эту строку, и он увидит redirect, и совершает переход на страницу "/people"
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) { // будем показана страница обновления человека
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}") //
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);

        return "redirect:/people";
    }
}
