package iot.api.model.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "stores")
public class Store {

	@Id
	@Column(name = "STORE_ID")
	private long id;
	
	@NotNull
	@Column(name = "NAME")
	private String name;
	
	@NotNull
	@Column(name = "ADDRESS")
	private String address;
	
	@NotNull
	@Column(name = "RASBERRY")
	private String raspberry;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="store", cascade = CascadeType.ALL)
	private List<Fridge> fridges;

	public Store(){

	}

	public Store(String name,String address,String raspberry) {
		this.name = name;
		this.address = address;
		this.raspberry = raspberry;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRaspberry() {
		return raspberry;
	}

	public void setRaspberry(String raspberry) {
		this.raspberry = raspberry;
	}

	public List<Fridge> getFridges() {
		return fridges;
	}

	public void setFridges(List<Fridge> fridges) {
		this.fridges = fridges;
	}

	@Override
	public String toString() {
		return "Store [id=" + id + ", name=" + name + ", address=" + address + ", raspberry=" + raspberry + ", fridges="
				+ fridges + "]";
	}

}
