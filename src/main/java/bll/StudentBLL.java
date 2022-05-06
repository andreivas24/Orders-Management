package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.EmailValidator;
import bll.validators.StudentAgeValidator;
import bll.validators.Validator;
import dao.AbstractDAO;
import dao.StudentDAO;
import model.Student;


public class StudentBLL {

    private List<Validator<Student>> validators;
    private StudentDAO studentDAO;

    public StudentBLL() {
        validators = new ArrayList<Validator<Student>>();
        validators.add(new StudentAgeValidator());

        studentDAO = new StudentDAO();
    }

    public Student findStudentById(int id) {
        Student st = studentDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The student with id =" + id + " was not found!");
        }
        return st;
    }

    public List<Student> findStudents(){
        List<Student> list = studentDAO.findAll();
        if(list == null){
            throw new NoSuchElementException("There are no students");
        }
        return list;
    }

    public void insertStudent(Student st){
        for(Validator v:validators){
            v.validate(st);
        }
        studentDAO.insert(st);
    }

    public void updateStudent(Student st, int id){
        for(Validator v:validators){
            v.validate(st);
        }
        studentDAO.update(st,id);
    }

    public void deleteStudent(int id)
    {
        studentDAO.delete(id);
    }
}
