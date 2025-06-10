// package com.matchalab.trip_todo_api.controller;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.client.HttpClientErrorException;

// import lombok.RequiredArgsConstructor;

// @RestController
// @RequestMapping("/todo")
// public class TodoController {

// private final TodoService TodoService;

// /**
// * Update the content of a Todo.
// */
// @PutMapping("/{tripId}")
// public ResponseEntity<TodoDTO> updateTodo(@RequestBody Todo newTodo) {
// try {
// return ResponseEntity.ok().body(TodoService.putTodo(newTodo));
// } catch (HttpClientErrorException e) {
// throw e;
// }
// }
// }
