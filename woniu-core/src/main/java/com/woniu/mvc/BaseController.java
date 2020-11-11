package com.woniu.mvc;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class BaseController {

//    @Autowired
//    BaseRepository<T> repository;
//
//    @RequestMapping(value = "/list", method = RequestMethod.POST)
//    private Page<T> list(T model, PageRequest pageRequest) {
//        return PageHelper.doPage(pageRequest.getPageIndex(), pageRequest.getPageSize(),
//                () -> repository.list(model));
//    }

    /**
     * 生成zip文件
     */
    protected void writeZip(HttpServletResponse response, byte[] data, String fileName) throws IOException {
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
