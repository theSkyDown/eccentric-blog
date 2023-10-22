package ink.eccentric.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * jwt工具类
 * @author eccentric
 */
public class JwtUtil {

    /**
     * 有效期为 60 * 60 *1000  六个小时
     */
    public static final Long TIME = 6 * 60 * 60 *1000L;

    /**
     * 签名
     */
    public static final String SIGNATURE_KEY = "ECCENTRIC_BLOG_JWT";


    /**
     * 生成Jwt
     * @param subject 主题
     * @param claims 内容
     * @return
     */
    public static String createJwt(String subject, Map<String, Object> claims){
        JwtBuilder jwtBuilder = Jwts.builder();
        //header
        jwtBuilder.setHeaderParam("typ","JWT").setHeaderParam("alg","HS256");
        //payload
        //主题，内容
        jwtBuilder.setSubject(subject).setClaims(claims);
        //id
        jwtBuilder.setId(UUID.randomUUID().toString());
        //签发人,签发时间，有效时间
        jwtBuilder.setIssuer("Eccentric")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TIME));
        //signature
        jwtBuilder.signWith(SignatureAlgorithm.HS256,generalKey());
        return jwtBuilder.compact();
    }


    /**
     * 生成Jwt
     * @param subject 主题
     * @param claims 内容
     * @return
     */
    public static String createJwt(String subject, Map<String, Object> claims,Long time){
        JwtBuilder jwtBuilder = Jwts.builder();
        //header
        jwtBuilder.setHeaderParam("typ","JWT").setHeaderParam("alg","HS256");
        //payload
        //主题，内容
        jwtBuilder.setSubject(subject).setClaims(claims);
        //id
        jwtBuilder.setId(UUID.randomUUID().toString());
        //签发人,签发时间，有效时间
        jwtBuilder.setIssuer("Eccentric")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + time));
        //signature
        jwtBuilder.signWith(SignatureAlgorithm.HS256,generalKey());
        return jwtBuilder.compact();
    }

    public static Claims parseJwt(String jwt){
        return Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(jwt).getBody();
    }


    /**
     * 签名加密
     * @return
     */
    private static byte[] generalKey(){
        //设置编码格式
        byte[] keyBytes = SIGNATURE_KEY.getBytes(StandardCharsets.UTF_8);
        // 限制密钥长度为16字节
        byte[] validKeyBytes = Arrays.copyOf(keyBytes, 24);

        //加密算法
        String algorithm = "AES";

        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            SecretKeySpec secretKeySpec = new SecretKeySpec(validKeyBytes,algorithm);
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
            result = cipher.doFinal();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
