package post_classification.stemming;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import post_classification.classification.ClassifyNew;
import weka.core.Stopwords;
import weka.core.stemmers.SnowballStemmer;
import post_classification.wordFeatures.WordFeaturesNewInstances;

public class Stemming {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

//		stemming();

	}
	
	public static void stemming(Connection con) throws SQLException{
		 	 
	        Statement st = null;
	        ResultSet rs = null;
	        Statement st2 = null;
	        int rs2 = -1;
	        
	        String query  = "";
	       int z = 0;
	        
	        String message = "";
	        String[] words = null;
	        
	        String new_message = "";
	        
	      
	    SnowballStemmer stem = new SnowballStemmer();
		stem.setStemmer("italian");
	        
		 
			
		try {
			st = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
//			rs = st.executeQuery("Select fbpid, message, post_type from fb_post where message != ''");
			rs = st.executeQuery("Select fbpid, message, post_type_l1, post_type_l2, post_type_l3 from fb_post where post_type_l1 is null limit 3000");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

  try {
		while (rs.next()) {          
		     z +=1;
		     
		     
		     message  = rs.getString("message");
		     
		     if(message != null){
		     System.out.println(rs.getString("fbpid"));
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
		     message = message.replace("\"", " ");
		     message = message.replace("'", " ");
		     message = message.trim();		     
		     
//		     System.out.println(z + message);
		     words = message.split(" ");  
		     
		     for (int i = 0 ; i< words.length; i++ )  
		     {  
		     			     	
		     		words[i] = stem.stem(words[i]);
		     		new_message += words[i] + " ";
		     	
		     	
		     	
		     }  
//		     new_message = new_message.replace("'", "\\'"); 
//		     System.out.println(new_message); 
		     query = "INSERT INTO pre_proc_post2 values (" + rs.getString("fbpid") +  ",'"+ new_message + "','"+ rs.getString("post_type_l1")+ "','"+ rs.getString("post_type_l2") + "','"+ rs.getString("post_type_l3") + "')";
		     st2 = con.createStatement();
		     rs2 = st2.executeUpdate(query);
		     
		    
		     new_message = "";
		     }
		 }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	st.close();
	st2.close();
	
	} 
	
	public static void StemmingAndStopwords(){ 
	 Connection con = null;
     Statement st = null;
     ResultSet rs = null;
     Statement st2 = null;
     int rs2 = -1;
     
     String query  = "";
    int z = 0;
     
     String message = "";
     String[] words = null;
     
     String new_message = "";
     
     Stopwords stop = new Stopwords();
		try {
			stop.read("C:\\it.txt");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		stop.elements();
		
		SnowballStemmer stem = new SnowballStemmer();
		stem.setStemmer("italian");
		
		
     
	 con = Version.connection();
		
	try {
		st = con.createStatement();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		rs = st.executeQuery("Select fbpid, message, post_type from fb_post where message != ''");
//		rs = st.executeQuery("Select fbpid, message, post_type from fb_post where fbpid = '119838214833262'");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

try {
	while (rs.next()) {          
	     z +=1;
	     
	     message  = rs.getString("message");
	     System.out.println(rs.getString("fbpid"));
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
	     message = message.trim();		     
	     
	     System.out.println(z + message);
	     words = message.split(" ");  
	     
	     for (int i = 0 ; i< words.length; i++ )  
	     {  
	     	if(stop.is(words[i])){
	     		words[i] = "";
	     	}else{
	     	
	     		words[i] = stem.stem(words[i]);
	     		new_message += words[i] + " ";
	     	}
	     	
	     	
	     }  
	     new_message = new_message.replace("'", "\\'"); 
	     System.out.println(new_message); 
	     query = "INSERT INTO pre_proc_post values (" + rs.getString("fbpid") +  ",'"+ new_message + "','"+ rs.getString("post_type")+"')";
	     st2 = con.createStatement();
	     rs2 = st2.executeUpdate(query);
	     
	    
	     new_message = "";
	 }
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	
//	Stopwords stop = new Stopwords();
//	stop.read("C:\\it.txt");
//	stop.elements();
//	
//	if(stop.is("vfad"))
//		System.out.println("baanan");
//	else
//		System.out.println("caboche");
//	SnowballStemmer stem = new SnowballStemmer();
//	
//	String [] stringhe = {"italian"};
//	
//	
//	stem.setStemmer("italian");
//	
//	stringhe = stem.getOptions();
//	
//	for(int i = 0 ; i < stringhe.length; i++){
//		
//		System.out.println(stringhe[i]);
//	}
//	
//	
////	System.out.println(stem.getStemmer()	);
//		
//	System.out.println(stem.stem("ragazzo"));
}

}

