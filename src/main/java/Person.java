import javax.persistence.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
// https://dzone.com/articles/hibernate-mapping
@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "companyId")
    private int id;

    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Person_avatar",
            joinColumns = @JoinColumn(name = "companyId"),
            inverseJoinColumns = @JoinColumn(name = "avatarId"))
    private Collection<Avatar> avatars = new ArrayList<>();

    public Collection<Avatar> getAvatars() {
        return avatars;
    }

    public void setAvatars(Collection<Avatar> avatars) {
        this.avatars = avatars;
    }
}
