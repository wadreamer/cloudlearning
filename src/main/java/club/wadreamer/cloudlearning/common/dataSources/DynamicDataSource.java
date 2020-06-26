package club.wadreamer.cloudlearning.common.dataSources;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

/**
 * 动态数据源
 *
 * @author fuce
 * @ClassName: DynamicDataSource
 * @date 2019-12-07 18:39
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    public DynamicDataSource(javax.sql.DataSource dataSource, Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(dataSource);//设置默认数据源
        super.setTargetDataSources(targetDataSources);//设置多数据源的集合

        //将targetDataSources赋值给resolvedDataSources(AbstractRoutingDataSource的成员属性)
        super.afterPropertiesSet();
    }

    /*
     * determineCurrentLookupKey()-->获取当前数据源在map中的key值
     * determineTargetDataSource()-->动态获取当前数据源
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }

}
