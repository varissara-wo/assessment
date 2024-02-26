package com.kbtg.bootcamp.posttest.lottery;

public class LotteryResponseDto {
    private final String  ticket;

    public LotteryResponseDto(String ticket) {
        this.ticket = ticket;
    }

    public String getTicket() {
        return ticket;
    }
}
