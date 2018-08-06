package com.ntouzidis.cooperative.module.customer;

import com.ntouzidis.cooperative.module.sale.Sale;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "customer")
public class Customer implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message=" is required")
    @Size(min=3, max=50, message="3 to 50 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message=" is required")
    @Size(min=3, max=50, message="3 to 50 characters")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message=" is required")
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")
    @Column(name = "email")
    private String email;

    @NotNull(message=" is required")
    @Size(min=3, max=50, message="3 to 50 characters")
    @Column(name = "username")
    private String username;
    
    @OneToMany(mappedBy="customer")
    private List<Sale> sales;

    @OneToMany(
            mappedBy = "customer",
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    private List<Address> addresses = new ArrayList<>();
    

    public Customer() {}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", sales=" + sales +
                ", addresses=" + addresses +
                '}';
    }
}
