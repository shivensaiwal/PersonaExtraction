package edu.usc.cs.ir;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;



public class SolrUserData {

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, SolrServerException {
		// TODO Auto-generated method stub
		UserExtractor user = new UserExtractor();
		HashMap<String, ArrayList<String>> Map= user.persons();

		
		String urlString = "http://localhost:8983/solr/WeaponsData";
		
		HttpSolrServer server = new HttpSolrServer(urlString);
		
		for (Entry<String, ArrayList<String>> entry : Map.entrySet()) {
			
	        System.out.print("HTML DOC ID - "+entry.getKey()+"\n");
	        System.out.println("--USERNAME--");
	        for(String id : entry.getValue()){
	            System.out.print(id+"\n");
	        }
	        System.out.println();
	    }
		
		

		for (Entry<String, ArrayList<String>> entry : Map.entrySet()) {
			if(entry.getValue().isEmpty())
			{
				SolrInputDocument doc = new SolrInputDocument();
			      doc.addField("id", entry.getKey());
			      doc.addField("persons", "");
			      server.add(doc);
			}
			else
			{
			SolrInputDocument doc = new SolrInputDocument();
		      doc.addField("id", entry.getKey());
		      doc.addField("persons", entry.getValue());
		      
		      server.add(doc);
			}
		}
	    
	      
	      
	       
	      
	      
	      //document is indexed  and boosted by value 2
	        // periodically flush
	    
	    server.commit();
	}

}
