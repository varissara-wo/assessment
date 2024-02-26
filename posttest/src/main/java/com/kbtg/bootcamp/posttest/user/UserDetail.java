package com.kbtg.bootcamp.posttest.user;


import com.kbtg.bootcamp.posttest.userticket.UserTicket;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="users")
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true)
    private String name;
    private String password;
    private String role;
    @OneToMany(mappedBy = "userDetail")
    private List<UserTicket> userTickets;

    public UserDetail() {
    }

    public Integer getId() {
        return Math.toIntExact(id);
    }

    public void setId(Long id) {
        this.id = Long.valueOf(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
