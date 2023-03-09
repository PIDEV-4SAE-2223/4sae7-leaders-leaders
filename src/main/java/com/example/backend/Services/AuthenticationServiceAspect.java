package com.example.backend.Services;

//@Slf4j
//@Aspect
//@Component
//@RequiredArgsConstructor
public class AuthenticationServiceAspect {

//    private final AuthenticationStatistics authenticationStatistics;
//
//    @Pointcut("execution(public * com.example.backend.Services.AuthenticationService.authenticate(..))")
//    public void authenticate() {
//    }
//
//    @Before("authenticate()")
//    public void logAuthenticationAttempt(JoinPoint joinPoint) {
//        log.info("User attempting to authenticate: {}", joinPoint.getArgs()[0]);
//    }
//
//    @AfterReturning(pointcut = "authenticate()", returning = "result")
//    public void logAuthenticationResult(AuthentificationResponse result) {
//        if (result != null && result.getToken() != null) {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            User user = (User) authentication.getPrincipal();
//            authenticationStatistics.incrementSuccessfulLogins();
//            log.info("User successfully authenticated: {}", user.getEmail());
//        } else {
//            authenticationStatistics.incrementFailedLogins();
//            log.warn("Failed authentication attempt");
//        }
//    }
//
//    @Before("execution(public * com.example.backend.Services.AuthenticationService.*(..)) && args(request)")
//    public void logAuthenticationRequest(AuthentificationRequest request) {
//        log.info("Authentication request received for email: {}", request.getEmail());
//    }
//
//    @AfterReturning("execution(public * com.example.backend.Services.AuthenticationService.*(..)) && args(request) && within(com.example.backend.Services.AuthenticationService)")
//    public void logAuthenticationRequestResult(AuthentificationRequest request) {
//        log.info("Authentication request processed for email: {}", request.getEmail());
//    }
//
//    @Before("execution(public * com.example.backend.Services.AuthenticationService.*(..)) && args(user)")
//    public void logUserLockingAttempt(User user) {
//        log.info("User locking attempt for email: {}", user.getEmail());
//    }
//
//    @AfterReturning(pointcut = "execution(public * com.example.backend.Services.AuthenticationService.*(..)) && args(user)", returning = "result")
//    public void logUserLockingResult(User user, boolean result) {
//        if (result) {
//            authenticationStatistics.incrementLockedAccounts();
//            log.info("User account locked for email: {}", user.getEmail());
//        } else {
//            log.info("User account not locked for email: {}", user.getEmail());
//        }
//    }
}
