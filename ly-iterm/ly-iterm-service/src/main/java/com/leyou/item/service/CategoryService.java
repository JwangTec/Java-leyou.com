package com.leyou.item.service;


import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> queryCategoryListByPid(Long pid) {
        Category t = new Category();
        t.setParentId(pid);
        final List<Category> categoryList = categoryMapper.select(t);
        if(CollectionUtils.isEmpty(categoryList)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOND);
        }
        return categoryList;
    }

    public List<Category> queryByBrandId(Long bid) {
        final List<Category> categories = categoryMapper.queryByBrandId(bid);
        if(CollectionUtils.isEmpty(categories)){
            throw new LyException(ExceptionEnum.BID_NOT_FOND);
        }
        return categories;
    }
}
