package com.renault.eplatofrm.core.xls2json;

import com.renault.eplatofrm.core.xls2json.converter.XlsConverter;
import com.renault.eplatofrm.core.xls2json.elasticsearch.Indexer;
import com.renault.eplatofrm.core.xls2json.json.JsonObject;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.get.GetField;
import org.elasticsearch.indices.IndexMissingException;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.facet.termsstats.TermsStatsFacetBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * Created by IntelliJ IDEA.
 * User: phoenix
 * Date: 28/05/12
 * Time: 12:18
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String...args) {
        if (args == null || args.length == 0) {
            printUsage();
            System.exit(-1);
        }
        if (args[0].equalsIgnoreCase("--convert")) {
            if (args.length != 2) {
                printUsage();
                System.exit(-1);
            }
            XlsConverter converter = new XlsConverter();
            Client client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress("192.168.6.159", 9302));

            Indexer indexer = new Indexer(client);

            try {
                DeleteIndexResponse response = client.admin().indices().prepareDelete("faq-ze").execute().actionGet();
            } catch (IndexMissingException ime) {
                logger.info("Index faq-ze not existing. No delete occured.");
            }
            try {
                indexer.createIndex();
            } catch (IOException ioe) {
                logger.error("Could not create index.", ioe);
                client.close();
                System.exit(1);
            }

            Map<String,List<JsonObject>> objects = converter.convert(args[1]);
            try {
                for (Map.Entry<String, List<JsonObject>> entry : objects.entrySet()) {
                    String indexName = entry.getKey();
                    List<JsonObject> indexObjects = entry.getValue();
                    logger.info("Index "+indexName+" has "+indexObjects.size()+" documents to index");
                    for (JsonObject objectToIndex : indexObjects) {
                        indexer.index(indexName, objectToIndex);
                    }
                }
                logger.info("Done");
            } catch (IOException ioe) {
                logger.error("Could not index document.", ioe);
            } finally {
                client.close();
            }
        } else if (args[0].equalsIgnoreCase("--search")) {
            Client client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress("192.168.6.159", 9302));
            GetResponse response = client.prepareGet("faq-ze", "category", "14")
                    .execute()
                    .actionGet();
            response = client.prepareGet("faq-ze", "question", "3")
                .execute()
                .actionGet();

            //SearchResponse searchResponse = client.prepareSearch("faq-ze").

            client.close();
        } else if (args[0].equalsIgnoreCase("--facets")) {
            Client client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress("192.168.6.159", 9302));
            SearchResponse response = client.prepareSearch("faq-ze").setTypes("question").addFacet(new TermsStatsFacetBuilder("category-facet").keyField("categoryTitle").valueField("categoryTitle")).execute().actionGet();

            //SearchResponse searchResponse = client.prepareSearch("faq-ze").

            client.close();
        }
    }

    private static void printUsage() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Usage :\n");
        buffer.append(" java com.renault.eplatform.core.xls2json.Main --convert source.xls\n");
        buffer.append("  > Converts an xls source file into json files.\n");
        buffer.append(" java com.renault.eplatform.core.xls2json.Main --import json_file_template\n");
        buffer.append("  > imports all file which name contains the json_file_template into the elasticsearch index.\n");
        System.out.println(buffer.toString());
    }
}
