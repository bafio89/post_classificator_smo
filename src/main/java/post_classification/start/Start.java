package post_classification.start;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.net.ftp.FTPClient;

import post_classification.stemming.Stemming;
import post_classification.stemming.Version;
import post_classification.classification.ClassifyNew;
import post_classification.classification.Kfold;
import post_classification.classification.TrainTestSplit;
import post_classification.wordFeatures.WordFeaturesNewInstances;

public class Start {
	
	public static Connection con = null;
	
	public Start(){
		
		Timer timer = new Timer();
	   
	      // scheduling the task at interval
		timer.schedule(new MyTimer(),1000, 2222000);
		
	}

	public class MyTimer extends TimerTask {
		// this method performs the task
	   public void run() {
		
		 con = Version.connection(); 
		   
		  try {
			Start.createFileLog();
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	     
		  System.out.println("timer working");
		  
		  try {
			Start.setSpam();
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		 	      
//	  	try {
//			Stemming.stemming(con);
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
	  	
	  	try {
			Start.writeLog(" Stemming ");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	  
	  	
		try {
			WordFeaturesNewInstances.wordFeaturesHash("_l1" ,con);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Start.writeLog(" Level 1 features rappr ");
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		try {
			ClassifyNew.readLevel1(con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Start.writeLog(" Classification first level ");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		
		try {
			Start.removeImages();
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
//		Start.removeSpam();
				
		try {
			Start.writeLog(" Spam removed ") ;
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//La lettura può essere fatta una sola volta ?? 
		try {
			WordFeaturesNewInstances.wordFeaturesHash("_l3", con);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		try {
			Start.writeLog("Level 3 features rappr ");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ClassifyNew.readOtherLevels(con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Start.writeLog(" Classification others level ");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Start.removeImages();
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
//		Start.removeNotLasting();
		
		try {
			Start.writeLog(" Not Lasting removed ");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
		System.out.println("end of work work");
		System.exit(0);
	   }   
	     
	}
	
	public static void main(String[] args) throws Exception {
		
		
		Start start = new Start();
//		String dataFile = new String("C:/Users/fabio/Google Drive/TESI - Q&A SL/TESI 2.0/Classificazione/classification 2/training test test - with stopwords_l2_rep.arff");
//        String predictionFile = new String("C:/Users/fabio/Google Drive/TESI - Q&A SL/TESI 2.0/Classificazione/classification 2/report/out_l2.txt");      
//
//        String[] ma = new String[]{"-t", dataFile,
////        			"-ts", dataFile,
//		            "-x", "10",
//		            "-out", predictionFile,
//		            "-c", "last",
//		            "-W", "weka.classifiers.functions.SMO -C 0.05 -L 0.001 -P 1.0E-12 -N 0 -V -1 -W 1 -K \"weka.classifiers.functions.supportVector.PolyKernel -E 1.0 -C 250007\""};
//
//			   Kfold.main(ma);
//        	TrainTestSplit.main(ma);
			   
			   
////			Stemming.stemming();
//			WordFeaturesNewInstances.wordFeaturesHash("_l1");
//			ClassifyNew.readLevel1();
////			start.removeSpam();
//// La lettura può essere fatta una sola volta ?? 
//			WordFeaturesNewInstances.wordFeaturesHash("_l2");
//		
//			ClassifyNew.readOtherLevels();
////			start.removeNotLasting();
	}
	
	public static void setSpam() throws SQLException{
		
		Statement st = null;
				
		int rs = 0;
		
		String query = "UPDATE fb_post SET post_type_l1 = 'spam'  where message is null";
				   
		try {
			st = con.createStatement();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 try {
				rs = st.executeUpdate(query);
			   } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   }
			   
		 
		
	}
	
	public static void removeImages() throws SQLException, IOException{
		
			Statement st = null;
		  		    
		   ResultSet rs = null;
		   
		   int rs2 = 0;
		   
		   String path = "";
		   
		   File file = null;
		 //VERIFICA
		   String query = "SELECT photo_fb_post.medium FROM qeanalysis.fb_post, qeanalysis.photo_fb_post where (post_type_l1 like \'%spam%\' OR post_type_l3 like \'%no%\') and fbpid = element_id";
		   
		   String query2 = "DELETE FROM qeanalysis.photo_fb_post where element_id IN (SELECT fbpid from qeanalysis.fb_post where post_type_l1 like '%spam%' OR post_type_l3 like '%no%') ";
		   	
		   FTPClient client = new FTPClient();
		  
		   client.connect("ftp.universitree.com", 21);
		   int replyCode = client.getReplyCode();
		   client.login("bafio", "Mrtwister89");
		    replyCode = client.getReplyCode();
		  
		    
		    
			   
			try {
				st = con.createStatement();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		   try {
			rs = st.executeQuery(query);
		   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
		   
		   
		   while(rs.next()){
			   
			   path = rs.getString("medium");
			   path = path.replace("/home/bafio", "");
			   			   
			   client.deleteFile(path);
			   			   
			  
		   }
		   
		   try {
			   rs2 = st.executeUpdate(query2);
		   } catch (SQLException e) {
			   
		   }

		   client.disconnect();
	}
	
	public static void removeSpam(){
			
			Statement st = null;   
		   		   	   
		   String query = "DELETE from fb_post where post_type_l1 like '%spam%'";		  
		   
		   int rs = 0;		   
		   
		   
			   
			try {
				st = con.createStatement();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				
				rs = st.executeUpdate(query);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				 
	}
	
	public static void removeNotLasting(){
		
		   Statement st = null;
		   
		    
		   
		   String query = "DELETE from fb_post where post_type_l3 like 'no'";
		   
		   int rs = 0;
		   
		    
			   
			try {
				st = con.createStatement();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				rs = st.executeUpdate(query);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
	}
	
	
	public static void createFileLog() throws FileNotFoundException, IOException{
		new FileOutputStream("C:/Users/fabio/Documents/Daily classification/output.txt", false).close();
		
	}
	
	public static void writeLog(String stage) throws FileNotFoundException, IOException{
		
		Statement st = null;
		 
		ResultSet rs = null;
		PrintWriter writer = null;
		Integer count = null;
		
	 	
		try {
//			st = con.createStatement();
			st = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			try {
				rs = st.executeQuery("SELECT count(*) AS total from fb_post ");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		try {
			rs.next();
			count = rs.getInt("total");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			writer = new PrintWriter(new FileOutputStream(new File("C:/Users/fabio/Documents/Daily Classification/output.txt"),true));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		writer.println(" \n ");		
		writer.println(stage +  " OK ");
		writer.println(" Instances n: " + count);
		
		writer.close();
		
	}

}
