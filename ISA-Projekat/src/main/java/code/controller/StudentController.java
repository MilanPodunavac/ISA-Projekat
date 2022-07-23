package code.controller;

import code.controller.base.BaseController;
import code.dto.StudentDto;
import code.model.Student;
import code.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController extends BaseController {
    private final StudentService studentService;

    public StudentController(StudentService studentService, ModelMapper _mapper) {
        super(_mapper);
        this.studentService = studentService;
    }

    @GetMapping(value = "/getById/{id}")
    public Student getById(@PathVariable Integer id){
        return studentService.findById(id);
    }

    @GetMapping(value = "/getAll")
    public List<Student> getAll(){
        return studentService.findAll();
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentDto Post(@RequestBody StudentDto dto){
        Student student = _mapper.map(dto, Student.class);
        StudentDto retVal = _mapper.map(student, StudentDto.class);
        return retVal;
    }
}
