package com.leyou.item.web;


import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * 请求方式：GET
     * 请求路径：/api/item/category/list?pid=0 网关前缀：/api，网关路由映射：/item
     * 请求参数：pid=0 根据tree组件说明，为父节点id，第一次为0，就是一级目录
     * 返回结果:
     * 根据父节点查询分类商品
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoryListByPid(@RequestParam("pid") Long pid){

        final List<Category> categoryList = categoryService.queryCategoryListByPid(pid);
        return ResponseEntity.ok(categoryList);
//        return ResponseEntity.status(HttpStatus.Ok).body(null);
    }

    /**
     * 根据id查询品牌分类
     * 请求方式：GET
     * 请求路径：http://api.leyou.com/api/item/category/bid/1115
     * 请求参数：bid/1112
     * 返回结果：data
     */

    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryByBrandId(@PathVariable("bid") Long bid){
        final List<Category> categories = categoryService.queryByBrandId(bid);
        return ResponseEntity.ok(categories);
    }
}
