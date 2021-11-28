package code.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import code.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    public Student findOneById(Integer id);
    public List<Student> findAll();
}
