/*
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
*/


package edu.usc.cs.ir;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class UserExtractor {

	private static HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
	//public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException  {
		
	@SuppressWarnings("deprecation")
	public HashMap<String, ArrayList<String>> persons() throws FailingHttpStatusCodeException, MalformedURLException, IOException, SolrServerException {
		
	
		WebClient webclient = new WebClient();
		DefaultCredentialsProvider provider = new DefaultCredentialsProvider();
		provider.addCredentials("darpamemex", "darpamemex");
		webclient.setCredentialsProvider(provider);
		
		webclient.getOptions().setJavaScriptEnabled(false);
		
		webclient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webclient.getOptions().setThrowExceptionOnScriptError(false);
		
		
		
		String urlString = "http://imagecat.dyndns.org/solr/imagecatdev";
		HttpSolrServer server = new HttpSolrServer(urlString);
		
		HttpClientUtil.setBasicAuth((DefaultHttpClient) server.getHttpClient(), "darpamemex", "darpamemex");
		
		SolrQuery query = new SolrQuery();
		query.setQuery("host:www.hipointfirearmsforums.com");
		query.set("fl", "id,persons");
		query.setStart(0);
		query.setRows(36000);
		QueryResponse response = server.query(query);
		//System.out.println(response);
		SolrDocumentList docResults = response.getResults();
		//System.out.println(docResults);
		List<String> htmldocs = new ArrayList<String>();
		for(int i=0;i<docResults.size();i++)
		{
		String id = docResults.get(i).getFieldValue("id").toString();
		//System.out.println(id);
		
		int index=id.lastIndexOf('/');
  	   String lastString=(id.substring(index +1));
		
		htmldocs.add(lastString);
		}		
		
		for(int i=0;i<htmldocs.size();i++)
		{
			System.out.println(htmldocs.get(i));
		}
		
		
		String []data = {"BAAEF51BAD5A08AD569898904D1EB08BFC6D40CD62C3B89C87D084C196A60765","62411F292054C945F435ED5C9428007F527CCE10448BD13DCB692F75027C43CF","50054DB31005E33D4DA4DF57F22253A855210B53CC18D77718EF640E39172620","65012DD7B919E2EA79BD135E70708FC108D35C5E275E6A538B46DDE23588A772","1AD65CF085E1C115EE7CA146E3ED51E099F354DB106C99542C57F20EDF92F056"};
		
		
		
		
		for(int j=0;j<htmldocs.size();j++)
		{
			try
			{
				
			
			ArrayList<String> username = new ArrayList<String>();
		
		//htmldocs.remove(0);
		String url = "http://imagecat.dyndns.org/weapons/alldata/com/hipointfirearmsforums/www/"+htmldocs.get(j);
		System.out.println(j+"\t"+url);
		HtmlPage htmlPage = webclient.getPage(url);
		
		
		@SuppressWarnings("unchecked")
		
		List<HtmlAnchor> anchor = (List<HtmlAnchor>) htmlPage.getByXPath("//*[contains(@href,'members')]");
		//List<HtmlAnchor> anchor = (List<HtmlAnchor>) htmlPage.getByXPath("//')]");
		for(int i=0;i<anchor.size();i++)
		{
			
		//	System.out.println(anchor.get(i).getHrefAttribute());
			String link = anchor.get(i).getHrefAttribute();
			if(link.toLowerCase().contains("php"))
			{
				continue;
			}
			else
			{
			int index=link.lastIndexOf('/');
     	   String lastString=(link.substring(index +1));
     	//   System.out.println(lastString);
     	   username.add(lastString);
			}
		}
		//System.out.println(((HtmlAnchor) anchor).getHrefAttribute());
		//System.out.println(href);
		//System.out.println(imgElement.getAttribute("src").contains("branding1"));
		
		
		LinkedHashSet<String> users = new LinkedHashSet<String>();
	       users.addAll(username);
	       username.clear();
	       username.addAll(users);
	     /*  System.out.println();
	       System.out.println("--Username--");
	       for (int i = 0; i < username.size(); i++) {
	    	     System.out.println((username.get(i)).toString());
	    	}*/
	       
	       
	       map.put(htmldocs.get(j), (ArrayList<String>) username);
			}
			catch(ClassCastException c)
			{
				continue;
			}
			catch (SocketTimeoutException e) {
				// TODO: handle exception
				continue;
			}
			catch (HttpHostConnectException e) {
				// TODO: handle exception
				continue;
			}
			catch (Exception e) {
				// TODO: handle exception
				continue;
			}
		}
		
		webclient.closeAllWindows();
		
		//System.out.println("\n\n");
	       
	/*	for (Entry<String, ArrayList<String>> entry : map.entrySet()) {
	        System.out.print("HTML DOC ID - "+entry.getKey()+"\n");
	        System.out.println("--USERNAME--");
	        for(String id : entry.getValue()){
	            System.out.print(id+"\n");
	        }
	        System.out.println();
	    }*/
		return map;	
		
	//}
	}
}
