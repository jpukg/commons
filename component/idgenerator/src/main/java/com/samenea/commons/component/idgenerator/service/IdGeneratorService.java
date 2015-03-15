package com.samenea.commons.component.idgenerator.service;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 10/22/12
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IdGeneratorService {
    String getNextId();
    String getCurrentId();
    String getNextId(String token);
    String getCurrentId(String token);
}
