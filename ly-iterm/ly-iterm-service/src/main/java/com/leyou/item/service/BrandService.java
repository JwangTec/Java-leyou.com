package com.leyou.item.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;


    /**
     * 分页查询实现
     * sql:
     * SELECT * FROM tb_brand
     * WHERE `name` LIKE "%x%" OR letter == `x`
     * ORDER BY id DESC
     */
    public PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页: 自动用过滤器将查询拦截拼接limit进行提交事务
        PageHelper.startPage(page, rows);

        //过滤
        final Example example = new Example(Brand.class); //获取其字节码，反射其主键等
        if(StringUtils.isNotBlank(key)){
            //过滤条件
            example.createCriteria().orLike("name", "%"+key+"%")
                    .orEqualTo("letter", key.toUpperCase());
        }

        //排序
        if(StringUtils.isNotBlank(sortBy)){
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }

        //查询
        final List<Brand> brandList = brandMapper.selectByExample(example);
        //强转，用page类，直接用下面的解析获取数据
//        final Page<Brand> brandList = (Page<Brand>) brandMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(brandList)){
            throw new LyException(ExceptionEnum.BRAND_NOT_FOND);
        }

        //解析分页结果
        final PageInfo<Brand> brandPageInfo = new PageInfo<>(brandList);
        final PageResult<Brand> brandPageResult = new PageResult<>(brandPageInfo.getTotal(), brandList);
        return brandPageResult;
    }

    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        //新增品牌
        brand.setId(null);
        final int count = brandMapper.insert(brand);
        if(count != 1){
            throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
        }
//        this.brandMapper.insertSelective(brand);
        //新增中间表 多对多关系 添加到mapper注解上就可
        for (Long cid : cids) {
            final int count1 = brandMapper.insertCategoryBrand(cid, brand.getId());
            if(count1 != 1){
                throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
            }
        }

    }

    @Transactional
    public void deleteBrandById(Long bid) {
        //删除品牌
        final int count = brandMapper.deleteByPrimaryKey(bid);
        if (count != 1){
            throw new LyException(ExceptionEnum.BRAND_UPDATA_ERROR);
        }
    }
}
