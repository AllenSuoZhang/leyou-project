package cn.leyou.item.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("category")
public interface CategoryApi {
    /**
     * 商品分类名称查询
     * @param ids
     * @return
     */
    @GetMapping("names")
    public List<String> queryCategoryNamesById(@RequestParam("ids")List<Long> ids);
}
