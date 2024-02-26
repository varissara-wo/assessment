package com.kbtg.bootcamp.posttest.lottery;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record  LotteryRequestDto(
        @NotNull
        @Size(min = 6, max = 6, message = "The ticket must be exactly 6 characters long")
        String ticket,

        @NotNull
        @Min(value = 1, message = "Price must be more than 0")

        Integer price,

        @NotNull
        @Min(value = 1, message = "Amount must be more than 0")
        Integer amount

){


        public void setTicket(String number) {
        }

        public void setPrice(double v) {
        }

        public void setAmount(int i) {
        }
}
