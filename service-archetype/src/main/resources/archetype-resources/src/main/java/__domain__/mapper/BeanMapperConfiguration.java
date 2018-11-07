package ${groupId}.${domain}.mapper;

import ${groupId}.${domain}.dto.${domainCamelCase}InputDto;
import ${groupId}.${domain}.dto.${domainCamelCase}OutputDto;
import ${groupId}.${domain}.entity.${domainCamelCase};
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Configuration;

/**
 * Orika configuration for mapping the fields of entities and DTOs
 */
@Configuration
public class BeanMapperConfiguration implements FactoryBean<MapperFactory> {

    @Override
    public MapperFactory getObject() {
        return getDefaultMapperFactory();
    }

    public static MapperFactory getDefaultMapperFactory() {

        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        register${domainCamelCase}InputDtoTransformation(mapperFactory);
        register${domainCamelCase}OutputDtoTransformation(mapperFactory);

        return mapperFactory;
    }

    private static void register${domainCamelCase}InputDtoTransformation(MapperFactory mapperFactory) {

        final ClassMapBuilder<${domainCamelCase}, ${domainCamelCase}InputDto> classMapBuilder =
                mapperFactory.classMap(${domainCamelCase}.class, ${domainCamelCase}InputDto.class);

        classMapBuilder
                .byDefault()
                .register();
    }

    private static void register${domainCamelCase}OutputDtoTransformation(MapperFactory mapperFactory) {

        final ClassMapBuilder<${domainCamelCase}, ${domainCamelCase}OutputDto> classMapBuilder =
                mapperFactory.classMap(${domainCamelCase}.class, ${domainCamelCase}OutputDto.class);

        classMapBuilder
                .byDefault()
                .register();
    }

    @Override
    public Class<?> getObjectType() {
        return MapperFactory.class;
    }
}
