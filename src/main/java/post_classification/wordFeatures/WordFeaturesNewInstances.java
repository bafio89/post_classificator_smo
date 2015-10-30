package post_classification.wordFeatures;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import post_classification.start.Start;
import post_classification.stemming.Version;

public class WordFeaturesNewInstances {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		// TODO Auto-generated method stub
		
		
	
	}
	

	public static void wordFeaturesHash(String level , Connection con) throws SQLException, ClassNotFoundException, IOException{
		
		
		   Statement st = null;
		   Statement st2 = null;
	       ResultSet rs = null;
	       ResultSet rs2 = null;
	       ResultSet rs3 = null;
	       			
			String message = "";
			
			String fbpid = "";
			
			String query = "";
			
			String[]words = null;
			
			Hashtable<String, Hashtable> post = new Hashtable<String, Hashtable>();
			
			Hashtable<String, Integer> post_line = new Hashtable<String, Integer>();
			   
			ArrayList<String> wordList = new ArrayList<String>();
			
					
			try {
//				st = con.createStatement();
				st2 = con.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			try {
//				rs = st.executeQuery("SELECT * from pre_proc_post2 where post_type"+ level +" is null limit 5");
//				
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			
//			while (rs.next()) {          
//			     
//				 		 
//			     message  = rs.getString("message");
//			
//			     message = message.toLowerCase();
//			     
////			     message = message.replaceAll( "^\\d+(\\.\\d+)?", " ");
//			     message = message.replaceAll("-?\\d+(?:\\.\\d+(?:E\\d+)?)?(\\s*[-+/\\*]\\s+-?\\d+(?:\\.\\d+(?:E\\d+)?)?)+", "");
//			     message = message.replaceAll("^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?/", " ");
//			     message = message.replaceAll("\\b[\\w][+*/-=\\|<>^]+[\\w]\\b", " ");
//			     message = message.replaceAll("\\b[\\w][+*/-=\\|<>^]+"," ");
//			     message = message.trim();
//			     message = message.replace("`", " ");
//			     message = message.replace("~", " ");
//			     message = message.replace(".", " ");
//			     message = message.replace("-", " ");
//			     message = message.replace(";", " ");
//			     message = message.replace(":", " ");
//			     message = message.replace(",", " ");
//			     message = message.replace(")", " ");
//			     message = message.replace("?", " ");
//			     message = message.replace("*", " ");
//			     message = message.replace("!", " ");
//			     message = message.replace("(", " ");
//			     message = message.replace("[", " ");
//			     message = message.replace("]", " ");
//			     message = message.replace("%", " ");
//			     message = message.replace(">", " ");
//			     message = message.replace("<", " ");	
//			     message = message.replace("\n", " ");		     
//			     message = message.replace("\"", " ");
//			     message = message.replace("'", " ");
//			     message = message.replace("^", " ");
//			     message = message.replace("|", " ");
//			     message = message.replace("+", " ");
//			     message = message.replace("/", " ");		     
//			     message = message.replace("’", " ");
//			     message = message.replace("'", "\\'");
//			     message = message.replace("ì", "i");
//			     message = message.replace("ò", "o");
//			     message = message.replace("è", "e");
//			     message = message.replace("é", "e");
//			     message = message.replace("ù", "u");
//			     message = message.replace("à", "a");
//			     
//			     
//			     message = message.replaceAll("\\d+"," ");
//			     message = message.replaceAll("([_])"," ");
//			     
//			     message = message.trim();
//			     
//			     words = message.split(" ");
//			     
////			    	     
////			     for (int i = 0 ; i< words.length; i++ )  
////			     {
////			    	 if(!wordList.contains(words[i]) && !words[i].equals("") 
////			    		&& !words[i].matches("(\\W)") && !words[i].contains("@") && !words[i].contains("{") 
////			    		&& !words[i].contains("=") && !words[i].contains("}" )&& !words[i].contains("")
////			    		&& !words[i].contains("studenti	es")){
////			    	 	wordList.add(words[i]);	
////			    		post_line.put(words[i], 0);	    	
////			    	 }
////			     }
//			     
//			   
//			}
				
				InputStream fileIn = WordFeaturesNewInstances.class.getClass().getResourceAsStream("/wordList"+level+".ser");
//			  FileInputStream fileIn = new FileInputStream("C:/Users/fabio/Google Drive/TESI - Q&A SL/TESI 2.0/Classificazione/classification/wordList.ser");
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         wordList = (ArrayList<String>) in.readObject();
		         in.close();
		         fileIn.close();
		         
		         InputStream fileIn2 = WordFeaturesNewInstances.class.getClass().getResourceAsStream("/postLine"+level+".ser");
//		         FileInputStream fileIn2 = new FileInputStream("C:/Users/fabio/Google Drive/TESI - Q&A SL/TESI 2.0/Classificazione/classification/postLine.ser");
		         ObjectInputStream in2 = new ObjectInputStream(fileIn2);
		         post_line = (Hashtable<String, Integer>) in2.readObject();
		         in2.close();
		         fileIn2.close();
			
			PrintWriter writer = null;
			try {
				writer = new PrintWriter("C:/Users/fabio/Google Drive/TESI - Q&A SL/TESI 2.0/Classificazione/features rappresentation/newInstances"+level+".csv", "UTF-8");
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
//			rs2 = st.executeQuery("SELECT f.*,pp.message,pp.post_type"+level+" from pre_proc_post2 as pp, features_rappresentation as f where pp.fbpid = 150548521714529 and f.pid = pp.fbpid");
			rs2 = st2.executeQuery("SELECT f.*,pp.message,pp.post_type"+level+" from pre_proc_post2 as pp, features_rappresentation as f where post_type"+level+" is null and f.pid = pp.fbpid limit 4000");
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
//					txt += ",post_type";
					System.out.println(txt);
					
					writer.println(txt);
					
					String line = "";
					
					String columnName = "";
					
					Hashtable<String, Integer> post_line_hold = new Hashtable<String, Integer>();
					
					Iterator<Map.Entry<String, Integer>> it3 = null;
					
					Map.Entry< String, Integer> entry3 = null;
			
			while(rs2.next()){
								
				post_line_hold = null;

				columnName = null;
				
				it3 = null;
				
				entry3 = null;
				
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
//			    	 System.out.println(words[i]);
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
//				line += ","+rs2.getString("post_type");
//				System.out.println(line);
//				System.out.println(k);
				
				writer.println(line);
			   
			}
			
//			System.out.println(post.size());
//			System.out.println(post_line.size());
		
			
			writer.close();
			
			st2.close();
			
	   	}
			
	
}
