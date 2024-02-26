package com.kbtg.bootcamp.posttest.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/lotteries")
    public ResponseEntity<GetUserTicketsResponseDto> getUserTickets(@PathVariable Integer userId){
        return  userService.getUserTickets(userId);
    }

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<BuyLotteryResponseDto> buyLotteryTicket(@PathVariable Integer userId, @PathVariable Integer ticketId){
        return  userService.buyLotteryTicket(userId,ticketId);
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<DeleteLotteryResponseDto> deleteLotteryTicket(@PathVariable Integer userId, @PathVariable Integer ticketId){
        return userService.deleteLotteryTicket(userId,ticketId);
    }

}




