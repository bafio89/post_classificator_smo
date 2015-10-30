package post_classification.wordFeatures;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

//import javax.script.ScriptEngine;
//import javax.script.ScriptEngineManager;
//import javax.script.ScriptException;

import post_classification.stemming.Version;

public class wordFeatures {

	public static void main(String[] args) throws SQLException, IOException {
		// TODO Auto-generated method stub

		wordFeaturesHash("_l3");
//		String message = "*ciao r+s quanto fa asd";
//				
//		message = message.replaceAll("[*](?!\\s)", " ");
//		
//
//		System.out.println(message);
		
	
}
	
//	public static void wordFeaturesWithStopwords() throws SQLException{
//			Statement st = null;
//	        ResultSet rs = null;
//	        int rs2 = 0;
//	        
//			Connection con = null;
//			
//			String message = "";
//			
//			String fbpid = "";
//			
//			String query = "";
//			
//			String[]words = null;
//			
//			con = Version.connection();
//			
//			try {
//				st = con.createStatement();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			try {
//				rs = st.executeQuery("SELECT * from fb_post where post_type is not");
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			
//			while (rs.next()) {          
//			     
//				 fbpid = rs.getString("fbpid");
//				 
//				 query = "INSERT INTO word_features2 (fbpid) values (" + fbpid + ")";  
//				 st = con.createStatement();
//				 rs2 = st.executeUpdate(query);
//				 
//			     message  = rs.getString("message");
//			
//			     message = message.toLowerCase();
//			     
//			     message = message.trim();
//			     message = message.replace(".", " ");
//			     message = message.replace(";", " ");
//			     message = message.replace(",", " ");
//			     message = message.replace(")", " ");
//			     message = message.replace("?", " ");
//			     message = message.replace("!", " ");
//			     message = message.replace("(", " ");
//			     message = message.replace("[", " ");
//			     message = message.replace("]", " ");
//			     message = message.replace("dell'", "dell ");
//			     message = message.replace("dell", "dell ");
//			     message = message.replace("del", "del ");
//			     message = message.replace("l'", "l ");
//			     message = message.replace("dall'", "dall ");
//			     message = message.replace("all'", "all ");
//			     message = message.replace("un'", "un ");
//			     message = message.replace("\n", " ");
//			     message = message.replace("quest'", "quest ");
//			     message = message.replace("\"", " ");
//			     message = message.replace("'", " ");
//				
//			     message = message.replace("'", "\\'"); 
//			     message = message.trim();
//			     
//			     words = message.split(" ");
//			     
//			    
//			     
//			     for (int i = 0 ; i< words.length; i++ )  
//			     {
//			    	 if(words[i] != null || words[i] != " " ){
//				    		 query = " ALTER TABLE qeanalysis.word_features2 ADD COLUMN " + words[i] + " INT(10) unsigned DEFAULT 0";
//				    	 
//				    	 System.out.println(query);
//				    	 st = con.createStatement();
//				    	 try{
//				    		 rs2 = st.executeUpdate(query);
//				    	 }catch(SQLException e){
//				    		 
//				    	 }
//					     query = "UPDATE word_features2 SET " + words[i] + " = 1 where fbpid  =" + fbpid; 
//					     System.out.println(query);
//					     try{
//					    	 rs2 = st.executeUpdate(query);
//					     	}catch(SQLException e){
//				    		 
//					     	}
//			    	 }
//			     }
//			
//		}
		
		
//	}
	
	
	
