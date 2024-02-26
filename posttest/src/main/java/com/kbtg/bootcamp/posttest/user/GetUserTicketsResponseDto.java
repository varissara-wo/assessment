package com.kbtg.bootcamp.posttest.user;

import java.util.List;

public class GetUserTicketsResponseDto {

    private final List<String> tickets;
    private final int count;
    private final int cost;


    public GetUserTicketsResponseDto(List<String> tickets, int count, int cost) {
        this.tickets = tickets;
        this.count = count;
        this.cost = cost;

    }

    public List<String> getTickets() {
        return tickets;
    }

    public int getCost() {
        return cost;
    }

    public int getCount() {
        return count;
    }
}
