package com.andreipetrushin.service;

import com.andreipetrushin.dao.Document;
import com.andreipetrushin.service.exception.ServiceLayerException;

public interface DocumentBuilder {

    Document parse(String path) throws ServiceLayerException;

}
