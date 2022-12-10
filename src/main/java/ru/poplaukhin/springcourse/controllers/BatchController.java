//package ru.poplaukhin.springcourse.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import ru.poplaukhin.springcourse.dao.PersonDAO;
//
//@Controller
//@RequestMapping("/test-batch-update")
//public class BatchController {
//    private final PersonDAO personDAO;
//
//    @Autowired
//    public BatchController(PersonDAO personDAO) {
//        this.personDAO = personDAO;
//    }
//
//    @GetMapping() // базовая страница
//    public String index() {
//        return "batch/index";
//    }
//
//    @GetMapping("/without")
//    public String withoutBatch() { // без batch update
//        personDAO.testMultipleUpdate(); // в этом методе будем внедрять просто 1000 записей в БД
//        return "redirect:/people";
//    }
//
//    @GetMapping("/with")
//    public String withBatch() { // без batch update
//        personDAO.testBatchUpdate(); // в этом методе будем внедрять просто 1000 записей в БД
//        return "redirect:/people";
//    }
//}
