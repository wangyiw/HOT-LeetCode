package com.yww.code.hot.handcheck;

import java.util.*;

public class rateLimiter {
    /**
     * Limiter, implement a limiter that restricts to at most 10 requests per second
     * Implement a token bucket limiter
     */
    public static class TokenBucket{
        private int capacity;
        private long lastRefillTime;
        private int tokens;
        private final int rate = 10; // tokens per second
        public TokenBucket(int capacity){
            this.capacity = capacity;
            this.tokens = capacity;
            this.lastRefillTime = System.currentTimeMillis(); // last refill time
        }
        public boolean tryAcquire() {
            // try to acquire a token
            refill();
            if (this.tokens > 0) {
                this.tokens--;
                return true;
            }
            return false;
        }
        private void refill() {
            // refill tokens at a certain rate
            long now = System.currentTimeMillis();
            long timePass = now - lastRefillTime;
            int tokensToAdd = (int) (timePass / 1000) * rate;
            if (tokensToAdd > 0) {
                this.tokens = Math.min(capacity, this.tokens + tokensToAdd);
                lastRefillTime = now;
            }
        }
    }
    public static void main(String[] args) {
        TokenBucket tokenBucket = new TokenBucket(10);
        for(int i=0;i<20;i++){
            if(tokenBucket.tryAcquire()){
                System.out.println("Request allowed");
            }else{
                System.out.println("Request denied");
            }
        }
    }

    
}
