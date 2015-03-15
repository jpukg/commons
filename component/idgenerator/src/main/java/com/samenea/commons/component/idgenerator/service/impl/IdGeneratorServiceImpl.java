package com.samenea.commons.component.idgenerator.service.impl;

import com.samenea.commons.component.idgenerator.model.IdGeneratorModel;
import com.samenea.commons.component.idgenerator.repository.IdGeneratorRepository;
import com.samenea.commons.component.idgenerator.service.IdGeneratorService;
import com.samenea.commons.component.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 10/22/12
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */

public class IdGeneratorServiceImpl implements IdGeneratorService {
    protected static final Integer DEFAULT_INCREMENT_UNIT = 1;
    protected static final Integer DEFAULT_CACHE_SIZE = 50;
    protected static final String DEFAULT_GLOBAL_TOKEN_NAME = " ";

    private Integer incrementUnit = DEFAULT_INCREMENT_UNIT;
    private Integer catchSize = DEFAULT_CACHE_SIZE;
    private String globalTokenName = DEFAULT_GLOBAL_TOKEN_NAME;


    @Autowired
    private IdGeneratorRepository idGeneratorRepository;
    private Map<String, Long> catchIds = new HashMap<String, Long>();
    private Map<String, Integer> incrementTokenCount = new HashMap<String, Integer>();

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public synchronized String getNextId() {
        return getNextId(globalTokenName);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public synchronized String getNextId(String token) {
        processCache(token);
        incrementToken(token);
        return catchIds.get(token).toString();
    }

    @Override
    public synchronized String getCurrentId() {
        return getCurrentId(globalTokenName);
    }

    @Override
    public synchronized String getCurrentId(String token) {
        processCache(token);
        return catchIds.get(token).toString();
    }

    private void processCache(String token) {
        if (!catchIds.containsKey(token)) {
            IdGeneratorModel byToken = idGeneratorRepository.findByToken(token);
            if (byToken == null) {
                createNewToken(token);
            } else {
                updateAndPutTokenIntoCache(byToken);
            }
        }
    }

    private void updateAndPutTokenIntoCache(IdGeneratorModel byToken) {
        AssertUtil.notNull(byToken, IllegalArgumentException.class);
        catchIds.remove(byToken);
        catchIds.put(byToken.getToken(), byToken.getSequenceID());
        byToken.incrementId(catchSize*incrementUnit);
        idGeneratorRepository.store(byToken);
    }

    private void createNewToken(String token) {
        IdGeneratorModel idGeneratorModel = new IdGeneratorModel(token);
        catchIds.put(token, idGeneratorModel.getSequenceID());
        idGeneratorModel.incrementId(catchSize*incrementUnit);
        idGeneratorRepository.store(idGeneratorModel);
    }

    private void incrementToken(String token) {
        Long id = catchIds.remove(token) + incrementUnit;
        catchIds.put(token, id);
        Integer remove = incrementTokenCount.remove(token);
        int count = remove == null ? 1 : remove + 1;
        if (count >= (catchSize*incrementUnit)) {
            updateAndPutTokenIntoCache(idGeneratorRepository.findByToken(token));
            count = 1;
        }
        incrementTokenCount.put(token, count);

    }

    public void setIdGeneratorRepository(IdGeneratorRepository idGeneratorRepository) {
        this.idGeneratorRepository = idGeneratorRepository;
    }

    public void setGlobalTokenName(String globalTokenName) {
        this.globalTokenName = globalTokenName;
    }

    public void setIncrementUnit(Integer incrementUnit) {
        this.incrementUnit = incrementUnit;
    }

    public void setCatchSize(Integer catchSize) {
        this.catchSize = catchSize;
    }
}
