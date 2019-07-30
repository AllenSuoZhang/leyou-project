package cn.leyou.item.controller;

import cn.leyou.item.pojo.Category;
import cn.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据父分类id查询子分类
     * @author yhl
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoriesByPid(@RequestParam(value = "pid", defaultValue = "0")Long pid){
        if (pid == null || pid < 0){
            //400参数不合法
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().build();
        }
        List<Category> categories = this.categoryService.queryCategoriesByPid(pid);
        if (CollectionUtils.isEmpty(categories)){
            //404：资源服务器未找到
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.notFound().build();
        }
        //200:查询成功
        return ResponseEntity.ok(categories);
    }

    /**
     * 根据品牌id查询商品分类
     * @author yhl
     * @param bid
     * @return
     */
    @GetMapping("{bid}")
    public ResponseEntity<List<Category>> queryCategoryByBid(@PathVariable("bid")Long bid){
        List<Category> categories = this.categoryService.queryCategoryByBid(bid);
        if (CollectionUtils.isEmpty(categories)){
            //404：资源服务器未找到
            return ResponseEntity.notFound().build();
        }
        //200:查询成功
        return ResponseEntity.ok(categories);
    }
}
