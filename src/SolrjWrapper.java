package com.georgesdoe;


import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class SolrjWrapper {
	
	private HttpSolrServer solrServer;
	private static final String urlString = "http://localhost:8983/solr";
	private int messageCount;
	
	public SolrjWrapper(){
		try {
			if (solrServer == null) {
				   solrServer = new HttpSolrServer(urlString);
				   System.out.println("Successfully connected to Server!");
				   messageCount=runQuery("*:*").length;
				   System.out.println(messageCount + " total messages");
			}
		} catch (Exception e) {
			System.err.println("Problem while connecting to server: "+e.getMessage());
		}
	}
	/**
	 * Adds a document to Solr with ID equal to total messages sent
	 * @param message The message to index
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public void indexDocument(String message) throws SolrServerException, IOException{
		SolrInputDocument doc=new SolrInputDocument();
		messageCount+=1;
		doc.addField("id", messageCount);
		doc.addField("msgcontent", message);
		solrServer.add(doc);
		solrServer.commit();
	}
	
	/**
	 * Runs a Solr query and returns the content of all messages
	 * @param query A query in Solr syntax
	 * @return An array of all message contents as Strings
	 * @throws SolrServerException
	 */
	public String[] runQuery(String query) throws Exception{
		SolrQuery q=new SolrQuery(query);

		QueryResponse resp = solrServer.query(q);
		SolrDocumentList results = resp.getResults();

		int count = results.size();
		String[] messages=new String[count];
		
		for(int i=0;i<count;i++){
			messages[i]=(String) results.get(i).getFieldValue("msgcontent");
		}

		return messages;
	}
}
