package Daniil.Spring.models;

//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "role")
    private String role;

    @NotEmpty(message = "username should not be empty")
    @Size(min = 2, max = 100, message = "username size should be between 2 and 100")
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "password should not be empty")
    @Size(min = 2, max = 100, message = "password size should be between 2 and 100")
    private String password;

//    @Column(name = "date_of_birth")
//    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(pattern = "dd/MM/yyyy") //дд/мм/гггг
//    private Date date_of_birth;
//
//    @Column(name = "account_creation_date")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date account_creation_date;

    public Person(String username) {
        this.username = username;
    }

    //Конструктор по умолчанию нужен для Spring
    public Person() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
