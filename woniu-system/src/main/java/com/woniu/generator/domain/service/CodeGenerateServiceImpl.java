package com.woniu.generator.domain.service;

import com.woniu.generator.config.CodeGenerateConfig;
import com.woniu.generator.domain.dto.MetaDTO;
import com.woniu.generator.domain.repository.DevMetadataRepository;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class CodeGenerateServiceImpl implements CodeGenerateService {

    private Configuration configuration;
    private static final Logger log = LoggerFactory.getLogger(CodeGenerateServiceImpl.class);

    @Autowired
    DevMetadataRepository metadataRepository;
    @Autowired
    CodeGenerateConfig codeGenerateConfig;

    public CodeGenerateServiceImpl() throws IOException {
        // step1 创建freeMarker配置实例
        configuration = new Configuration(Configuration.VERSION_2_3_28);
        // step2 获取模版路径
//        log.info(GeneratorApplication.class.getClassLoader().getResource(".").getPath());
//        log.info(GeneratorApplication.class.getClassLoader().getResource("." + File.separator + "templates").getPath());
//        String templatePath = GeneratorApplication.class.getClassLoader().getResource("./templates").getPath();
//        configuration.setDirectoryForTemplateLoading(new File(templatePath));

        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding(Charset.forName("UTF-8").name());
        configuration.setTemplateLoader(new ClassTemplateLoader(CodeGenerateServiceImpl.class, "/templates/"));
    }

    /**
     * 生成至自定义路径
     */
    @Override
    public void execute(List<Long> ids) {
        for (Long id : ids) {
            execute(id);
        }
    }

    /**
     * 生成至自定义路径
     */
    @Override
    public void execute(Long mataId) {
        MetaDTO metaDTO = metadataRepository.getMetaById(mataId);
        if (metaDTO == null) return;

        try {
            MetaDTO model = prepare(metaDTO);
            Map<String, Object> templateModel = toTemplateModel(model);

            List<String> templates = getTemplateList();
            for (String template : templates) {
                String fileName = getFileName(template, model);
                fileName = codeGenerateConfig.getOutputPath() + fileName;
                writeTemplate(template, templateModel, fileName);
            }
        } catch (Exception err) {
            log.error("代码生成", err);
        }
    }

    /**
     * 下载方式
     *
     * @param ids
     * @return
     */
    @Override
    public byte[] download(List<Long> ids) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (Long id : ids) {
            execute(id, zip);
        }
        IOUtils.closeQuietly(zip, null);
        return outputStream.toByteArray();
    }

    /**
     * 生成至zip文件
     */
    private void execute(Long mataId, ZipOutputStream zip) {
        MetaDTO metaDTO = metadataRepository.getMetaById(mataId);
        if (metaDTO == null) return;

        try {
            MetaDTO model = prepare(metaDTO);
            Map<String, Object> templateModel = toTemplateModel(model);

            List<String> templates = getTemplateList();
            for (String template : templates) {

                String content = writeTemplate(template, templateModel);
                String fileName = getFileName(template, model);
                if (fileName.startsWith(File.separator)) {
                    fileName = fileName.substring(1);
                }

                // 添加到zip
                zip.putNextEntry(new ZipEntry(fileName));
                zip.write(content.getBytes());
//                IOUtils.write(content, zip, "UTF-8");
                zip.flush();
                zip.closeEntry();
            }
        } catch (Exception err) {
            log.error("代码生成", err);
        }
    }

    /**
     * 预览
     *
     * @param mataId
     */
    @Override
    public Map<String, String> preview(Long mataId) {
        Map<String, String> map = new LinkedHashMap<>();
        MetaDTO metaDTO = metadataRepository.getMetaById(mataId);
        if (metaDTO == null) return map;

        try {
            MetaDTO model = prepare(metaDTO);
            Map<String, Object> templateModel = toTemplateModel(model);

            List<String> templates = getTemplateList();
            for (String template : templates) {
                String content = writeTemplate(template, templateModel);
                String key = template.replace(".ftl", "");
                if (key.contains("/")) {
                    key = key.substring(key.lastIndexOf("/") + 1);
                }
                map.put(key, content);
            }
        } catch (Exception err) {
            log.error("代码生成", err);
        }
        return map;
    }

    /**
     * 数据预处理，构造成生成需要的DTO
     */
    private MetaDTO prepare(MetaDTO tableModel) {
        //整理数据
        String domain = tableModel.getClassName();
        if (domain.endsWith("DO")) {
            domain = domain.substring(0, domain.lastIndexOf("DO"));
        }
        String camelhump = domain;
        if (camelhump != null && camelhump.length() > 1) {
            camelhump = camelhump.substring(0, 1).toLowerCase() + camelhump.substring(1);
        }

        List<String> baseColumns = Arrays.asList("id", "version", "createTime", "updateTime");
        List<MetaDTO.MetaColumnDTO> fields = new ArrayList<>();
        for (MetaDTO.MetaColumnDTO f : tableModel.getColumns()) {
            if (f.getJavaField() == null || f.getJavaField().length() == 0) continue;
            if (baseColumns.contains(f.getJavaField())) continue;

            String method = f.getJavaField().substring(0, 1).toUpperCase() + f.getJavaField().substring(1);
            f.setJavaGetCode("get" + method);
            f.setJavaSetCode("set" + method);
            f.setJavaType(convertToJavaType(f.getDataType(), f.getSize()));

            //字符串默认长度50
            if ("String".equals(f.getDataType()) && f.getSize() == null) {
                f.setSize(50);
            }
            if ("Integer".equals(f.getDataType()) && f.getSize() == null) {
                f.setSize(32);
            }

            fields.add(f);
        }

        MetaDTO model = new MetaDTO();
        BeanUtils.copyProperties(tableModel, model);
        model.setDomain(domain);
        model.setCamelhump(camelhump);
        model.setColumns(fields);
        return model;
    }

    /**
     * 模板中包含的参数
     */
    private Map<String, Object> toTemplateModel(MetaDTO tableModel) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("package", tableModel.getPackageName());
        dataMap.put("name", tableModel.getName());
        dataMap.put("entity", tableModel.getClassName());
        dataMap.put("table", tableModel.getTableName());
        dataMap.put("domain", tableModel.getDomain());
        dataMap.put("camelhump", tableModel.getCamelhump());
        dataMap.put("fields", tableModel.getColumns());
        return dataMap;
    }

    /**
     * 转化成java数据类型
     *
     * @param dataType
     * @param size
     * @return
     */
    @Override
    public String convertToJavaType(String dataType, Integer size) {
        switch (dataType) {
            case "String":
                return "String";

            case "Integer":
                if (size != null && size == 64) {
                    return "Long";
                } else {
                    return "Integer";
                }

            case "Float":
                return "Float";

            case "Date":
            case "DateTime":
            case "Time":
                return "Date";

//            case "DateTime":
//                return "Timestamp";
//            case "Time":
//                return "Time";

            case "Blob":
                return "byte[]";

            case "Decimal":
                return "BigDecimal";

            case "Text":
                return "String";

            default:
                return "String";
        }
    }

    /**
     * 保存至物理文件
     */
    private void writeTemplate(String templateName, Map<String, Object> dataModel, String filePath) throws IOException {

        Template template = configuration.getTemplate(templateName);

        File f = new File(filePath);
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }

        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
            template.process(dataModel, writer);
            writer.flush();
            log.info("代码生成 " + filePath + " 文件创建成功");
        } catch (Exception err) {
            log.error("代码生成", err);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 保存到字符串
     */
    private String writeTemplate(String templateName, Map<String, Object> dataModel) throws IOException {

        Template template = configuration.getTemplate(templateName);
        StringWriter writer = new StringWriter();
        try {
            template.process(dataModel, writer);
            writer.flush();
            return writer.toString();
        } catch (Exception err) {
            log.error("代码生成", err);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    private List<String> getTemplateList() {
        List<String> templates = new ArrayList<>();
        templates.add("java/entity.java.ftl");
        templates.add("java/mapper.java.ftl");
        templates.add("java/repository.java.ftl");
        templates.add("java/repositoryImpl.java.ftl");
        templates.add("java/controller.java.ftl");
        templates.add("xml/mapper.xml.ftl");
        templates.add("sql/mysql.sql.ftl");

        return templates;
    }

    /**
     * 生成文件的路径，不包括根目录
     */
    private String getFileName(String template, MetaDTO model) {

        String javaPath = File.separator + "main" + File.separator + "java"
                + File.separator + model.getPackageName().replace(".", File.separator);
        if (template.contains("entity.java.ftl")) {
            return javaPath + File.separator + "domain" + File.separator + "entity"
                    + File.separator + model.getClassName() + ".java";
        }
        if (template.contains("mapper.java.ftl")) {
            return javaPath + File.separator + "infra" + File.separator + "mapper"
                    + File.separator + model.getDomain() + "Mapper.java";
        }
        if (template.contains("repository.java.ftl")) {
            return javaPath + File.separator + "domain" + File.separator + "repository"
                    + File.separator + model.getDomain() + "Repository.java";
        }
        if (template.contains("repositoryImpl.java.ftl")) {
            return javaPath + File.separator + "infra" + File.separator + "repository"
                    + File.separator + "impl" + File.separator + model.getDomain() + "RepositoryImpl.java";
        }
        if (template.contains("controller.java.ftl")) {
            return javaPath + File.separator + "api" + File.separator + "controller"
                    + File.separator + model.getDomain() + "Controller.java";
        }
        if (template.contains("mapper.xml.ftl")) {
            String xmlPath = File.separator + "main" + File.separator + "resources"
                    + File.separator + "mapper";
            if (model.getPackageName().contains(".")) {
                Integer beginIndex = model.getPackageName().lastIndexOf(".") + 1;
                xmlPath += File.separator + model.getPackageName().substring(beginIndex);
            } else {
                xmlPath += File.separator + model.getPackageName();
            }
            xmlPath += File.separator + model.getDomain() + "Mapper.xml";
            return xmlPath;
        }
        if (template.contains("mysql.sql.ftl")) {
            //sql ddl
            String sqlPath = File.separator + "sql";
            if (model.getPackageName().contains(".")) {
                Integer beginIndex = model.getPackageName().lastIndexOf(".") + 1;
                sqlPath += File.separator + model.getPackageName().substring(beginIndex);
            } else {
                sqlPath += File.separator + model.getPackageName();
            }
            sqlPath += File.separator + model.getTableName() + ".sql";
            return sqlPath;
        }
        return null;
    }
}
