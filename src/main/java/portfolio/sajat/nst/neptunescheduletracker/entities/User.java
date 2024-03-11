package portfolio.sajat.nst.neptunescheduletracker.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    private String fullName;
    private String neptuneCode;
    private Date dateOfBirth;
    private Date startOfStudies;
    private int currentSemester;
    private int subjectId;

    public User(int id, String fullName, Date dateOfBirth, Date startOfStudies, int currentSemester, int subjectId){
        super();

    }
}
