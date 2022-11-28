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
    private final PersonDAO personDAO;
    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
    @GetMapping()
    public String index(Model model) { // получим всех людей из DAO и передадим на отображение в представлении(View)
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }
    @GetMapping("/{id}") // Получим одного человека по его id из DAO и передадим этого человека на отображение в представление(View)
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }
    @GetMapping("/new") // возвращает html форму для создания нового человека, и мы также можем создать Model и положить туда new Person в значение
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }
    @PostMapping() // будет создавать нового пользователя и добавлять его в DAO
    public String create(@ModelAttribute("person") @Valid Person person, // что бы создать new Person() и положить в него данные с формы мы используем @ModelAttribute и проверяем Валидность
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDAO.save(person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) { // будем показана страница перезаписи человека
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }
    @PatchMapping("/{id}") // информация о user и возможность удалить или перезаписать
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
