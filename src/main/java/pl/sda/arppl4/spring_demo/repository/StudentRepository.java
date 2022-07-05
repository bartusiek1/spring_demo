package pl.sda.arppl4.spring_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.arppl4.spring_demo.model.Student;

import java.util.List;

// Bean -> nasionko, ziarno
// Instancja Componentu (Bean) tworzy się przy uruchomieniu aplikacji
// Interfejsy mają tylko definicje metod
@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {    // Student to typ klasy, a Long identyfikator
    // Select * from student s where s.name like .....
    List<Student> findAllByNameLike(String name);   // ta metoda szuka po imieniu podobnych "do"
    // List<Student> findAllByName(String name);  -> ta metoda, jeśli ją byśmy stworzyli - szukałaby studentów po imieniu
}


// USER -> || -> [Controller] -> [...] -> [Repository]