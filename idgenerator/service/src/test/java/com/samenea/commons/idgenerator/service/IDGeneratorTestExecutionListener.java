package com.samenea.commons.idgenerator.service;

import com.samenea.commons.component.utils.test.AbstractAcceptanceTestExecutionListener;

public class IDGeneratorTestExecutionListener extends AbstractAcceptanceTestExecutionListener {

    @Override
    protected String getDatasourceName() {
        return "dataSource";
    }

    @Override
    protected String getXmlDataFileLoaderName() {
        return "fullXmlDataFileLoader";
    }

}
