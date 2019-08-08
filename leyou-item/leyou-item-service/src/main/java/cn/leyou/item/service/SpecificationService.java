package cn.leyou.item.service;

import cn.leyou.item.pojo.SpecGroup;
import cn.leyou.item.pojo.SpecParam;

import java.util.List;

public interface SpecificationService {
    List<SpecGroup> queryGroupsByCid(Long cid);

    List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching);

    int saveGroup(SpecGroup specGroup);

    int delGroupById(Long id);

    int saveParam(SpecParam param);

    int deleteParam(Long id);

    List<SpecGroup> querySpecsByCid(Long cid);
}
