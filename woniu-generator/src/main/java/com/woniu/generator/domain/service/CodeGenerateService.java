package com.woniu.generator.domain.service;

import java.util.List;
import java.util.Map;

public interface CodeGenerateService {

    /**
     * 生成至目录
     *
     * @param ids
     */
    void execute(List<Long> ids);

    /**
     * 生成至目录
     *
     * @param mataId
     */
    void execute(Long mataId);

    /**
     * 下载
     *
     * @param ids
     * @return
     */
    byte[] download(List<Long> ids);

    /**
     * 预览
     *
     * @param mataId
     * @return
     */
    Map<String, String> preview(Long mataId);

    /**
     * 数据类型转java类型
     *
     * @param dataType
     * @param size
     * @return
     */
    String convertToJavaType(String dataType, Integer size);
}
