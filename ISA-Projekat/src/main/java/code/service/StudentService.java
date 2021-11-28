package code.service;

import code.model.Student;
import code.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    public Student findById(Integer id){
        return studentRepository.findOneById(id);
    }
    public List<Student> findAll(){
        return studentRepository.findAll();
    }
}
