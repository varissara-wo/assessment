package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.exception.InternalServiceException;
import com.kbtg.bootcamp.posttest.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LotteryService {
    private final LotteryRepository lotteryRepository;

    public LotteryService(LotteryRepository lotteryRepository, UserRepository userRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    public ResponseEntity<List<String>> getLotteries(){
        try {
            List<Lottery> lotteries = lotteryRepository.findAvailableLotteryTicket();
            List<String> response = lotteries.stream()
                    .map(Lottery::getTicket)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServiceException("Internal service exception");
        }
    }

    @Transactional
    public ResponseEntity<LotteryResponseDto> addLotteries(LotteryRequestDto requestDto){
        try{
            List<Lottery> newLotteries = new ArrayList<>();
            for(int i = 0; i < requestDto.amount(); i++){
                Lottery lottery = new Lottery();
                lottery.setTicket(requestDto.ticket());
                lottery.setPrice(requestDto.price());
                newLotteries.add(lottery);
            }

            lotteryRepository.saveAll(newLotteries);
            LotteryResponseDto response = new LotteryResponseDto(requestDto.ticket());

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception a){
            throw new InternalServiceException("Internal service exception");
        }
    }
}


