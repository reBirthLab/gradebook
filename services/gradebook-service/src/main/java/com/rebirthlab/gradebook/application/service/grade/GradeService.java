package com.rebirthlab.gradebook.application.service.grade;

import com.rebirthlab.gradebook.domain.model.grade.StudentGrade;
import java.util.Optional;

/**
 * Created by Anastasiy
 */
public interface GradeService {

    Optional<StudentGrade> updateByStudentIdAndTaskId(Long studentId, Long taskId, GradeDTO gradeDTO);

}
