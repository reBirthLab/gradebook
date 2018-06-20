package com.rebirthlab.gradebook.application.service.task;

import com.rebirthlab.gradebook.domain.model.attendance.StudentAttendance;
import com.rebirthlab.gradebook.domain.model.attendance.StudentAttendanceRepository;
import com.rebirthlab.gradebook.domain.model.grade.StudentGrade;
import com.rebirthlab.gradebook.domain.model.grade.StudentGradeRepository;
import com.rebirthlab.gradebook.domain.model.gradebook.Gradebook;
import com.rebirthlab.gradebook.domain.model.gradebook.GradebookRepository;
import com.rebirthlab.gradebook.domain.model.task.Task;
import com.rebirthlab.gradebook.domain.model.task.TaskRepository;
import com.rebirthlab.gradebook.domain.model.user.Student;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Anastasiy
 */
@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

    private TaskRepository taskRepository;
    private GradebookRepository gradebookRepository;
    private StudentGradeRepository gradeRepository;
    private StudentAttendanceRepository attendanceRepository;

    public TaskServiceImpl(TaskRepository taskRepository, GradebookRepository gradebookRepository,
                           StudentGradeRepository gradeRepository, StudentAttendanceRepository attendanceRepository) {
        this.taskRepository = taskRepository;
        this.gradebookRepository = gradebookRepository;
        this.gradeRepository = gradeRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    @Transactional
    public Optional<Task> create(TaskDTO taskDTO) {
        if (taskRepository.existsBy(taskDTO.getGradebookId(), taskDTO.getTitle())) {
            LOGGER.warn("Cannot create task. Task with title '{}' for gradebookId={} already exists",
                    taskDTO.getTitle(), taskDTO.getGradebookId());
            return Optional.empty();
        }
        Optional<Task> newTask = mapNewEntity(taskDTO);
        if (!newTask.isPresent()) {
            return Optional.empty();
        }
        Task createdTask = taskRepository.save(newTask.get());
        Collection<Student> students = createdTask.getGradebookId().getGroupId().getStudentCollection();
        initializeStudentGrades(createdTask, students);
        initializeStudentAttendance(createdTask, students);
        return Optional.of(createdTask);
    }

    @Override
    @Transactional
    public Optional<Task> updateById(Long id, TaskDTO taskDTO) {
        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()) {
            LOGGER.warn("Cannot update task. Task id '{}' is not found", id);
            return Optional.empty();
        }
        return updateTask(taskDTO, task.get());
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);
        return tasks;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        taskRepository.deleteById(id);
        LOGGER.info("Task with id '{}' and related data were successfully deleted", id);
    }

    private Optional<Task> mapNewEntity(TaskDTO taskDTO) {
        Optional<Gradebook> gradebook = gradebookRepository.findById(taskDTO.getGradebookId());
        if (!gradebook.isPresent()) {
            LOGGER.warn("Cannot create task. Gradebook id '{}' doesn't exist", taskDTO.getGradebookId());
            return Optional.empty();
        }
        Task task = new Task(gradebook.get(), taskDTO.getTitle(), taskDTO.getStartDate(), taskDTO.getTaskLength(),
                taskDTO.isOnCourseMon(), taskDTO.isOnCourseTue(), taskDTO.isOnCourseWed(), taskDTO.isOnCourseThu(),
                taskDTO.isOnCourseFri(), taskDTO.getMaxGrade(), taskDTO.getDescription());
        return Optional.of(task);
    }

    private void initializeStudentGrades(Task task, Collection<Student> students) {
        for (Student student : students) {
            StudentGrade grade = new StudentGrade(student, task, (short) 0);
            gradeRepository.save(grade);
        }
    }

    private void initializeStudentAttendance(Task task, Collection<Student> students) {
        List<Date> classDates = calculateClassDates(task);
        for (Student student : students) {
            for (Date date : classDates) {
                StudentAttendance attendance = new StudentAttendance(task, student, date);
                attendanceRepository.save(attendance);
            }
        }
    }

    private List<Date> calculateClassDates(Task task) {
        LocalDateTime startDate = Instant.ofEpochMilli(task.getStartDate().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        List<Date> classDates = new ArrayList<>();
        List<DayOfWeek> courseDaysOfWeek = getCourseDaysOfWeek(task, startDate);
        for (DayOfWeek courseDayOfWeek : courseDaysOfWeek) {
            LocalDateTime currentDate = startDate.with(TemporalAdjusters.nextOrSame(courseDayOfWeek));
            for (int week = 1; week <= task.getTaskLength(); week++) {
                classDates.add(Date.from(currentDate.atZone(ZoneId.systemDefault()).toInstant()));
                currentDate = currentDate.plusWeeks(1);
            }
        }
        return classDates;
    }

    private List<DayOfWeek> getCourseDaysOfWeek(Task task, LocalDateTime startDate) {
        Set<DayOfWeek> courseDaysOfWeek = new HashSet<>();
        if (task.getOnCourseMon()) {
            courseDaysOfWeek.add(DayOfWeek.MONDAY);
        }
        if (task.getOnCourseTue()) {
            courseDaysOfWeek.add(DayOfWeek.TUESDAY);
        }
        if (task.getOnCourseWed()) {
            courseDaysOfWeek.add(DayOfWeek.WEDNESDAY);
        }
        if (task.getOnCourseThu()) {
            courseDaysOfWeek.add(DayOfWeek.THURSDAY);
        }
        if (task.getOnCourseFri()) {
            courseDaysOfWeek.add(DayOfWeek.FRIDAY);
        }

        List<DayOfWeek> courseDaysOfWeekOrdered = new ArrayList<>();
        DayOfWeek currentDayOfWeek = startDate.getDayOfWeek();
        for (int i = 1; i <= 7; i++) {
            if (courseDaysOfWeek.contains(currentDayOfWeek)) {
                courseDaysOfWeekOrdered.add(currentDayOfWeek);
            }
            currentDayOfWeek = currentDayOfWeek.plus(1);
        }
        return courseDaysOfWeekOrdered;
    }

    private Optional<Task> updateTask(TaskDTO taskDTO, Task task) {
        patchTaskDTO(taskDTO, task);
        Optional<Task> updatedTask = mapUpdatedEntity(taskDTO);
        return updatedTask.map(t -> taskRepository.save(t));
    }

    private void patchTaskDTO(TaskDTO taskDTO, Task task) {
        taskDTO.setId(task.getId());
        taskDTO.setGradebookId(task.getGradebookId().getId());
        taskDTO.setStartDate(task.getStartDate());
        taskDTO.setOnCourseMon(task.getOnCourseMon());
        taskDTO.setOnCourseTue(task.getOnCourseTue());
        taskDTO.setOnCourseWed(task.getOnCourseWed());
        taskDTO.setOnCourseThu(task.getOnCourseThu());
        taskDTO.setOnCourseFri(task.getOnCourseFri());
        taskDTO.setTaskLength(task.getTaskLength());
        taskDTO.setMaxGrade(task.getMaxGrade());
        if (taskDTO.getTitle() == null) {
            taskDTO.setTitle(task.getTitle());
        }
        if (taskDTO.getDescription() == null) {
            taskDTO.setDescription(task.getDescription());
        }
    }

    private Optional<Task> mapUpdatedEntity(TaskDTO taskDTO) {
        Optional<Gradebook> gradebook = gradebookRepository.findById(taskDTO.getGradebookId());
        if (!gradebook.isPresent()) {
            LOGGER.warn("Cannot update task. Task id '{}' doesn't exist", taskDTO.getGradebookId());
            return Optional.empty();
        }
        Task updatedTask = new Task(taskDTO.getId(), gradebook.get(), taskDTO.getTitle(), taskDTO.getStartDate(),
                taskDTO.getTaskLength(), taskDTO.isOnCourseMon(), taskDTO.isOnCourseTue(), taskDTO.isOnCourseWed(),
                taskDTO.isOnCourseThu(), taskDTO.isOnCourseFri(), taskDTO.getMaxGrade(), taskDTO.getDescription());
        return Optional.of(updatedTask);
    }
}
