package transformers;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/*
 Holds property -> property mappings From one object To another.
 A mapping represents each property on the From object with a value, T,
 being used for the setter/builder call on the To object, R.
 If R and T are not the same type, a transformer function is required that converts from T to R.
 */
public interface MappingsContainer<From, To> {

    List<PropertyMapping> getPropertyMappings();

    void addPropertyMapping(PropertyMapping propertyMapping);

    default <T,R> void withMapping(Function<From, T> recordPropertyGetter,
                           BiConsumer<To, R> builderPropertySetter,
                           Function<T, R> transformation) {
        PropertyWithTransformationMapping<R, T, From, To> propertyMapping = new PropertyWithTransformationMapping(recordPropertyGetter, builderPropertySetter, transformation);
        addPropertyMapping(propertyMapping);
    }

    default <T> void withMapping(Function<From, T> recordPropertyGetter,
                         BiConsumer<To, T> builderPropertySetter) {
        OneToOnePropertyMapping<T, From, To> oneToOneMapping = new OneToOnePropertyMapping<>(recordPropertyGetter, builderPropertySetter);
        addPropertyMapping(oneToOneMapping);
    }

}