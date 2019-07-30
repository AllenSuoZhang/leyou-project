package cn.leyou.item.service.impl;

import cn.leyou.item.mapper.SpecGroupMapper;
import cn.leyou.item.mapper.SpecParamMapper;
import cn.leyou.item.pojo.SpecGroup;
import cn.leyou.item.pojo.SpecParam;
import cn.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * 根据分类id查询规格组
     * @param cid
     * @return
     */
    @Override
    public List<SpecGroup> queryGroupsByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> groups = this.specGroupMapper.select(specGroup);
        return groups;
    }

    /**
     * 根据条件查询规格参数
     * @param gid
     * @return
     */
    @Override
    public List<SpecParam> queryParams(Long gid) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        return this.specParamMapper.select(param);
    }

    /**
     * 保存规格组
     * @param specGroup
     * @return
     */
    @Transactional
    @Override
    public int saveGroup(SpecGroup specGroup) {
        //规格组id为空，即为添加
        int flag = 0;
        if (StringUtils.isEmpty(specGroup.getId())){
            flag = this.specGroupMapper.insert(specGroup);
        }else {
            flag = this.specGroupMapper.updateByPrimaryKeySelective(specGroup);
        }
        return flag;
    }

    /**
     * 根据规格组id删除规格组以及所属的规格参数
     * @param id
     * @return
     */
    @Transactional
    @Override
    public int delGroupById(Long id) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(id);
        this.specParamMapper.delete(specParam);

        return this.specGroupMapper.deleteByPrimaryKey(id);
    }

    /**
     * 添加或编辑规格参数数据
     * @param param
     * @return
     */
    @Transactional
    @Override
    public int saveParam(SpecParam param) {
        //规格组id为空，即为添加
        int flag = 0;
        if (StringUtils.isEmpty(param.getId())){
            flag = this.specParamMapper.insert(param);
        }else {
            flag = this.specParamMapper.updateByPrimaryKeySelective(param);
        }
        return flag;
    }

    /**
     * 根据规格参数id删除
     * @param id
     * @return
     */
    @Transactional
    @Override
    public int deleteParam(Long id) {
        return this.specParamMapper.deleteByPrimaryKey(id);
    }
}
