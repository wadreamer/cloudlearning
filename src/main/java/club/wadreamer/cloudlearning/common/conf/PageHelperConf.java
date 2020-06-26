package club.wadreamer.cloudlearning.common.conf;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @ClassName PageHelperConf
 * @Description TODO
 * @Author bear
 * @Date 2020/2/29 10:08
 * @Version 1.0
 **/
@Configuration
public class PageHelperConf {
    /**
     * 分页插件处理
     *
     * @return
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();

        properties.setProperty("offsetAsPageNum", "true");

        //使用RowBounds分页会进行 count 查询
        properties.setProperty("rowBoundsWithCount", "true");

        //分页合理化
        properties.setProperty("reasonable", "true");

        //配置mysql数据库的方言
        properties.setProperty("dialect", "mysql");

        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
