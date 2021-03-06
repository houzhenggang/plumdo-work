package com.plumdo.flow.rest.model.resource;

import com.plumdo.common.resource.BaseResource;
import com.plumdo.flow.constant.ErrorConstant;
import com.plumdo.flow.rest.RestResponseFactory;
import org.flowable.engine.IdentityService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 模型资源基类
 *
 * @author wengwenhui
 * @date 2018年4月11日
 */
public class BaseModelResource extends BaseResource {
    @Autowired
    protected RestResponseFactory restResponseFactory;
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    protected ManagementService managementService;
    @Autowired
    protected IdentityService identityService;

    Model getModelFromRequest(String modelId) {
        Model model = repositoryService.getModel(modelId);
        if (model == null) {
            exceptionFactory.throwObjectNotFound(ErrorConstant.MODEL_NOT_FOUND, modelId);
        }
        return model;
    }

    void checkModelKeyExists(String modelKey) {
        long countNum = repositoryService.createModelQuery().modelKey(modelKey).count();
        if (countNum > 0) {
            exceptionFactory.throwForbidden(ErrorConstant.MODEL_KEY_ALREADY_EXISTS);
        }
    }
}
