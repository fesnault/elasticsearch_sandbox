package com.renault.eplatofrm.core.xls2json.json;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: phoenix
 * Date: 31/05/12
 * Time: 18:19
 * To change this template use File | Settings | File Templates.
 */
public interface JsonObject {
    String toJson();
    String getTypeName();
    int getId();
    XContentBuilder buildJson(XContentBuilder builder) throws IOException;
}
