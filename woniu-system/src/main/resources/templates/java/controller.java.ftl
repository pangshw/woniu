package ${package}.api.controller;

import ${package}.domain.entity.${entity};
import ${package}.domain.repository.${domain}Repository;
import com.woniu.mvc.BaseController;
import com.woniu.mybatis.pagehelper.Page;
import com.woniu.mybatis.pagehelper.PageHelper;
import com.woniu.vo.PageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/${camelhump}")
@Api(tags = "${name}")
public class ${domain}Controller extends BaseController {

    @Autowired
    ${domain}Repository ${camelhump}Repository;

    @ApiOperation("列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    private Page<${entity}> list(${entity} model, PageRequest pageRequest) {
        return PageHelper.doPage(pageRequest.getPageIndex(), pageRequest.getPageSize(),
            () -> ${camelhump}Repository.list(model));
    }

    @ApiOperation("详情")
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    private ${entity} get(@RequestParam Long id) {
        ${entity} obj = ${camelhump}Repository.findById(id);
        return obj;
    }

    @ApiOperation("保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    private void save(@RequestBody ${entity} model) {
        ${camelhump}Repository.save(model);
    }

    @ApiOperation("删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    private void delete(@RequestParam List<Long> ids) {
        ${camelhump}Repository.deleteByIds(ids);
    }
}
