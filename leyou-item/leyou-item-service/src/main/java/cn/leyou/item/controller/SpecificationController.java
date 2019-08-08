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
    public ResponseEntity<List<SpecParam>> queryParams(@RequestParam(value = "gid", required = false)Long gid,
                                                       @RequestParam(value = "cid", required = false)Long cid,
                                                       @RequestParam(value = "generic", required = false)Boolean generic,
                                                       @RequestParam(value = "searching", required = false)Boolean searching){
        List<SpecParam> params = this.specificationService.queryParams(gid, cid, generic, searching);
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
        this.specificationService.saveGroup(group);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 编辑规格组数据
     * @param group
     * @return
     */
    @PutMapping("group")
    public ResponseEntity<Void> editGroup(@RequestBody SpecGroup group){
        this.specificationService.saveGroup(group);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 根据规格组id删除规格组以及所属的规格参数
     * @param id
     * @return
     */
    @DeleteMapping("group/{id}")
    public ResponseEntity<Void> delGroupById(@PathVariable("id") Long id){
        this.specificationService.delGroupById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 添加规格参数数据
     * @param param
     * @return
     */
    @PostMapping("param")
    public ResponseEntity<Void> addParam(@RequestBody SpecParam param){
        this.specificationService.saveParam(param);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 编辑规格参数数据
     * @param param
     * @return
     */
    @PutMapping("param")
    public ResponseEntity<Void> editParam(@RequestBody SpecParam param){
        this.specificationService.saveParam(param);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 根据规格参数id删除
     * @param id
     * @return
     */
    @DeleteMapping("param/{id}")
    public ResponseEntity<Void> deleteParam(@PathVariable("id") Long id){
        this.specificationService.deleteParam(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 根据分类id查询规格组以及规格组中的规格参数
     * @param cid
     * @return
     */
    @GetMapping("group/param/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecsByCid(@PathVariable("cid") Long cid){
        List<SpecGroup> list = this.specificationService.querySpecsByCid(cid);
        if(list == null || list.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }
}
