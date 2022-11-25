package ru.poplaukhin.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.poplaukhin.springcourse.dao.PersonDAO;

@Controller
@RequestMapping("/people")
public class PeopleController {

    // внедрение нашего PersonDAO через конструктор
    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
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
}
