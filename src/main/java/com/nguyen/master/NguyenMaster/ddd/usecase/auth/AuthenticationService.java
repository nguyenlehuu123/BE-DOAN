package com.nguyen.master.NguyenMaster.ddd.usecase.auth;

import com.nguyen.master.NguyenMaster.core.common.BaseService;
import com.nguyen.master.NguyenMaster.core.common.ErrorMessage;
import com.nguyen.master.NguyenMaster.core.constant.Constants;
import com.nguyen.master.NguyenMaster.core.constant.SystemMessageCode;
import com.nguyen.master.NguyenMaster.core.constant.enums.Role;
import com.nguyen.master.NguyenMaster.core.exceptions.rest.Error400Exception;
import com.nguyen.master.NguyenMaster.core.util.JwtUtils;
import com.nguyen.master.NguyenMaster.core.util.convert.JsonConverter;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.AccountRedis;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.auth.Tokens;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.auth.Users;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.RegisterUserRequest;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.response.auth.AuthenticationResponse;
import com.nguyen.master.NguyenMaster.ddd.repositoty.auth.TokenRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.auth.UserRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService extends BaseService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private JsonConverter jsonConverter;


    @Value("${token.login.prefix}")
    private String tokenLoginPrefix;

    @Value("${bezkoder.app.accessTokenExpirationMs}")
    private long accessTokenExpirationMs;


    @Value("${bezkoder.app.refreshTokenExpirationMs}")
    private long refreshTokenExpirationMs;

    public AuthenticationResponse registerUser(RegisterUserRequest registerUserRequest) {

        var userCheckRegister = userRepository.findByEmail(registerUserRequest.getEmail());
        if (!ObjectUtils.isEmpty(userCheckRegister)) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.GMAIL_HAS_BEEN_REGISTER));
            throw new Error400Exception(Constants.E400, errorMessages);
        }

        var user = Users.builder()
                .email(registerUserRequest.getEmail())
                .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                .role(Role.USER)
                .avatar("avatar")
                .build();
        Users saveUser = userRepository.save(user);

        var accessToken = jwtUtils.generatorToken(String.valueOf(saveUser.getUserId()));
        var refreshToken = jwtUtils.generatorRefreshTokenToken(String.valueOf(saveUser.getUserId()));

        Tokens token = Tokens.builder()
                .refreshToken(refreshToken)
                .users(saveUser)
                .build();

        tokenRepository.save(token);

        //save redis
        HttpServletRequest servletRequest =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String redisKey = String.format(tokenLoginPrefix, saveUser.getUserId(), servletRequest.getRemoteAddr());
        AccountRedis accountRedis = new AccountRedis();
        accountRedis.setUserId(saveUser.getUserId());
        accountRedis.setAccessToken(accessToken);
        redisTemplate.opsForValue().set(redisKey, accountRedis);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expireTime(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .build();
    }

    public AuthenticationResponse authenticate(RegisterUserRequest request) {
        Users user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new Error400Exception(Constants.E404, List.of(buildErrorMessage(SystemMessageCode.GMAIL_NOT_REGISTER))));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.INCORRECT_PASSWORD));
            throw new Error400Exception(Constants.E405, errorMessages);
        }

        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authenticationManager.authenticate(userToken);
        var accessToken = jwtUtils.generatorToken(String.valueOf(user.getUserId()));
        String refreshToken = tokenRepository.findByUserIdEAndExpi(user.getUserId());

        HttpServletRequest servletRequest =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String redisKey = String.format(tokenLoginPrefix, user.getUserId(), servletRequest.getRemoteAddr());
        AccountRedis accountRedis = new AccountRedis();
        accountRedis.setUserId(user.getUserId());
        accountRedis.setAccessToken(accessToken);
        redisTemplate.opsForValue().set(redisKey, accountRedis);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .role(user.getRole())
                .avatar(user.getAvatar())
                .expireTime(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .build();
    }

    public AuthenticationResponse refreshToken() {
        HttpServletRequest servletRequest =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String authHeader = servletRequest.getHeader("Authorization");

        final String token = StringUtils.isNotEmpty(authHeader) && authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;
        if (StringUtils.isNotEmpty(token) && jwtUtils.isValidateToken(token)) {
            String userId = jwtUtils.getUserIdFromJwt(token);
            var accessToken = jwtUtils.generatorToken(String.valueOf(userId));
            String refreshToken = tokenRepository.findByUserIdEAndExpi(new BigInteger(userId));

            //save redis
            String redisKey = String.format(tokenLoginPrefix, userId, servletRequest.getRemoteAddr());
            AccountRedis accountRedis = jsonConverter.convertValueToObject(redisTemplate.opsForValue().get(redisKey), AccountRedis.class);
            if (!ObjectUtils.isEmpty(accountRedis)) {
                String redisDelete = String.format(tokenLoginPrefix, userId, Constants.START);
                redisTemplate.delete(redisDelete);
                accountRedis.setAccessToken(accessToken);
                redisTemplate.opsForValue().set(redisKey, accountRedis);
            }

            AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .expireTime(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                    .build();
            return authenticationResponse;
        }
        return null;
    }
}
