// package com.matchalab.trip_todo_api.model.mapper;

// import org.mapstruct.Mapper;
// import org.mapstruct.Mapping;

// import com.matchalab.trip_todo_api.model.Todo;
// import com.matchalab.trip_todo_api.model.DTO.TodoDTO;
// import com.matchalab.trip_todo_api.repository.AccomodationRepository;
// import com.matchalab.trip_todo_api.repository.TodoRepository;

// @Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
// public abstract class TodoMapper {

// protected TodoRepository todoRepository;
// protected AccomodationRepository accomodationRepository;

// // @Autowired
// // protected void setReferenceFinderRepository(ReferenceFinderRepository
// // repository) {
// // this.repository = repository;
// // }
// @Mapping
// public TodoDTO mapToTodoDTO(Todo todo) {
// return TodoDTO.builder()
// .id(todo.getId())
// .orderKey(todo.getOrderKey())
// .note(todo.getNote())
// .category(todo.getCategory())
// .type(todo.getType())
// .title(todo.getTitle())
// .iconId(todo.getIconId())
// .title(todo.getTitle())
// .title(todo.getTitle())
// .startDateISOString(todo.getStartDateISOString())
// .endDateISOString(todo.getEndDateISOString())
// .destination(todo.getDestination())
// .todolist(todo.getTodolist())
// .accomodation(todo.getAccomodation()).build();
// }

// public Todo mapToTodo(TodoDTO todoDTO) {
// return Todo.builder()
// .id(todoDTO.id())
// .startDateISOString(todoDTO.startDateISOString())
// .endDateISOString(todoDTO.endDateISOString())
// .destination(todoDTO.destination())
// .todolist(todoDTO.todolist())
// .accomodation(todoDTO.accomodation()).build();
// }
// }