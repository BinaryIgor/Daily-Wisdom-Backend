package control.self.igor.dailywisdom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "role")
    private String role;

    public long getId() {
	return id;
    }

    public String getRole() {
	return role;
    }

    public void setId(long id) {
	this.id = id;
    }

    public void setRole(String role) {
	this.role = role;
    }

}
