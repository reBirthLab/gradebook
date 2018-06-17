package com.rebirthlab.gradebook.application.service.attendance;

import com.rebirthlab.gradebook.domain.model.attendance.StudentAttendance;
import java.util.Optional;

/**
 * Created by Anastasiy
 */
public interface AttendanceService {

    Optional<StudentAttendance> updateById(Long id, AttendanceDTO attendanceDTO);

}
