package com.samenea.commons.component.idgenerator.repository.hibernate;

import com.samenea.commons.component.idgenerator.model.IdGeneratorModel;
import com.samenea.commons.component.idgenerator.repository.IdGeneratorRepository;
import com.samenea.commons.model.repository.BasicRepositoryHibernate;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 10/30/12
 * Time: 10:43 AM
 * To change this template use File | Settings | File Templates.
 */

public class IdGeneratorRepositoryHibernate extends BasicRepositoryHibernate<IdGeneratorModel, Long> implements IdGeneratorRepository {
    public IdGeneratorRepositoryHibernate() {
        super(IdGeneratorModel.class);
    }

    @Override
    public IdGeneratorModel findByToken(String token) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("token",token);
        List<IdGeneratorModel> findByToken = findByNamedQuery("findByToken", hashMap);
        return findByToken.isEmpty()?null:findByToken.get(0);
    }
}
