package cn.leyou.item.mapper;

import cn.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {
    /**
     * 新增商品分类和品牌中间表数据
     * @param cid 商品分类id
     * @param bid 品牌id
     * @return
     */
    @Insert("insert into tb_category_brand(category_id, brand_id) values (#{cid}, #{bid})")
    Integer insertCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    /**
     * 根据品牌id删除品牌
     * @param bid
     * @return
     */
    @Delete("delete from tb_category_brand where brand_id = #{bid}")
    Integer deleteCategoryAndBrand(@Param("bid") Long bid);

    @Select("select b.* from tb_brand b inner join tb_category_brand cb on cb.brand_id = b.id where cb.category_id = #{cid}")
    List<Brand> queryBrandsByCid(@Param("cid") Long cid);
}
