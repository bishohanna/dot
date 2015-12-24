package com.goeuro.dot.base.dao.BaseRepository;

import com.goeuro.dot.base.model.BaseModel;

/**
 * Merges model with updates to already existing model
 *
 * @param <M> base model
 */
@FunctionalInterface
public interface ModelMerger<M extends BaseModel> {

    /**
     * Sets updated or changed properties in the existing model to take effect in persistence
     *
     * @param newModel      new model containing changes
     * @param existingModel already persisted model
     */
    void merge(M newModel, M existingModel);
}
