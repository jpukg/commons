package com.samenea.commons.idgenerator.repository;

import com.samenea.commons.idgenerator.model.HiLoGeneratorModel;
import com.samenea.commons.model.repository.BasicRepositoryHibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository()
public class HiLoGeneratorRepositoryHibernate extends BasicRepositoryHibernate<HiLoGeneratorModel, Long> implements HiLoGeneratorRepository {
    public HiLoGeneratorRepositoryHibernate() {
        super(HiLoGeneratorModel.class);
    }

    @Override
    @Transactional(readOnly = true)
    public HiLoGeneratorModel findByName(String name) {
        Session session = getSession();

        Query query = session.createQuery("from HiLoGeneratorModel where name=:name").setString("name", name);
        HiLoGeneratorModel model = (HiLoGeneratorModel) query.uniqueResult();

        return model;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public HiLoGeneratorModel getNextHi(String name) {
        HiLoGeneratorModel model = findByName(name);

        if (model == null) {
            model = new HiLoGeneratorModel(name);
        }

        model.setHi(model.getHi() + 1);
        return store(model);
    }
}
