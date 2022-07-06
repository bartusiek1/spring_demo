package pl.sda.arppl4.spring_demo.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sda.arppl4.spring_demo.model.Student;
import pl.sda.arppl4.spring_demo.service.StudentService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

//http://localhost:8080/CTX/api/student
//- protokół http
// host localhost
// port 8080
// CTX - kontekst jest pusty
// api/student - endpoint

@Slf4j
@RequestMapping("/api/student")   // tu określamy jakby główny prefiks...kolejne podstrony zaczynają
// się tak samo, czyli api/student/test lub api/student/students...
// Representational State Transfer
@RestController
@RequiredArgsConstructor
public class StudentController {
    // REST == HTTP == ZAPYTANIA == REQUESTS (mają 'metody')
    // Wyróżniamy metody HTTP:
    //  - GET  - gdy chcemy coś pobrać z servera (wysłać zapytanie w oknie przeglądarki)
    //  - POST  - gdy chcemy coś wysłać na server (wstaw, edytuj)
    //  - PUT   - wstaw, podmień (create)
    //  - DELETE  - usuń
    //  - PATCH - implementują osoby, które chcą zaimplementować RESTFUL API (edytuj fragment [nie cały])

    private final StudentService studentService;

    @GetMapping
    public List<Student> studentList() {
        log.info("Wywołano metodę studentList");

        List<Student> studentList = studentService.findAll();
        return studentList;
    }

    // PathVariable - zmienna podana w ścieżce
    // http://localhost:8080/api/student/5

    @ApiOperation(value = "znajdzStudenta", notes = "Ten endpoint pozwala na znajdowanie studentow po ich identyfikatorach w bazie danych")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ten kod oznacza sukces operacji i poprawne załadowanie obiektów z tabeliu 'students"),
            @ApiResponse(code = 400, message = "Ten kod oznacza źle wpisaną wartość lub typ parametru"),
            @ApiResponse(code = 401, message = "Ten kod oznacza brak uprawnień do wykonywania tej operacji"),
            @ApiResponse(code = 403, message = "Ten kod oznacza dostęp zabroniony"),
            @ApiResponse(code = 500, message = "Ten kod oznacza błąd serwera")
    })
    @GetMapping("/{identifier}")
    public Student findStudent(@ApiParam(name = "identyfikator studenta", example = "1", type = "long", required = true) @PathVariable(name = "identifier") Long studentId) {
        log.info("Wywołano metodę findStudent: " + studentId);

        return studentService.findById(studentId);
    }

        // Request Param - parametr zapytania
        // http://localhost:8080/api/student/find?studentId=5
        @GetMapping("/find")
        public Student findStudentById (@RequestParam(name = "studentId") Long studentId){
            log.info("Wywołano metodę findStudentById: " + studentId);

            return studentService.findById(studentId);
        }


        // PathVariable - zmienna podana w ścieżce
        // http://localhost:8080/api/student/5
        @DeleteMapping("/{identifier}")
        public void deleteStudent (@PathVariable(name = "identifier") Long studentId){
            log.info("Wywołano metodę deleteStudent: " + studentId);

            studentService.deleteById(studentId);
        }

        // Get i Delete lecą do URL...
        // PUT wysyłany jest do Body


        @PostMapping()
        @ResponseStatus(HttpStatus.CREATED)
        public void createStudent (@RequestBody Student student){
            log.info("Wywołano metodę createStudent: " + student);

            studentService.save(student);
        }

        // Request Param - parametr zapytania
        // Select * from Student s where s.name LIKE %Gawel%
        // http://localhost:8080/api/student/findByName?name=Gawel

        @GetMapping("/findByName")
        public List<Student> findStudentByName (@RequestParam(name = "name") String searchedName){
            log.info("Wywołano metodę finsStudentByName: " + searchedName);

            return studentService.findAllByNameContaining(searchedName);
        }
    }

// Controler  ->  zwraca HTML
//RestController  -> zwraca DANE
// [Controller] -> [ -> ] -> [Respository]