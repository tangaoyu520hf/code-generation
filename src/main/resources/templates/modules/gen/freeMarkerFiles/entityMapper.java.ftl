package ${packageName}.${moduleName}.service.mapper;

import ${packageName}.${moduleName}.service.dto.${ClassName}DTO;
import ${packageName}.${moduleName}.model.${ClassName};
import org.mapstruct.Mapper;
/**
 *  ${functionName} 映射mapper
 * @author ${functionAuthor}
 * @date ${functionVersion}
 */
@Mapper(componentModel = "spring", uses = {})
public interface ${ClassName}Mapper {
    /**
     * DTO转换成POJO
     * @param ${className}DTO ${className}DTO
     * @return ${ClassName}
     */
    ${ClassName} ${className}DTOTo${ClassName}(${ClassName}DTO ${className}DTO);
}
