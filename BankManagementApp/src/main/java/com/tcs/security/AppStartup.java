package com.tcs.security;



import org.springframework.stereotype.Component;

@Component
public class AppStartup {

    @jakarta.annotation.PostConstruct
    public void onApplicationStart() {
        JwtUtil.setLastRestartTimestamp(System.currentTimeMillis());
    }
}
