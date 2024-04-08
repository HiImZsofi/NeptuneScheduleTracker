package wv.work.nst.neptunescheduletracker.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Setter
@Getter
public class User extends BaseEntity{
    private String fullName;
    private String neptuneCode;
    private LocalDate dateOfBirth;
    private String startOfStudies;
    private int currentSemester;
    private int subjectId;

    public User(String fullName, String neptuneCode, LocalDate dateOfBirth, String startOfStudies){
        super();
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.startOfStudies = startOfStudies;
        this.neptuneCode = neptuneCode;
    }
}