package ${groupId}.${domain}.mapper;

import ma.glasnost.orika.MapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ${groupId}.${domain}.entity.${domainCamelCase};

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Entity/DTO conversion methods
 */
@Component
public class BeanMapper {

    private final MapperFactory mapperFactory;

    private final Lock mapperLock;

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanMapper.class);

    private static final String MESSAGE_CANNOT_MAP_BEAN = "Error mapping bean of type: [%s] to type: [%s].";

    private static final String MESSAGE_CANNOT_MAP_LIST = "Error mapping list to type: [%s].";


    public BeanMapper(final MapperFactory mapperFactory) {

        this.mapperFactory = mapperFactory;
        this.mapperLock    = new ReentrantLock();
    }

    public <S, D> D map(S sourceObject, Class<D> destinationClass) {
        mapperLock.lock();

        try {
            D convertedObject = mapperFactory.getMapperFacade().map(sourceObject, destinationClass);

            return convertedObject;
        } catch (final Throwable cause) {
            LOGGER.error(String.format(MESSAGE_CANNOT_MAP_BEAN, sourceObject.getClass().getSimpleName(), destinationClass.getSimpleName()), cause);
            return null;
        } finally {
            mapperLock.unlock();
        }
    }

    public <S, D> List<D> mapAsList(Iterable<S> source, Class<D> destinationClass) {
        mapperLock.lock();

        try {
            return mapperFactory.getMapperFacade().mapAsList(source, destinationClass);
        } catch (final Throwable cause) {
            LOGGER.error(String.format(MESSAGE_CANNOT_MAP_LIST, destinationClass.getSimpleName()), cause);

            return null;
        } finally {
            mapperLock.unlock();
        }
    }
}
