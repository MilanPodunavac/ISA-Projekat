package code.controller;

import code.model.Student;
import code.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/getById/{id}")
    public Student getById(@PathVariable Integer id){
        return studentService.findById(id);
    }

    @GetMapping(value = "/getAll")
    public List<Student> getAll(){
        return studentService.findAll();
    }
}
