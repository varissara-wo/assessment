package com.kbtg.bootcamp.posttest.user;

public class DeleteLotteryResponseDto {
    private final String ticket;

    public DeleteLotteryResponseDto(String ticket) {
        this.ticket = ticket;
    }

    public String getTicket() {
        return ticket;
    }


}
