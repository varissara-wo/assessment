package com.kbtg.bootcamp.posttest.userticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {
    Optional<UserTicket> findByUserDetailIdAndLotteryId(Integer userDetailId, Integer lotteryId);

    @Query("SELECT ut FROM UserTicket ut JOIN ut.userDetail u JOIN ut.lottery l WHERE u.id = :userId")
    List<UserTicket> findAllByUserId(Long userId);

}


