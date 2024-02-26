package com.kbtg.bootcamp.posttest.user;


import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.userticket.UserTicket;
import com.kbtg.bootcamp.posttest.userticket.UserTicketRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final LotteryRepository lotteryRepository;
    private final UserTicketRepository userTicketRepository;

    public UserService(UserRepository userRepository, LotteryRepository lotteryRepository, UserTicketRepository userTicketRepository) {
        this.userRepository = userRepository;
        this.lotteryRepository = lotteryRepository;
        this.userTicketRepository = userTicketRepository;
    }

    public ResponseEntity<GetUserTicketsResponseDto> getUserTickets(Integer userId){
        List<UserTicket> userTicket  = userTicketRepository.findAllByUserId(Long.valueOf(userId));
        int count = userTicket.size();
        int cost = 0;
        List<String> tickets = new ArrayList<>();

        for (UserTicket ticket : userTicket) {
            tickets.add(ticket.getLottery().getTicket());
            cost += ticket.getLottery().getPrice();
        }

        GetUserTicketsResponseDto response = new GetUserTicketsResponseDto(tickets,count,cost);

        return  new ResponseEntity<>(response, HttpStatus.OK);
    }


    public ResponseEntity<BuyLotteryResponseDto> buyLotteryTicket(Integer userId, Integer ticketId){
        Optional<UserDetail> userDetailOptional  = userRepository.findById(Long.valueOf(userId));

        if (userDetailOptional.isEmpty()) {
            throw new BadRequestException("User with id: " + userId + " does not exist");
        }

//        String userName = userDetailOptional.get().getName();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String authenticatedUserName = authentication.getName();
//        if (!authenticatedUserName.equals(userName)) {
//            throw new BadRequestException("Access denied. The provided userId does not match the authenticated user's name.");
//        }

        Optional<Lottery> isLotteryAvailable = lotteryRepository.findById(Long.valueOf(ticketId));
        if (isLotteryAvailable.isEmpty()) {
            throw new BadRequestException("Lottery id: " + ticketId + " is not available");
        }

       List<Lottery> lotteries = lotteryRepository.findAvailableLotteryTicket();
        boolean isTicketAvailable = lotteries.stream()
                .anyMatch(lottery -> lottery.getId().equals(ticketId));
        if(!isTicketAvailable){
            throw new BadRequestException("Lottery id: " + ticketId + " has already been sell");
        }

        UserTicket userTicket  = new UserTicket();

            userTicket.setLottery(isLotteryAvailable.get());
            userTicket.setUserDetail(userDetailOptional.get());
            userTicket = userTicketRepository.save(userTicket);


        BuyLotteryResponseDto response = new BuyLotteryResponseDto(userTicket.getId());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public  ResponseEntity<DeleteLotteryResponseDto> deleteLotteryTicket (Integer userId, Integer ticketId) {
        Optional<UserTicket> userTicketOptional = userTicketRepository.findByUserDetailIdAndLotteryId(userId, ticketId);
        if (userTicketOptional.isEmpty()) {
            throw new BadRequestException("Lottery ticket with id: " + ticketId + " does not exist or is not associated with the user");
        }

        userTicketRepository.delete(userTicketOptional.get());
        Optional<Lottery> lottery = lotteryRepository.findById(Long.valueOf(ticketId));

        DeleteLotteryResponseDto response = new DeleteLotteryResponseDto(lottery.get().getTicket());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}


