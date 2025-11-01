import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.hongchu.common.util.SimplePasswordEncoder;
import org.junit.jupiter.api.Assertions;

public class TestPasswordEnc {
    public static void main(String[] args) {
        SimplePasswordEncoder passwordEncoder = new SimplePasswordEncoder();
        String salt1 = passwordEncoder.generateSalt();
        String password1 = "123456";
        String hash1 = passwordEncoder.encrypt(password1, salt1);
        System.out.println("salt1:" + salt1);
        System.out.println("hash1:" + hash1);
        System.out.println("hash1:" + passwordEncoder.verify(password1, hash1, salt1));


    }
}
