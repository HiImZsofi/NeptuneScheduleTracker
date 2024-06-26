package wv.work.nst.neptunescheduletracker.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User extends BaseEntity{
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String neptuneCode;
    @Column
    private LocalDate dateOfBirth;
    @Column
    private String startOfStudies;
    @Column
    private int currentSemester;
    @Column
    private int subjectId;
    @Column
    private String password;

    @JsonCreator
    public User(String firstName,String lastName, String neptuneCode, LocalDate dateOfBirth, String startOfStudies, String email){
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.startOfStudies = startOfStudies;
        this.neptuneCode = neptuneCode;
        this.email = email;
    }

    @JsonCreator
    public User(String firstName, String lastName, String email, String password){
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }
}