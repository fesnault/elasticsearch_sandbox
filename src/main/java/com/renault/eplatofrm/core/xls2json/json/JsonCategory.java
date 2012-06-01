package com.renault.eplatofrm.core.xls2json.json;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: phoenix
 * Date: 31/05/12
 * Time: 18:17
 * To change this template use File | Settings | File Templates.
 */
public class JsonCategory implements JsonObject {
    private int id;
    private String name;

    public XContentBuilder buildJson(XContentBuilder builder) throws IOException {
        return builder.field("id", id)
               .field("name", name);
    }

    public String getTypeName() {
        return "category";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;

    }

    public String toJson() {
        return name;
    }
}
