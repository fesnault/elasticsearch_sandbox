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
public class JsonQuestion implements JsonObject {
    private int id;
    private String categoryTitle;
    private String questionTitle;
    private int questionPopularity;
    private String questionDisplay;
    private String responseTitle;
    private String responseDescription;
    private String responsePdf;
    private String responsePlusUrl;
    private String responsePlusLabel;
    private String responseMedia;
    private String responseMediaGlimpse;


    public XContentBuilder buildJson(XContentBuilder builder) throws IOException {
        return builder.field("id", id)
                .field("categoryTitle", categoryTitle)
                .field("questionTitle", questionTitle)
                .field("questionPopularity", questionPopularity)
                .field("questionDisplay", questionDisplay)
                .field("responseTitle", responseTitle)
                .field("responseDescription", responseDescription)
                .field("responsePdf", responsePdf)
                .field("responsePlusUrl", responsePlusUrl)
                .field("responsePlusLabel", responsePlusLabel)
                .field("responseMedia", responseMedia)
                .field("responseMediaGlimpse", responseMediaGlimpse);
    }

    public String getTypeName() {
        return "question";
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public int getQuestionPopularity() {
        return questionPopularity;
    }

    public void setQuestionPopularity(int questionPopularity) {
        this.questionPopularity = questionPopularity;
    }

    public String getQuestionDisplay() {
        return questionDisplay;
    }

    public void setQuestionDisplay(String questionDisplay) {
        this.questionDisplay = questionDisplay;
    }

    public String getResponseTitle() {
        return responseTitle;
    }

    public void setResponseTitle(String responseTitle) {
        this.responseTitle = responseTitle;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    public String getResponsePdf() {
        return responsePdf;
    }

    public void setResponsePdf(String responsePdf) {
        this.responsePdf = responsePdf;
    }

    public String getResponsePlusUrl() {
        return responsePlusUrl;
    }

    public void setResponsePlusUrl(String responsePlusUrl) {
        this.responsePlusUrl = responsePlusUrl;
    }

    public String getResponsePlusLabel() {
        return responsePlusLabel;
    }

    public void setResponsePlusLabel(String responsePlusLabel) {
        this.responsePlusLabel = responsePlusLabel;
    }

    public String getResponseMedia() {
        return responseMedia;
    }

    public void setResponseMedia(String responseMedia) {
        this.responseMedia = responseMedia;
    }

    public String getResponseMediaGlimpse() {
        return responseMediaGlimpse;
    }

    public void setResponseMediaGlimpse(String responseMediaGlimpse) {
        this.responseMediaGlimpse = responseMediaGlimpse;
    }

    public String toJson() {
       return questionTitle;
    }
}
