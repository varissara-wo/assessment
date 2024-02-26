package com.kbtg.bootcamp.posttest.userticket;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.user.UserDetail;
import jakarta.persistence.*;

@Entity
@Table(name = "user_ticket")
public class UserTicket {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Lottery lottery;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDetail userDetail;

    public Integer getId() {
        return id;
    }

    public Integer getTicketId() {
        return lottery != null ? lottery.getId() : null;
    }

    public Integer getUserId() {
        return userDetail != null ? userDetail.getId() : null;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public Lottery getLottery() {
        return lottery;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
