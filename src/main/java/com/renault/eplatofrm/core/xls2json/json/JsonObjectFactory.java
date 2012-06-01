package com.renault.eplatofrm.core.xls2json.json;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: phoenix
 * Date: 31/05/12
 * Time: 18:20
 * To change this template use File | Settings | File Templates.
 */
public class JsonObjectFactory {

    private JsonObjectFactory() {}

    public static JsonObject createJsonObject(Map<String, String> data, String indexName) {
        if (indexName.equalsIgnoreCase("Categories")) {
            return createJsonCategory(data);
        } else if (indexName.equalsIgnoreCase("Questions")) {
            return createJsonQuestion(data);
        }
        return null;
    }

    private static JsonCategory createJsonCategory(Map<String, String> data) {
        String id = data.get("category_id");
        String name = data.get("category_title");
        if (id == null || name == null) {
            return null;
        }
        JsonCategory category = new JsonCategory();
        category.setId(Integer.parseInt(id));
        category.setName(name);
        return category;
    }

    private static JsonQuestion createJsonQuestion(Map<String, String> data) {
        String categoryTitle = data.get("category_title");
        String id = data.get("question_id");
        String questionTitle = data.get("question_title");
        String questionPopularity = data.get("question_popularity");
        String questionDisplay = data.get("question_display");
        String responseTitle = data.get("response_title");
        String responseDescription = data.get("response_description");
        String responsePdf = data.get("response_pdf");
        String responsePlusUrl = data.get("response_plus_url");
        String responsePlusLabel = data.get("response_plus_label");
        String responseMedia = data.get("response_media");
        String responseMediaGlimpse = data.get("response_media_glimpse");

        if (id == null || categoryTitle == null || responseTitle == null) {
            return null;
        }
        JsonQuestion question = new JsonQuestion();
        question.setId(Integer.parseInt(id));
        question.setCategoryTitle(categoryTitle);
        question.setQuestionDisplay(questionDisplay);
        question.setQuestionTitle(questionTitle);
        question.setQuestionPopularity(Integer.parseInt(questionPopularity));
        question.setResponseTitle(responseTitle);
        question.setResponseDescription(responseDescription);
        question.setResponsePdf(responsePdf);
        question.setResponsePlusUrl(responsePlusUrl);
        question.setResponsePlusLabel(responsePlusLabel);
        question.setResponseMedia(responseMedia);
        question.setResponseMediaGlimpse(responseMediaGlimpse);
        return question;
    }
}
