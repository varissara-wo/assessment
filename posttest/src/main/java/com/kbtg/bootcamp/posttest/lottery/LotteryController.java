package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LotteryController {
    private final LotteryService lotteryService;

    public LotteryController (LotteryService lotteryService){
        this.lotteryService = lotteryService;
    }

    @GetMapping("/lotteries")
    public ResponseEntity<List<String>> getLotteries(){
        return lotteryService.getLotteries();
    }

    @PostMapping("/admin/lotteries")
    public ResponseEntity<LotteryResponseDto> addLotteries (@Validated @RequestBody LotteryRequestDto requestDto){
       return lotteryService.addLotteries(requestDto);
    }
}

