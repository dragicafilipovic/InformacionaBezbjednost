package ib.project.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User implements UserDetails {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "certificate")
	private String certificate;

	@Column(name = "active")
	private boolean active;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private Set<Authority> user_authorities = new HashSet<>();

	public User() {

	}

	public User(Integer id, String email, String password, String certificate, boolean active,
			Set<Authority> user_authorities) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.certificate = certificate;
		this.active = active;
		this.user_authorities = user_authorities;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<Authority> getUser_authorities() {
		return user_authorities;
	}

	public void setUser_authorities(Set<Authority> user_authorities) {
		this.user_authorities = user_authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.user_authorities;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

}
