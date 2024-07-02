package br.com.udemy.tasks.controller.converter;

import br.com.udemy.tasks.controller.dto.TaskDTO;
import br.com.udemy.tasks.model.Task;
import br.com.udemy.tasks.model.TaskState;
import br.com.udemy.tasks.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskDTOConverterTest {

    @InjectMocks
    private TaskDTOConverter converter;

    @Test
    void converter_mustReturnTaskDTO_whenInputTask() {
        Task task = TestUtils.buildValidTask();

        TaskDTO dto = converter.convert(task);

        assertEquals(dto.getId(), task.getId());
        assertEquals(dto.getTitle(), task.getTitle());
        assertEquals(dto.getDescription(), task.getDescription());
        assertEquals(dto.getPriority(), task.getPriority());
        assertEquals(dto.getState(), task.getState());
    }

    @Test
    void converter_mustReturnTask_whenInputTaskDTO() {
        TaskDTO dto = TestUtils.buildValidTaskDTO();

        Task task = converter.convert(dto);

        assertEquals(task.getId(), dto.getId());
        assertEquals(task.getTitle(), dto.getTitle());
        assertEquals(task.getDescription(), dto.getDescription());
        assertEquals(task.getPriority(), dto.getPriority());
        assertEquals(task.getState(), dto.getState());
    }

    @Test
    void converter_mustReturnTask_whenInputParameters() {
        String id = "123";
        String title = "Title";
        String description = "Description";
        Integer priority = 1;
        TaskState taskState = TaskState.INSERT;

        Task task = converter.convert(id, title, description, priority, taskState);

        assertEquals(id, task.getId());
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(priority, task.getPriority());
        assertEquals(taskState, task.getState());
    }
}
