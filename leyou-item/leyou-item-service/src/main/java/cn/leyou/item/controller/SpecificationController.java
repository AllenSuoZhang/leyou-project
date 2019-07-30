package cn.leyou.item.controller;

import cn.leyou.item.pojo.SpecGroup;
import cn.leyou.item.pojo.SpecParam;
import cn.leyou.item.service.SpecificationService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("spec")
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    /**
     * 根据分类id查询规格组
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupsByCid(@PathVariable("cid") Long cid){
        List<SpecGroup> groups = this.specificationService.queryGroupsByCid(cid);
        if (CollectionUtils.isEmpty(groups)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groups);
    }

    /**
     * 根据条件查询规格参数
     * @param gid
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParams(@RequestParam("gid")Long gid){
        List<SpecParam> params = this.specificationService.queryParams(gid);
        if (CollectionUtils.isEmpty(params)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(params);
    }

    /**
     * 添加规格组数据
     * @param group
     * @return
     */
    @PostMapping("group")
    public ResponseEntity<Void> addGroup(@RequestBody SpecGroup group){
        int flag = this.specificationService.saveGroup(group);
        if (flag >= 1){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * 编辑规格组数据
     * @param group
     * @return
     */
    @PutMapping("group")
    public ResponseEntity<Void> editGroup(@RequestBody SpecGroup group){
        int flag = this.specificationService.saveGroup(group);
        if (flag >= 1){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * 根据规格组id删除规格组以及所属的规格参数
     * @param id
     * @return
     */
    @DeleteMapping("group/{id}")
    public ResponseEntity<Void> delGroupById(@PathVariable("id") Long id){
        int flag = this.specificationService.delGroupById(id);
        if (flag >= 1){
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * 添加或编辑规格参数数据
     * @param param
     * @return
     */
    @PutMapping("param")
    public ResponseEntity<Void> saveParam(@RequestBody SpecParam param){
        int flag = this.specificationService.saveParam(param);
        if (flag >= 1){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * 根据规格参数id删除
     * @param id
     * @return
     */
    @DeleteMapping("param/{id}")
    public ResponseEntity<Void> deleteParam(@PathVariable("id") Long id){
        int flag = this.specificationService.deleteParam(id);
        if (flag >= 1){
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.badRequest().build();
    }
}
