package com.kapil.employeeRestDemo.service;

import com.kapil.employeeRestDemo.model.JwtHeader;
import com.kapil.employeeRestDemo.model.JwtPayload;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.List;

@Service
public class JwtServiceImpl {

    private final String secret;
    private final long expirationMillis;
    private final String algo;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtServiceImpl(@Value("${jwt.secret}") String secret,
                          @Value("${jwt.expiry}")long expirationMillis,
                          @Value("${jwt.algo}") String algo) {
        this.secret = secret;
        this.expirationMillis = expirationMillis;
        this.algo = algo;
    }

    public String generateToken(String username, List<String> roles){
        try {

            long now = Instant.now().getEpochSecond();
            long exp = now + (expirationMillis/1000);

            JwtHeader jwtHeader = new JwtHeader(algo);
            JwtPayload jwtPayload = new JwtPayload(username,roles,now,exp);

            String encodedHeader = encode(objectMapper.writeValueAsBytes(jwtHeader));
            String encodedPayload = encode(objectMapper.writeValueAsBytes(jwtPayload));
            String unsignedSignature = encodedHeader+"."+encodedPayload;

            String signature = sign(unsignedSignature);

            return unsignedSignature+"."+signature;

        } catch (Exception exception){
            throw new RuntimeException("Error in generating token");
        }
    }

    protected String sign(String data) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec keySpec =
                new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(keySpec);
        byte[] signatureBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return encode(signatureBytes);
    }

    private String encode(byte[] bytes) {
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }
}
