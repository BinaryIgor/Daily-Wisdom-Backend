package control.self.igor.dailywisdom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_role")
public class UserRole implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull(message = "User role must have at least 3 characters")
    @Size(min = 3, message = "User role must have at least 3 characters")
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

	public boolean equalsByTranslation(String role) {
	    return translation.equals(role);
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

    public static UserRole createAdmin() {
	return new UserRole(Role.ADMIN.translation);
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
