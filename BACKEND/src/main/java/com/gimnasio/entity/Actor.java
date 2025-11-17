package com.gimnasio.entity;


import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Actor extends DomainEntity {

	@NotBlank
	private String nombre;
	
	@NotBlank
	private String apellidos;
	
	@NotBlank
	@Email
	@Column(nullable = false, unique = true) //Asegura que todos los usuarios tengan email y no se repita
	private String email;
	
	@Column(nullable = true)
	@URL
	private String fotografia;
	
	@NotBlank
	@Pattern(regexp="^[6-9][0-9]{8}$")
	@Column(nullable = false, unique = true)
	private String telefono;
	
	@Min(0)
	private int edad;
	//El rol no estaba
	@NotBlank
	private String rol;
	//Firebase
	@Column(name = "firebase_id", unique = true)
	private String firebaseId;
	
	public Actor() {
		super();
	}

	public Actor(@NotBlank String nombre, @NotBlank String apellidos, @NotBlank @Email String email,
			@NotBlank @URL String fotografia, @NotBlank @Pattern(regexp = "^[6-9][0-9]{8}$") String telefono,
			@Min(0) int edad) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.fotografia = fotografia;
		this.telefono = telefono;
		this.edad = edad;
	}

	//GETTERS
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public String getRol() {
		return rol;
	}
	//SETTERS
	public void setEmail(String email) {
		this.email = email;
	}

	public String getFotografia() {
		return fotografia;
	}

	public void setFotografia(String fotografia) {
		this.fotografia = fotografia;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}
	
	public void setRol(String rol) {
		this.rol = rol;
	}
	
	public void setId(Long id) {
		this.id=id;
	}
	
	// getters y setters
	public String getFirebaseId() {
	    return firebaseId;
	}

	public void setFirebaseId(String firebaseId) {
	    this.firebaseId = firebaseId;
	}
	
}
