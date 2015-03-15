package com.samenea.commons.component.idgenerator.repository;

import com.samenea.commons.component.idgenerator.model.IdGeneratorModel;
import com.samenea.commons.component.model.BasicRepository;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 10/30/12
 * Time: 10:40 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IdGeneratorRepository extends BasicRepository<IdGeneratorModel, Long> {
    IdGeneratorModel findByToken(String token);
}
