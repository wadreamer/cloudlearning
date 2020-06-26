package club.wadreamer.cloudlearning;

import club.wadreamer.cloudlearning.model.custom.CourseReviewLog;
import club.wadreamer.cloudlearning.service.ReviewLogService;
import club.wadreamer.cloudlearning.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CloudlearningApplicationTests {

    @Autowired
    private ReviewLogService reviewLogService;

    @Test
    void contextLoads() {
    }

    @Test
    public void getUnpassList(){
        
    }

}
