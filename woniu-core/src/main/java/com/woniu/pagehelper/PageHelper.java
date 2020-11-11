package com.woniu.mybatis.pagehelper;

import java.util.List;

public class PageHelper {

    public PageHelper() {
    }

    public static <E> Page<E> doPage(int page, int size, Select select) {
        if (page >= 0 && size > 0) {
            com.github.pagehelper.PageHelper.startPage(page, size);
            com.github.pagehelper.Page<E> pageInfo = (com.github.pagehelper.Page<E>) select.doSelect();

            Page<E> result = new Page<>();
            result.setTotalElements(pageInfo.getTotal());
            result.setContent(pageInfo.getResult());
            return result;
        } else {
            return selectAllAsOnePage(select);
        }
    }

    private static <E> Page<E> selectAllAsOnePage(Select select) {
        List list = select.doSelect();

        Page<E> result = new Page<>();
        result.setTotalElements(list.size());
        result.setContent(list);
        return result;
    }
}
