package com.goeuro.dot.base.dao.BaseRepository;


import com.goeuro.dot.base.exception.DotException;
import com.goeuro.dot.base.model.BaseModel;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.util.Arrays;

@Component
public class ModelReflectionMerger<M extends BaseModel> {

    public void merge(final M newModel, M existingModel) {

        PropertyDescriptor[] newModelPDs = PropertyUtils.getPropertyDescriptors(newModel);

        Arrays.stream(newModelPDs).filter(pd -> isNotTheIDPd(pd, newModel.getClass()))
                .forEach(propertyDescriptor -> {
                    try {
                        propertyDescriptor.getWriteMethod().invoke(existingModel, propertyDescriptor.getReadMethod().invoke(newModel));
                    } catch (Exception ex) {
                        throw new DotException("Couldn't merge new model", ex);
                    }
                });

    }

    private boolean isNotTheIDPd(PropertyDescriptor pd, Class aClass) {
        return false;
    }
}
