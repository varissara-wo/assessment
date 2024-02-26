package com.kbtg.bootcamp.posttest.lottery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LotteryServiceTest {

    @Mock
    private LotteryRepository lotteryRepository;

    @InjectMocks
    private LotteryService lotteryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getLotteries() {
        Lottery lottery1 = new Lottery();
        lottery1.setTicket("123456");
        Lottery lottery2 = new Lottery();
        lottery2.setTicket("789012");
        List<Lottery> mockLotteries = Arrays.asList(lottery1, lottery2);

        when(lotteryRepository.findAvailableLotteryTicket()).thenReturn(mockLotteries);

        ResponseEntity<List<String>> responseEntity = lotteryService.getLotteries();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
        assertEquals("123456", responseEntity.getBody().get(0));
        assertEquals("789012", responseEntity.getBody().get(1));
    }

    @Test
    void addLotteries() {

        LotteryRequestDto requestDto = new LotteryRequestDto("999999", 80, 3);
        requestDto.setTicket("999999");
        requestDto.setPrice(80);
        requestDto.setAmount(3);

        ResponseEntity<LotteryResponseDto> responseEntity = lotteryService.addLotteries(requestDto);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("999999", responseEntity.getBody().getTicket());
    }
}
