package asia.dyh1319.oj;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 主类测试
 */
@SpringBootTest
class MainApplicationTests {
    
    @Test
    void contextLoads() {
        System.out.println(DigestUtils.sha256Hex("123"));
    }
    
}
