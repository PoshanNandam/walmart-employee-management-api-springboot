package itc.walmart.pocmysql.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "address")
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String address;
    String pincode;

    public Address() {
    }
}
