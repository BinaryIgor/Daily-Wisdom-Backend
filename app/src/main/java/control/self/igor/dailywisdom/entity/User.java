package control.self.igor.dailywisdom.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull(message = "User name must have at least 3 characters")
    @Size(min = 3, message = "User name must have at least 3 characters")
    @Column(name = "name")
    private String name;

    @NotNull(message = "User password must have at least 3 characters")
    @Size(min = 3, message = "User password must have at least 3 characters")
    @Column(name = "password")
    private String password;

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
	    CascadeType.REFRESH })
    @JoinColumn(name = "user_role_id")
    private UserRole userRole;

    public User() {

    }

    public User(String name, String password, UserRole userRole) {
	this.name = name;
	this.password = password;
	this.userRole = userRole;
    }

    @Override
    public long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public String getPassword() {
	return password;
    }

    public UserRole getUserRole() {
	return userRole;
    }

    public void setId(long id) {
	this.id = id;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public void setUserRole(UserRole userRole) {
	this.userRole = userRole;
    }

    @Override
    public String toString() {
	return "User [id=" + id + ", name=" + name + ", password=" + password + ", userRole=" + userRole + "]";
    }

}
