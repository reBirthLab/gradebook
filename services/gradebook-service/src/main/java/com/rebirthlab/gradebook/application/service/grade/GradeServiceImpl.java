package com.rebirthlab.gradebook.application.service.grade;

import com.rebirthlab.gradebook.application.service.attendance.AttendanceServiceImpl;
import com.rebirthlab.gradebook.domain.model.grade.StudentGrade;
import com.rebirthlab.gradebook.domain.model.grade.StudentGradeId;
import com.rebirthlab.gradebook.domain.model.grade.StudentGradeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Anastasiy
 */
@Service
public class GradeServiceImpl implements GradeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttendanceServiceImpl.class);

    private StudentGradeRepository gradeRepository;

    public GradeServiceImpl(StudentGradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    @Transactional
    public Optional<StudentGrade> updateByStudentIdAndTaskId(Long studentId, Long taskId, GradeDTO gradeDTO) {
        Optional<StudentGrade> gradeOptional = gradeRepository.findById(new StudentGradeId(studentId, taskId));
        if (!gradeOptional.isPresent()) {
            LOGGER.warn("Cannot update grade. Grade for studentId={} and taskId={} is not found", studentId, taskId);
            return Optional.empty();
        }
        StudentGrade grade = gradeOptional.get();
        StudentGrade updatedGrade = new StudentGrade(grade.getStudentId(), grade.getTaskId(), gradeDTO.getGrade());
        return Optional.of(gradeRepository.save(updatedGrade));
    }
}
