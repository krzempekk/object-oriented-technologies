package pl.edu.agh.iisg.to.dao;

import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;

public class GradeDao extends GenericDao<Grade> {

    public boolean gradeStudent(final Student student, final Course course, final float grade) {
        Grade studentGrade = new Grade(student, course, grade);
        student.gradeSet().add(studentGrade);
        course.gradeSet().add(studentGrade);

        this.update(studentGrade);
        return true;
    }


}
