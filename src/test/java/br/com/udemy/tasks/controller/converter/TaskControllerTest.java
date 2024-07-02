package br.com.udemy.tasks.controller.converter;

import br.com.udemy.tasks.controller.TaskController;
import br.com.udemy.tasks.controller.dto.TaskDTO;
import br.com.udemy.tasks.controller.dto.TaskInsertDTO;
import br.com.udemy.tasks.model.Task;
import br.com.udemy.tasks.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskControllerTest {

    @InjectMocks
    private TaskController controller;
    @Mock
    private TaskService service;
    @Mock
    private TaskDTOConverter converter;
    @Mock
    private TaskInsertDTOConverter insertDTOConverter;

    @Test
    void controller_mustReturnOk_whenSaveSuccessfuly() {
        when(service.insert(any())).thenReturn(Mono.just(new Task()));
        when(converter.convert(any(Task.class))).thenReturn(new TaskDTO());

        WebTestClient client = WebTestClient.bindToController(controller).build();

        client.post()
                .uri("/task")
                .bodyValue(new TaskInsertDTO())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(TaskDTO.class);
    }

    @Test
    void controller_mustReturnOk_whenGetPaginatedError() {
        when(service.findPaginated(any(), anyInt(), anyInt())).thenReturn(Mono.just(Page.empty()));

        WebTestClient client = WebTestClient.bindToController(controller).build();

        client.get()
                .uri("/task")
                .exchange()
                .expectStatus()
                .is5xxServerError()
                .expectBody(TaskDTO.class);
    }

    @Test
    void controller_mustReturnNoContent_whenDeleteSuccessfully() {
        String taskId = "any-id";

        when(service.deleteById(any())).thenReturn(Mono.empty());
        WebTestClient client = WebTestClient.bindToController(controller).build();

        client.delete()
                .uri("/task/" + taskId)
                .exchange()
                .expectStatus()
                .isNoContent();
    }
}
