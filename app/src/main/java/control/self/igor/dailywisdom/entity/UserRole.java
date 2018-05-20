package control.self.igor.dailywisdom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "role")
    private String role;

    public enum Role {

	GUEST("guest"), ADMIN("admin");

	private String translation;

	Role(String translation) {
	    this.translation = translation;
	}

	public String getTranslation() {
	    return translation;
	}

	public static boolean isAdmin(String role) {
	    return ADMIN.translation.equals(role);
	}
    }

    public UserRole() {

    }

    public static UserRole createGuest() {
	return new UserRole(Role.GUEST.translation);
    }

    private UserRole(String role) {
	this.role = role;
    }

    @Override
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
