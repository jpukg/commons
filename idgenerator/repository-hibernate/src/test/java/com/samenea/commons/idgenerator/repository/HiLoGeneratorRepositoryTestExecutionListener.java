package com.samenea.commons.idgenerator.repository;

import com.samenea.commons.component.utils.test.AbstractAcceptanceTestExecutionListener;

public class HiLoGeneratorRepositoryTestExecutionListener extends AbstractAcceptanceTestExecutionListener {

    @Override
    protected String getDatasourceName() {
        return "dataSource";
    }

    @Override
    protected String getXmlDataFileLoaderName() {
        return "fullXmlDataFileLoader";
    }

}
