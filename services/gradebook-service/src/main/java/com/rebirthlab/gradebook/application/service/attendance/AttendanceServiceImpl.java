package com.rebirthlab.gradebook.application.service.attendance;

import com.rebirthlab.gradebook.domain.model.attendance.StudentAttendance;
import com.rebirthlab.gradebook.domain.model.attendance.StudentAttendanceRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Anastasiy
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttendanceServiceImpl.class);

    private StudentAttendanceRepository attendanceRepository;

    public AttendanceServiceImpl(StudentAttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    @Transactional
    public Optional<StudentAttendance> updateById(Long id, AttendanceDTO attendanceDTO) {
        Optional<StudentAttendance> attendance = attendanceRepository.findById(id);
        if (!attendance.isPresent()) {
            LOGGER.warn("Cannot update attendance. Attendance id '{}' is not found", id);
            return Optional.empty();
        }
        return updateAttendance(attendanceDTO, attendance.get());
    }

    private Optional<StudentAttendance> updateAttendance(AttendanceDTO attendanceDTO, StudentAttendance attendance) {
        patchAttendanceDTO(attendanceDTO, attendance);
        if (!validateAttendance(attendanceDTO)) {
            LOGGER.warn("Cannot update attendance. Only one of attendance states can be true at the same time: " +
                            "[present={}, absent={}, absentWithReason={}]",
                    attendanceDTO.getPresent(), attendanceDTO.getAbsent(), attendanceDTO.getAbsentWithReason());
            return Optional.empty();
        }
        StudentAttendance updatedAttendance = new StudentAttendance(attendance.getId(),
                attendance.getTaskId(),
                attendance.getStudentId(),
                attendance.getClassDate(),
                attendanceDTO.getPresent(),
                attendanceDTO.getAbsent(),
                attendanceDTO.getAbsentWithReason());
        return Optional.of(attendanceRepository.save(updatedAttendance));
    }

    private void patchAttendanceDTO(AttendanceDTO attendanceDTO, StudentAttendance attendance) {
        attendanceDTO.setId(attendance.getId());
        attendanceDTO.setTaskId(attendance.getTaskId().getId());
        attendanceDTO.setStudentId(attendance.getStudentId().getId());
        attendanceDTO.setClassDate(attendance.getClassDate());
        if (attendanceDTO.getPresent() == null) {
            attendanceDTO.setPresent(attendance.getPresent());
        }
        if (attendanceDTO.getAbsent() == null) {
            attendanceDTO.setAbsent(attendance.getAbsent());
        }
        if (attendanceDTO.getAbsentWithReason() == null) {
            attendanceDTO.setAbsentWithReason(attendance.getAbsentWithReason());
        }
    }

    private boolean validateAttendance(AttendanceDTO attendanceDTO) {
        boolean isPresent = attendanceDTO.getPresent();
        boolean isAbsent = attendanceDTO.getAbsent();
        boolean isAbsentWithReason = attendanceDTO.getAbsentWithReason();
        return isPresent ^ isAbsent ^ isAbsentWithReason ^ (isPresent && isAbsent && isAbsentWithReason);
    }

}