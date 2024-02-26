package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.userticket.UserTicket;
import com.kbtg.bootcamp.posttest.userticket.UserTicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LotteryRepository lotteryRepository;

    @Mock
    private UserTicketRepository userTicketRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("When getting a ticket, it should return user ticket list, cost, and count")
    void getUserTickets() {
        Long userId = 1234567890L;
        List<UserTicket> userTickets = new ArrayList<>();
        Lottery lottery1 = new Lottery();
        lottery1.setId(1);
        lottery1.setTicket("123456");
        lottery1.setPrice(10);

        UserDetail userDetail = new UserDetail();
        userDetail.setId(userId);
        userDetail.setName("member1");

        UserTicket userTicket = new UserTicket();
        userTicket.setId(1);
        userTicket.setLottery(lottery1);
        userTicket.setUserDetail(userDetail);

        userTickets.add(userTicket);

        when(userTicketRepository.findAllByUserId(userId)).thenReturn(userTickets);

        ResponseEntity<GetUserTicketsResponseDto> responseEntity = userService.getUserTickets(userId.intValue());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        GetUserTicketsResponseDto responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(1, responseBody.getCount());
        assertEquals(10, responseBody.getCost());
        assertEquals(1, responseBody.getTickets().size());
        assertEquals("123456", responseBody.getTickets().get(0));
    }

    @Test
    @DisplayName("When buying a ticket, it should add new userTicket row and return new row id")
    void buyLotteryTicket() {
        Long userId = 1234567890L;
        Integer ticketId = 2;
        String responseTicket = "789456";

        UserDetail mockUserDetail = new UserDetail();
        mockUserDetail.setId(userId);
        mockUserDetail.setName("member1");

        Lottery mockLottery = new Lottery();
        mockLottery.setId(ticketId);
        mockLottery.setTicket(responseTicket);
        mockLottery.setPrice(10);

        UserTicket mockUserTicket = new UserTicket();
        mockUserTicket.setId(1);
        mockUserTicket.setLottery(mockLottery);
        mockUserTicket.setUserDetail(mockUserDetail);

        when(userRepository.findById(any())).thenReturn(Optional.of(mockUserDetail));
        when(lotteryRepository.findById(any())).thenReturn(Optional.of(mockLottery));
        when(lotteryRepository.findAvailableLotteryTicket()).thenReturn(Collections.singletonList(mockLottery));
        when(userTicketRepository.save(any())).thenReturn(mockUserTicket);

        ResponseEntity<BuyLotteryResponseDto> responseEntity = userService.buyLotteryTicket(Math.toIntExact(userId), ticketId);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().getId());

    }

    @Test
    @DisplayName("When delete ticket, it should remove userTicket row and return delete ticket number")
    void deleteLotteryTicket() {
        Long userId = 1234567890L;
        Integer ticketId = 123;

        UserTicket userTicket = new UserTicket();
        userTicket.setId(1);

        Lottery lottery = new Lottery();
        lottery.setTicket("123456");


        UserTicketRepository userTicketRepository = mock(UserTicketRepository.class);
        when(userTicketRepository.findByUserDetailIdAndLotteryId(Math.toIntExact(userId), ticketId)).thenReturn(Optional.of(userTicket));

        LotteryRepository lotteryRepository = mock(LotteryRepository.class);
        when(lotteryRepository.findById((long) ticketId)).thenReturn(Optional.of(lottery));


        UserService userService = new UserService(userRepository, lotteryRepository, userTicketRepository);

        ResponseEntity<DeleteLotteryResponseDto> responseEntity = userService.deleteLotteryTicket(Math.toIntExact(userId), ticketId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        DeleteLotteryResponseDto responseDto = responseEntity.getBody();
        assertEquals(lottery.getTicket(), responseDto.getTicket());
    }
}