	public static void bagOfWords() throws SQLException{
		
		Statement st = null;
		
        ResultSet rs = null;
        int rs2 = 0;
        
		Connection con = null;
		
		String message = "";
		
		String fbpid = "";
		
		String query = "";
		
		String[]words = null;
		
		con = Version.connection();
		
		try {
			st = con.createStatement();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs = st.executeQuery("SELECT * from pre_proc_post where post_type != 'null'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		while (rs.next()) {          
		     
			 fbpid = rs.getString("fbpid");
			 
			 query = "INSERT INTO word_features2 (fbpid) values (" + fbpid + ")";  
			 st = con.createStatement();
			 rs2 = st.executeUpdate(query);
			 
		     message  = rs.getString("message");
		
		     message = message.toLowerCase();
		     
		     message = message.trim();
		     message = message.replace(".", " ");
		     message = message.replace(";", " ");
		     message = message.replace(",", " ");
		     message = message.replace(")", " ");
		     message = message.replace("?", " ");
		     message = message.replace("!", " ");
		     message = message.replace("(", " ");
		     message = message.replace("[", " ");
		     message = message.replace("]", " ");
		     message = message.replace("dell'", " ");
		     message = message.replace("dell", " ");
		     message = message.replace("del", " ");
		     message = message.replace("l'", " ");
		     message = message.replace("dall'", " ");
		     message = message.replace("all'", " ");
		     message = message.replace("un'", " ");
		     message = message.replace("\n", " ");
		     message = message.replace("quest'", " ");
		     message = message.replace("\"", " ");
		     message = message.replace("'", " ");
			
		     message = message.replace("'", "\\'"); 
		     message = message.trim();
		     
		     words = message.split(" ");
		     
		    
		     
		     for (int i = 0 ; i< words.length; i++ )  
		     {
		    	 if(words[i] != null || words[i] != " " ){
			    		 query = " ALTER TABLE qeanalysis.word_features2 ADD COLUMN " + words[i] + " INT(10) unsigned DEFAULT 0";
			    	 
			    	 System.out.println(query);
			    	 st = con.createStatement();
			    	 try{
			    		 rs2 = st.executeUpdate(query);
			    	 }catch(SQLException e){
			    		 
			    	 }
				     query = "UPDATE word_features2 SET " + words[i] + " = 1 where fbpid  =" + fbpid; 
				     System.out.println(query);
				     try{
				    	 rs2 = st.executeUpdate(query);
				     	}catch(SQLException e){
			    		 
				     	}
		    	 }
		     }
		
	}
		
	}
	
	
	public static void wordFeatures() throws SQLException{
		Statement st = null;
        ResultSet rs = null;
        int rs2 = 0;
        
		Connection con = null;
		
		String message = "";
		
		String fbpid = "";
		
		String query = "";
		
		String[]words = null;
		
		con = Version.connection();
		
		try {
			st = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs = st.executeQuery("SELECT * from pre_proc_post2 where post_type != 'null'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		while (rs.next()) {          
		     
			 fbpid = rs.getString("fbpid");
			 
			 query = "INSERT INTO word_features3 (fbpid) values (" + fbpid + ")";  
			 st = con.createStatement();
			 rs2 = st.executeUpdate(query);
			 
		     message  = rs.getString("message");
		
		     message = message.toLowerCase();
		     
		     message = message.trim();
		     message = message.replace(".", " ");
		     message = message.replace(";", " ");
		     message = message.replace(",", " ");
		     message = message.replace(")", " ");
		     message = message.replace("?", " ");
		     message = message.replace("!", " ");
		     message = message.replace("(", " ");
		     message = message.replace("[", " ");
		     message = message.replace("]", " ");		    
		     message = message.replace("\n", " ");		     
		     message = message.replace("\"", " ");
		     message = message.replace("'", " ");
			
		     message = message.replace("'", "\\'"); 
		     message = message.trim();
		     
		     words = message.split(" ");
		     
		    
		     
		     for (int i = 0 ; i< words.length; i++ )  
		     {
		    	 if(words[i] != null || words[i] != " " ){
			    		 query = " ALTER TABLE qeanalysis.word_features3 ADD COLUMN " + words[i] + " TINYINT unsigned DEFAULT 0";
			    	 
			    	 System.out.println(query);
			    	 st = con.createStatement();
			    	 try{
			    		 rs2 = st.executeUpdate(query);
			    	 }catch(SQLException e){
			    		 if(e.getErrorCode() == 1117)
			    			 System.out.println(e);
			    		
			    	 }
				     query = "UPDATE word_features3 SET " + words[i] + " =" + words[i] + " + 1 where fbpid  =" + fbpid; 
				     System.out.println(query);
				     try{
				    	 rs2 = st.executeUpdate(query);
				     	}catch(SQLException e){
			    		 
				     	}
		    	 }
		     }
		
	}
	}
	
	
  
   
   public static void wordFeaturesHash(String level) throws SQLException, IOException{
		
	   Statement st = null;
	   Statement st2 = null;
       ResultSet rs = null;
       ResultSet rs2 = null;
       ResultSet rs3 = null;
       
		Connection con = null;
		
		String message = "";
		
		String fbpid = "";
		
		String query = "";
		
		String[]words = null;
		
		Hashtable<String, Hashtable> post = new Hashtable<String, Hashtable>();
		
		Hashtable<String, Integer> post_line = new Hashtable<String, Integer>();
		   
		ArrayList<String> wordList = new ArrayList<String>();
		
		con = Version.connection();
		
		try {
			st = con.createStatement();
			st2 = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs = st.executeQuery("SELECT * from pre_proc_post2 where (post_type"+level+" like 'si' OR post_type"+level+" like 'no') order by fbpid DESC limit 1600");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		while (rs.next()) {          
	
			 		 
		     message  = rs.getString("message");
		
		     message = message.toLowerCase();
		     
//		     message = message.replaceAll( "^\\d+(\\.\\d+)?", " ");
		     message = message.replaceAll("-?\\d+(?:\\.\\d+(?:E\\d+)?)?(\\s*[-+/\\*]\\s+-?\\d+(?:\\.\\d+(?:E\\d+)?)?)+", "");
		     message = message.replaceAll("^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?/", " ");
		     message = message.replaceAll("\\b[\\w][+*/-=\\|<>^]+[\\w]\\b", " ");
		     message = message.replaceAll("\\b[\\w][+*/-=\\|<>^]+"," ");
		     message = message.trim();
		     message = message.replace("`", " ");
		     message = message.replace("~", " ");
		     message = message.replace(".", " ");
		     message = message.replace("-", " ");
		     message = message.replace(";", " ");
		     message = message.replace(":", " ");
		     message = message.replace(",", " ");
		     message = message.replace(")", " ");
		     message = message.replace("?", " ");
		     message = message.replace("*", " ");
		     message = message.replace("!", " ");
		     message = message.replace("(", " ");
		     message = message.replace("[", " ");
		     message = message.replace("]", " ");
		     message = message.replace("%", " ");
		     message = message.replace(">", " ");
		     message = message.replace("<", " ");	
		     message = message.replace("\n", " ");		     
		     message = message.replace("\"", " ");
		     message = message.replace("'", " ");
		     message = message.replace("^", " ");
		     message = message.replace("|", " ");
		     message = message.replace("+", " ");
		     message = message.replace("/", " ");		     
		     message = message.replace("’", " ");
		     message = message.replace("'", "\\'");
		     message = message.replace("ì", "i");
		     message = message.replace("ò", "o");
		     message = message.replace("è", "e");
		     message = message.replace("é", "e");
		     message = message.replace("ù", "u");
		     message = message.replace("à", "a");
		     message = message.replace("#", " ");
		     message = message.replace(System.getProperty("line.separator"), " ");
		     
		     
		     message = message.replaceAll("\\d+"," ");
		     message = message.replaceAll("([_])"," ");
		     
		     message = message.trim();
		     
		     words = message.split(" ");
		     
		    	     
		     for (int i = 0 ; i< words.length; i++ )  
		     {
		    	 if(!wordList.contains(words[i]) && !words[i].equals("") 
		    		&& !words[i].matches("(\\W)") && !words[i].contains("@") && !words[i].contains("{") 
		    		&& !words[i].contains("=") && !words[i].contains("}" )&& !words[i].contains("")
		    		&& !words[i].contains("studenti	es") && !words[i].contains("directoryshell") && !words[i].contains("root")){
		    	 	wordList.add(words[i]);	
		    		post_line.put(words[i], 0);	    	
		    	 }
		     }
		}
		
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("C:/Users/fabio/Google Drive/TESI - Q&A SL/TESI 2.0/Classificazione/Nuovo modello/training test test - with stopwords"+level+".csv", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.out.println(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
		
		String txt = " ";			  
			
	
		int k = 0;
		
		String query2 = "SELECT f.*,pp.message,pp.post_type"+level+" from pre_proc_post2 as pp, features_rappresentation as f where (post_type"+level+" like 'si' OR post_type"+level+" like 'no') and f.pid = pp.fbpid";
		rs2 = st.executeQuery(query2);
		ResultSetMetaData rsmd = rs2.getMetaData();
		int columnCount = rsmd.getColumnCount();
		
		txt = rsmd.getColumnName(1);
		
		for(int j = 2; j<= columnCount-2; j++){
			
			txt += "," + rsmd.getColumnName(j);
		}
		
		Iterator<Map.Entry<String, Integer>> it = post_line.entrySet().iterator();
		
			
				while(it.hasNext()){
					Map.Entry< String, Integer> entry2 = it.next(); 
					if(entry2.getKey() != "")				
						txt += "," + entry2.getKey();
				}
				txt += ",post_type";
				System.out.println(txt);
				
				writer.println(txt);
				
				String line = "";
				
				String columnName = "";
				
				Hashtable<String, Integer> post_line_hold = new Hashtable<String, Integer>();
				
				Iterator<Map.Entry<String, Integer>> it3 = null;
				
				Map.Entry< String, Integer> entry3 = null;
		
		while(rs2.next()){
			
			line = "";
			post_line_hold = new Hashtable<String, Integer>();
			
			 fbpid = rs2.getString("pid");
			 
			
			 columnName = rsmd.getColumnName(1);
			

					line = rs2.getString(columnName);
					
					for(int j = 2; j< columnCount-1; j++){
						
						line += "," +  rs2.getString(rsmd.getColumnName(j));
					}	
			
			 
		     message = rs2.getString("message");

		     message = message.toLowerCase();
		      
		     message = message.trim();
		     message = message.replaceAll("-?\\d+(?:\\.\\d+(?:E\\d+)?)?(\\s*[-+/\\*]\\s+-?\\d+(?:\\.\\d+(?:E\\d+)?)?)+", "");
		     message = message.replaceAll("^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?/", " ");
		     message = message.replaceAll("\\b[\\w][+*/-=\\|<>^]+[\\w]\\b", " ");		
		     message = message.replaceAll("\\b[\\w][+*/-=\\|<>^]+"," ");
		     message = message.trim();
		     message = message.replace("`", " ");
		     message = message.replace("~", " ");
		     message = message.replace(".", " ");		     
		     message = message.replace("-", " ");
		     message = message.replace(";", " ");
		     message = message.replace(":", " ");
		     message = message.replace(",", " ");
		     message = message.replace(")", " ");
		     message = message.replace("?", " ");
		     message = message.replace("*", " ");
		     message = message.replace("!", " ");
		     message = message.replace("(", " ");
		     message = message.replace("[", " ");
		     message = message.replace("]", " ");
		     message = message.replace("%", " ");
		     message = message.replace(">", " ");
		     message = message.replace("<", " ");	
		     message = message.replace("\n", " ");		     
		     message = message.replace("\"", " ");
		     message = message.replace("'", " ");
		     message = message.replace("^", " ");
		     message = message.replace("|", " ");
		     message = message.replace("+", " ");
		     message = message.replace("/", " ");		     
		     message = message.replace("’", " ");
		     message = message.replace("'", "\\'"); 
		     message = message.replace("ì", "i");
		     message = message.replace("ò", "o");		     
		     message = message.replace("ù", "u");
		     message = message.replace("à", "a");
		     message = message.replace("#", " ");
		     message = message.replace(System.getProperty("line.separator"), " ");
		     
		     message = message.replaceAll("\\d+"," ");
		     message = message.replaceAll("([_])"," ");
		     message = message.trim();
		     
		     words = message.split(" ");
		     
		     post_line_hold = (Hashtable<String, Integer>) post_line.clone();    
		     
		     for (int i = 0 ; i< words.length; i++ )  
		     {
//		    	 System.out.println(words[i]);
		    	 if(post_line_hold.get(words[i]) != null)
		    		 post_line_hold.put(words[i], post_line_hold.get(words[i])+1);
		    	
		     } 
		     post.put(fbpid,(Hashtable<String, Integer>) post_line_hold.clone());
		     
		  	
		     it3 = post_line_hold.entrySet().iterator();
		  	
		     entry3 = null;

			while(it3.hasNext()){
				
				entry3 = it3.next(); 
				
				if(entry3.getKey() != null)			
					line += "," + entry3.getValue();
				k += 1;
			}
			line += ","+rs2.getString("post_type"+level);
			System.out.println(line);
			System.out.println(k);
			
			writer.println(line);
			
		  	
		   
		}
		
		System.out.println(post.size());
		System.out.println(post_line.size());
		
		
		 FileOutputStream fileOut =
		         new FileOutputStream("C:/Users/fabio/Google Drive/TESI - Q&A SL/TESI 2.0/Classificazione/Nuovo modello/wordList"+level+".ser");
		         ObjectOutputStream out = new ObjectOutputStream(fileOut);
		         out.writeObject(wordList);
		         out.close();
		         fileOut.close();
		
         
         FileOutputStream fileOut2 =
		         new FileOutputStream("C:/Users/fabio/Google Drive/TESI - Q&A SL/TESI 2.0/Classificazione/Nuovo modello/postLine"+level+".ser");
		         ObjectOutputStream out2 = new ObjectOutputStream(fileOut2);
		         out2.writeObject(post_line);
		         out2.close();
		         fileOut.close();
		         
		         
		writer.close();

		
		
   	}
		
   
}
