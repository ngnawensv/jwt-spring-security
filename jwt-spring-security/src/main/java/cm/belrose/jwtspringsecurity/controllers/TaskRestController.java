package cm.belrose.jwtspringsecurity.controllers;

import cm.belrose.jwtspringsecurity.dao.TasksDao;
import cm.belrose.jwtspringsecurity.entities.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskRestController {

    @Autowired
    private TasksDao tasksDao;

    @GetMapping("/tasks")
    private List<Tasks> listTasks(){
        return tasksDao.findAll();
    }

    @PostMapping("/tasks")
    private Tasks save(@RequestBody Tasks t){
        System.out.println(t);
        return tasksDao.save(t);
    }

}
