package com.javainterviewpoint.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.javainterviewpoint.model.Student;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class StudentDAOImpl implements StudentDAO
{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void createStudent(Student student)
    {
        entityManager.persist(student);
    }

    @Override
    public Student getStudentById(long id)
    {
        return entityManager.find(Student.class,id);
    }

    @Override
    public List<Student> getAllStudents()
    {
        return entityManager.createQuery("select stu from Student stu").getResultList();
    }

    @Override
    public void updateStudent(Student student)
    {
        entityManager.merge(student);
    }

    @Override
    public void deleteStudent(long id)
    {
        Student s = entityManager.find(Student.class,id);
        entityManager.remove(s);
    }
}
