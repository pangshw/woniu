package ${package}.infra.repository.impl;

import com.woniu.domain.BaseMapper;
import com.woniu.domain.impl.BaseRepositoryImpl;
import ${package}.domain.entity.${entity};
import ${package}.domain.repository.${domain}Repository;
import ${package}.infra.mapper.${domain}Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* ${name}
*/
@Service
public class ${domain}RepositoryImpl extends BaseRepositoryImpl<${entity}> implements ${domain}Repository {

    @Autowired
    private ${domain}Mapper mapper;

    @Override
    public BaseMapper<${entity}> getMapper() {
        return mapper;
    }
}
