package com.leyou.item.web;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 请求方式：GET
     * 请求路径：http://api.leyou.com/api/brand/page?key=&page=1&rows=5&sortBy=id&desc=true
     * 返回数据: PageResult封装的结果集
     * 接收数据：page,rows,sortBy,desc, key
     */

    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", defaultValue = "") String key
    ){
        PageResult<Brand> result = brandService.queryBrandByPage(page, rows, sortBy, desc, key);
        return ResponseEntity.ok(result);
    }

    /**
     *新增品牌
     */

    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids") List<Long> cids){
        brandService.saveBrand(brand, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除品牌
     * 请求方式：DELETE
     * 请求路径：http://api.leyou.com/api/item/brand/delete?bid=1115
     * 返回数据：成功状态
     * 接收数据：id=1115
     */
    @DeleteMapping("delete")
    public ResponseEntity<Void> deleteBrand(@RequestParam("bid") Long bid){
        brandService.deleteBrandById(bid);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

}

