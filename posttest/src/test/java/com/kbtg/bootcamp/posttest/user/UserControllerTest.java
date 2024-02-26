package com.kbtg.bootcamp.posttest.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    MockMvc mockMvc;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        UserController userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("When performing the GET request on /users/2/lotteries, it should return the list of all tickets, cost, and count")
    void getUserLotteries() throws Exception {
        List<String> tickets = Arrays.asList("123456", "456544", "789546");
        int count = tickets.size();
        int cost = 100;
        GetUserTicketsResponseDto responseDto = new GetUserTicketsResponseDto(tickets, count, cost);
        ResponseEntity<GetUserTicketsResponseDto> responseEntity = new ResponseEntity<>(responseDto, HttpStatus.OK);
        when(userService.getUserTickets(any(Integer.class))).thenReturn(responseEntity);


        mockMvc.perform(get("/users/2/lotteries", 123))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets", hasSize(3)))
                .andExpect(jsonPath("$.count").value(3))
                .andExpect(jsonPath("$.cost").value(100));
    }

    @Test
    @DisplayName("When performing the POST request on /users/2/lotteries/2, it should return the buy ticket id from the userTicket table")
    void buyLotteries() throws Exception {
        int userId = 2;
        int ticketId = 2;
        int responseId = 5;
        BuyLotteryResponseDto responseDto = new BuyLotteryResponseDto(responseId);
        ResponseEntity<BuyLotteryResponseDto> responseEntity = new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        when(userService.buyLotteryTicket(eq(userId), eq(ticketId))).thenReturn(responseEntity);

        mockMvc.perform(post("/users/{userId}/lotteries/{ticketId}", userId, ticketId))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(responseId));
    }

    @Test
    @DisplayName("When performing the DELETE request on /users/2/lotteries/2, it should return delete ticket number")
    void deleteLotteries() throws Exception {
        int userId = 2;
        int ticketId = 2;
        String responseTicket = "789456";
        DeleteLotteryResponseDto responseDto = new DeleteLotteryResponseDto(responseTicket);
        ResponseEntity<DeleteLotteryResponseDto> responseEntity = new ResponseEntity<>(responseDto, HttpStatus.OK);
        when(userService.deleteLotteryTicket(eq(userId), eq(ticketId))).thenReturn(responseEntity);

        mockMvc.perform(delete("/users/{userId}/lotteries/{ticketId}", userId, ticketId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticket").value(responseTicket));
    }
}