package portfolio.sajat.nst.neptunescheduletracker.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Setter
@Getter
public class User {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String fullName;
    private String neptuneCode;
    private Date dateOfBirth;
    private Date startOfStudies;
    private int currentSemester;
    private int subjectId;

    public User(int id, String fullName, String neptuneCode, Date dateOfBirth, Date startOfStudies){
        super();
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.startOfStudies = startOfStudies;
        this.neptuneCode = neptuneCode;
    }
}
