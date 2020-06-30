package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


//异常枚举
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {

    PRICE_CANNOT_BE_NULL(400, "价格不能为空"),
    CATEGORY_NOT_FOND(404, "商品分类未查到"),
    BRAND_NOT_FOND(404,"品牌不存在"),
    BRAND_SAVE_ERROR(500, "新增品牌失败"),
    UPLOAD_FILE_ERROR(500, "上传文件失败"),
    INVALID_FILE_TYPE(500, "无效的文件类型"),
    BID_NOT_FOND(404, "该分类不存在"),
    BRAND_UPDATA_ERROR(500, "删除商品失败"),

    ;
    private int code;
    private String msg;
}
