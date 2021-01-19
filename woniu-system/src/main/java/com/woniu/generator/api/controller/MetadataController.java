package com.woniu.generator.api.controller;

import com.woniu.generator.api.controller.dto.SimpleDTO;
import com.woniu.generator.domain.dto.MetaDTO;
import com.woniu.generator.domain.entity.DevMetadata;
import com.woniu.generator.domain.repository.DevMetadataRepository;
import com.woniu.generator.domain.service.CodeGenerateService;
import com.woniu.mvc.BaseController;
import com.woniu.mybatis.pagehelper.Page;
import com.woniu.mybatis.pagehelper.PageHelper;
import com.woniu.vo.PageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/metadata")
@Api(tags = "数据建模")
public class MetadataController extends BaseController {

    @Autowired
    CodeGenerateService codeGeneratService;
    @Autowired
    DevMetadataRepository metadataRepository;

    @ApiOperation("预览")
    @RequestMapping(value = "/gen/preview", method = RequestMethod.POST)
    private List<SimpleDTO> genPreview(@RequestParam Long id) {
        Map<String, String> map = codeGeneratService.preview(id);
        List<SimpleDTO> result = new ArrayList<>();
        for (String key : map.keySet()) {
            SimpleDTO dto = new SimpleDTO();
            dto.setTitle(key);
            dto.setContent(map.get(key));
            result.add(dto);
        }
        return result;
    }

    @ApiOperation("批量生成后下载")
    @RequestMapping(value = "/gen/download", method = RequestMethod.POST)
    private void genDownload(@RequestParam List<Long> ids,
                             HttpServletResponse response) throws IOException {
        byte[] bytes = codeGeneratService.download(ids);
        writeZip(response, bytes, "数据建模.zip");
    }

    @ApiOperation("批量生成至目录")
    @RequestMapping(value = "/gen/local", method = RequestMethod.POST)
    private void genLocal(@RequestParam List<Long> ids) {
        codeGeneratService.execute(ids);
    }

    @ApiOperation("列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    private Page<DevMetadata> list(DevMetadata model, PageRequest pageRequest) {
        return PageHelper.doPage(pageRequest.getPageIndex(), pageRequest.getPageSize(),
                () -> metadataRepository.list(model));
    }

    @ApiOperation("详情")
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    private MetaDTO get(@RequestParam Long id) {
        MetaDTO dto = metadataRepository.getMetaById(id);
        return dto;
    }

    @ApiOperation("保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    private void save(@RequestBody MetaDTO model) {
        metadataRepository.saveMeta(model);
    }

    @ApiOperation("删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    private void delete(@RequestParam List<Long> ids) {
        metadataRepository.deleteMeta(ids);
    }
}
