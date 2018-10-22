package com.rafaborrego.message.mapper;

import com.rafaborrego.message.dto.MessageOutputDto;
import com.rafaborrego.message.dto.MessageInputDto;
import com.rafaborrego.message.model.Message;
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

        registerMessageInputDtoTransformation(mapperFactory);
        registerMessageOutputDtoTransformation(mapperFactory);

        return mapperFactory;
    }

    private static void registerMessageInputDtoTransformation(MapperFactory mapperFactory) {

        final ClassMapBuilder<Message, MessageInputDto> classMapBuilder =
                mapperFactory.classMap(Message.class, MessageInputDto.class);

        classMapBuilder
                .byDefault()
                .register();
    }

    private static void registerMessageOutputDtoTransformation(MapperFactory mapperFactory) {

        final ClassMapBuilder<Message, MessageOutputDto> classMapBuilder =
                mapperFactory.classMap(Message.class, MessageOutputDto.class);

        classMapBuilder
                .byDefault()
                .register();
    }

    @Override
    public Class<?> getObjectType() {
        return MapperFactory.class;
    }
}
