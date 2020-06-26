package club.wadreamer.cloudlearning.common.base;

/**
 * 抽象类BaseService,web应用中的基本操作，可通过实现该接口，对其重写
* @ClassName: BaseService
* @Description: TODO(Service实现这个)
* @author fuce
* @date 2018年6月3日
*
 */
public interface BaseService<T> {
	
    int deleteByPrimaryKey(String id);

    int insertSelective(T record);

    T selectByPrimaryKey(String id);
   
    int updateByPrimaryKeySelective(T record);
    
//    int updateByExampleSelective(@Param("record") T record, @Param("example") T2 example);
//
//    int updateByExample(@Param("record") T record, @Param("example") T2 example);
//
//    List<T> selectByExample(T2 example);
//
//    long countByExample(T2 example);
//
//    int deleteByExample(T2 example);

}
