package ru.hogwarts.school.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import ru.hogwarts.school.dto.event.*;
import ru.hogwarts.school.service.EventService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;


    @GetMapping("/{id}")
    public ResponseEntity <EventDto> getEventById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(eventService.geByIdEvent(id));
    }

    @GetMapping("/full/{id}")
    public ResponseEntity<EventFullDto> getByIdFullEvent(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(eventService.geByIdEventFull(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<EventDto>> allEventWithTaskId() {
        return ResponseEntity.ok(eventService.findAllWithTasks());
    }

    @GetMapping("/short-all")
    public ResponseEntity<List <EventShortDto>> getAllShortEvent() {
        return ResponseEntity.ok(eventService.getByAllEventShort());
    }

    @PostMapping
    public ResponseEntity<EventFullDto> createEvent(@RequestBody @Valid CreateEventDto createEventDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventService.createEvent(createEventDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EventFullDto> patchEventAndTasksAndCreateTask(@PathVariable @Positive Long id,
                                                                        @RequestBody @Valid PatchEventDto patchEventDto) {
        return ResponseEntity.ok(eventService.patchEvent(id, patchEventDto));
    }

    @GetMapping ("/check/{id}")
    public ResponseEntity <DeleteWarningDTO> checkTaskFotThisEvent(@PathVariable @Positive Long id){
        return ResponseEntity.ok(eventService.checkBeforeDelete(id));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity <String> deleteEventAndTasksThisEvent (@PathVariable @Positive Long id) {
        return ResponseEntity.ok("Event" +id + "and this tasks successfully removed");
    }
}
