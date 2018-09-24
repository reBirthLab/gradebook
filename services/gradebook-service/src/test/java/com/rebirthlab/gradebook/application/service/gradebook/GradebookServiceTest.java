package com.rebirthlab.gradebook.application.service.gradebook;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.rebirthlab.gradebook.domain.model.department.Department;
import com.rebirthlab.gradebook.domain.model.faculty.Faculty;
import com.rebirthlab.gradebook.domain.model.gradebook.Gradebook;
import com.rebirthlab.gradebook.domain.model.gradebook.GradebookRepository;
import com.rebirthlab.gradebook.domain.model.group.Group;
import com.rebirthlab.gradebook.domain.model.group.GroupRepository;
import com.rebirthlab.gradebook.domain.model.semester.Semester;
import com.rebirthlab.gradebook.domain.model.semester.SemesterRepository;
import com.rebirthlab.gradebook.domain.model.user.Lecturer;
import com.rebirthlab.gradebook.domain.model.user.LecturerRepository;
import java.util.Optional;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Anastasiy
 */
@RunWith(SpringRunner.class)
public class GradebookServiceTest {

    @Autowired
    private GradebookRepository gradebookRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private GradebookService gradebookService;

    @Before
    public void setUp() {
        Faculty faculty = mock(Faculty.class);
        when(faculty.getId()).thenReturn(101L);
        when(faculty.getName()).thenReturn("Test Faculty");

        Department department = mock(Department.class);
        when(department.getId()).thenReturn(101L);
        when(department.getFacultyId()).thenReturn(faculty);
        when(department.getName()).thenReturn("Test Department");

        Group group = mock(Group.class);
        when(group.getId()).thenReturn(101L);
        when(group.getNumber()).thenReturn("TEST101");
        when(group.getFacultyId()).thenReturn(faculty);

        Semester semester = mock(Semester.class);
        when(semester.getId()).thenReturn(101L);
        when(semester.getName()).thenReturn("Test Semester");
        when(semester.getAcademicYear()).thenReturn((short) 2020);

        Lecturer lecturer1 = mock(Lecturer.class);
        when(lecturer1.getId()).thenReturn(101L);
        when(lecturer1.getEmail()).thenReturn("one@test.com");
        when(lecturer1.getFirstName()).thenReturn("One");
        when(lecturer1.getLastName()).thenReturn("Test");
        when(lecturer1.getRole()).thenReturn("lecturer");
        when(lecturer1.getDepartmentId()).thenReturn(department);

        Lecturer lecturer2 = mock(Lecturer.class);
        when(lecturer2.getId()).thenReturn(102L);
        when(lecturer2.getEmail()).thenReturn("two@test.com");
        when(lecturer2.getFirstName()).thenReturn("Two");
        when(lecturer2.getLastName()).thenReturn("Test");
        when(lecturer2.getRole()).thenReturn("lecturer");
        when(lecturer2.getDepartmentId()).thenReturn(department);

        when(groupRepository.findById(group.getId())).thenReturn(Optional.of(group));
        when(groupRepository.findById(not(eq(101L)))).thenReturn(Optional.empty());

        when(semesterRepository.findById(semester.getId())).thenReturn(Optional.of(semester));
        when(semesterRepository.findById(not(eq(101L)))).thenReturn(Optional.empty());

        when(lecturerRepository.findById(lecturer1.getId())).thenReturn(Optional.of(lecturer1));
        when(lecturerRepository.findById(lecturer2.getId())).thenReturn(Optional.of(lecturer2));

        doReturn(true).when(gradebookRepository).existsBy(anyLong(), anyLong(), not(eq("Test Subject")));
        doAnswer(returnsFirstArg()).when(gradebookRepository).save(any(Gradebook.class));

    }

    @Test
    public void create_ValidGradebookDTO_CreateGradebook() {
        GradebookDTO gradebookDTO = new GradebookDTO();
        gradebookDTO.setGroupId(101L);
        gradebookDTO.setSemesterId(101L);
        gradebookDTO.setSubject("Test Subject");
        gradebookDTO.setDescription("Test description");
        gradebookDTO.setLecturerCollection(Set.of(101L, 102L));

        Optional<Gradebook> gradebookOptional = gradebookService.create(gradebookDTO);

        assertThat(gradebookOptional.isPresent()).isTrue();
        Gradebook gradebook = gradebookOptional.get();

        assertThat(gradebook.getGroupId()).isInstanceOf(Group.class);
        assertThat(gradebook.getGroupId().getNumber()).isEqualTo("TEST101");
        assertThat(gradebook.getGroupId().getFacultyId()).isInstanceOf(Faculty.class);
        assertThat(gradebook.getGroupId().getFacultyId().getName()).isEqualTo("Test Faculty");
        assertThat(gradebook.getSubject()).isEqualTo(gradebookDTO.getSubject());
        assertThat(gradebook.getDescription()).isEqualTo(gradebookDTO.getDescription());
        assertThat(gradebook.getLecturerCollection().size()).isEqualTo(2);

        gradebook.getLecturerCollection().forEach(lecturer -> assertThat(lecturer).isInstanceOf(Lecturer.class));
    }

    @TestConfiguration
    static class GradebookServiceTestConfiguration {

        @MockBean
        private GradebookRepository gradebookRepository;

        @MockBean
        private GroupRepository groupRepository;

        @MockBean
        private SemesterRepository semesterRepository;

        @MockBean
        private LecturerRepository lecturerRepository;

        @Bean
        public GradebookService gradebookService() {
            return new GradebookServiceImpl(gradebookRepository, groupRepository, semesterRepository,
                    lecturerRepository);
        }
    }

}
