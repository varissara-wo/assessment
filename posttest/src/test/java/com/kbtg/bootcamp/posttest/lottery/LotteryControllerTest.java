package com.kbtg.bootcamp.posttest.lottery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;

@ExtendWith(MockitoExtension.class)
class LotteryControllerTest {

    MockMvc mockMvc;

    @Mock
    private LotteryService lotteryService;

    @BeforeEach
    void setUp() {
        LotteryController lotteryController = new LotteryController(lotteryService);
        mockMvc = MockMvcBuilders.standaloneSetup(lotteryController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("When performing GET on /lotteries, should return a list of available ticket numbers")
    void getLotteries() throws Exception {
        List<String> mockLotteryTickets = Arrays.asList("398746", "987309", "111222", "111222");
        ResponseEntity<List<String>> responseEntity = new ResponseEntity<>(mockLotteryTickets, HttpStatus.OK);
        when(lotteryService.getLotteries()).thenReturn(responseEntity);
        mockMvc.perform(get("/lotteries"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("398746"))
                .andExpect(jsonPath("$[1]").value("987309"))
                .andExpect(jsonPath("$[2]").value("111222"));
    }

    @Test
    @DisplayName("When performing POST on /admin/lotteries, should and new lottery and return ticket number")
    void createLotteries() throws Exception {
        String requestBody = "{\"ticket\": \"123456\", \"price\": 10.0, \"amount\": 3}";

        LotteryResponseDto mockResponseDto = new LotteryResponseDto("123456");
        ResponseEntity<LotteryResponseDto> mockResponseEntity = new ResponseEntity<>(mockResponseDto, HttpStatus.CREATED);
        when(lotteryService.addLotteries(any(LotteryRequestDto.class))).thenReturn(mockResponseEntity);

        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ticket").value("123456"));
    }

}