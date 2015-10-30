package post_classification.classification;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import post_classification.stemming.Version;
import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.SMOTE;
import weka.filters.unsupervised.attribute.Remove;

public class ClassifyNew implements Cloneable{

	public static void main(String args[]) throws Exception {

		Connection con = Version.connection();
		
		ClassifyNew.readOtherLevels(con);
		
int mb = 1024*1024;
		
		//Getting the runtime reference from system
		Runtime runtime = Runtime.getRuntime();
		
		System.out.println("##### Heap utilization statistics [MB] #####");
		
		//Print used memory
		System.out.println("Used Memory:" 
			+ (runtime.totalMemory() - runtime.freeMemory()) / mb);

		//Print free memory
		System.out.println("Free Memory:" 
			+ runtime.freeMemory() / mb);
		
		//Print total available memory
		System.out.println("Total Memory:" + runtime.totalMemory() / mb);

		//Print Maximum available memory
		System.out.println("Max Memory:" + runtime.maxMemory() / mb);
		 
	}
	
	public Object clone(){  
	    try{  
	        return super.clone();  
	    }catch(Exception e){ 
	        return null; 
	    }
	}
	
	public static void readLevel1(Connection con) throws Exception{
		
		
		 FilteredClassifier fc = null;
		 DataSource source = null;
		try {
			source = new DataSource("C:/Users/fabio/Google Drive/TESI - Q&A SL/TESI 2.0/Classificazione/features rappresentation/newInstances_l1.csv");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 Instances data = null;
		try {
			data = source.getDataSet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 // setting class attribute if the data format does not provide this information
		 // For example, the XRFF format saves the class attribute information as well
		 if (data.classIndex() == -1)
		   data.setClassIndex(data.numAttributes() - 1);
		 
		
		 fc = filter1();
		 model1(data,fc, con);
		 
		 data.delete();
	}
	
	public static void readOtherLevels(Connection con) throws Exception{
		
		 FilteredClassifier fc = null;
		 DataSource source = null;
//		try {
//			source = new DataSource("C:/Users/fabio/Google Drive/TESI - Q&A SL/TESI 2.0/Classificazione/features rappresentation/newInstances_l2.csv");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 Instances data = null;
//		try {
//			data = source.getDataSet();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 // setting class attribute if the data format does not provide this information
//		 // For example, the XRFF format saves the class attribute information as well
//		 if (data.classIndex() == -1)
//		   data.setClassIndex(data.numAttributes() - 1);
//	
//		 fc = filter1();
////		 model2(data,fc, con);
		 
		 FilteredClassifier fc2 = null;
		 DataSource source2 = null;
		 Instances data = null;
		 
		 try {
				source2 = new DataSource("C:/Users/fabio/Google Drive/TESI - Q&A SL/TESI 2.0/Classificazione/features rappresentation/newInstances_l3.csv");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Instances data2 = null;
			
			data = null;
			try {
				data = source2.getDataSet();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 // setting class attribute if the data format does not provide this information
			 // For example, the XRFF format saves the class attribute information as well
			 if (data.classIndex() == -1)
			   data.setClassIndex(data.numAttributes() - 1);
		
			 fc2 = filter1();
			 model3(data,fc2, con);
	}
	
	public static FilteredClassifier filter1(){
		
		 // filter
		 Remove rm = new Remove();
		 rm.setAttributeIndices("1");  // remove 1st attribute
		 
		 // meta-classifier
		 FilteredClassifier fc = new FilteredClassifier();
//		 fc.setFilter(rm);
		 return fc;
	}
	
	
	public static void model1(Instances data, FilteredClassifier fc, Connection con) throws Exception{
				
		ObjectInputStream ois = null;
		Classifier smo = null;
				
		ois = new ObjectInputStream(ClassifyNew.class.getResourceAsStream("/smo1.model"));
//			ois = new ObjectInputStream(
//			         new FileInputStream("C:/Users/fabio/Google Drive/TESI - Q&A SL/TESI 2.0/Classificazione/models/smo1.model"));
		  try {
			 smo = (Classifier) ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  try {
			ois.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		  FastVector values = new FastVector(2);		  	            
	        values.addElement("useful");
	        values.addElement("spam");       
	        
	        data.insertAttributeAt(new Attribute("post_type", values), data.numAttributes());
	        data.setClassIndex(data.numAttributes() - 1);
	        
	        
	        
//		  smo.setClassifier(smo);
		// label instances
		  for (int i = 0; i < data.numInstances(); i++) {
		    double clsLabel = smo.classifyInstance(data.instance(i));
//		    double[] confidence = smo.distributionForInstance(data.instance(i));
		    data.instance(i).setClassValue(clsLabel);
		    System.out.println( data.classAttribute().value((int) clsLabel));
		    String big = new BigDecimal(data.instance(i).value(0)).toPlainString();
		    System.out.println(big);
		    
		    updateNewInstances("_l1", data.classAttribute().value((int) clsLabel), big , con);
		    
		  }
		
	}
	
	
	public static void model2(Instances data, FilteredClassifier fc, Connection con) throws Exception{
		
		  ObjectInputStream ois = null;
		  Classifier smo = null;
		  ois = new ObjectInputStream(ClassifyNew.class.getClass().getResourceAsStream("/smo2.model"));
//			ois = new ObjectInputStream(
//			         new FileInputStream("C:/Users/fabio/Google Drive/TESI - Q&A SL/TESI 2.0/Classificazione/models/smo2.model"));
		  try {
			 smo = (Classifier) ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  try {
			ois.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		  FastVector values = new FastVector(2);		  	            
	        values.addElement("give_info");
	        values.addElement("need_info");       
	        
	        data.insertAttributeAt(new Attribute("post_type", values), data.numAttributes());
	        data.setClassIndex(data.numAttributes() - 1);
	        
//		  smo.setClassifier(smo);
		// label instances
		  for (int i = 0; i < data.numInstances(); i++) {
		    double clsLabel = smo.classifyInstance(data.instance(i));
		    data.instance(i).setClassValue(clsLabel);
		    System.out.println( data.classAttribute().value((int) clsLabel));
		    String big = new BigDecimal(data.instance(i).value(0)).toPlainString();
		    System.out.println(big);
		    
		    updateNewInstances("_l2", data.classAttribute().value((int) clsLabel), big, con );
		    
		  }
		
	}
	
	
	public static void model3(Instances data, FilteredClassifier fc, Connection con) throws Exception{
				
		  ObjectInputStream ois = null;
		  Classifier smo = null;
		  ois = new ObjectInputStream(ClassifyNew.class.getClass().getResourceAsStream("/smo3.model"));
//			ois = new ObjectInputStream(
//			         new FileInputStream("C:/Users/fabio/Google Drive/TESI - Q&A SL/TESI 2.0/Classificazione/models/smo3.model"));
		  try {
			 smo = (Classifier) ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  try {
			ois.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		  FastVector values = new FastVector(2);		  	            
	        values.addElement("si");
	        values.addElement("no");       
	        

	        data.insertAttributeAt(new Attribute("post_type", values), data.numAttributes());
	        data.setClassIndex(data.numAttributes() - 1);
	        
//		  smo.setClassifier(smo);
		// label instances
		  for (int i = 0; i < data.numInstances(); i++) {
			 Instance da = data.instance(i); 
		    double clsLabel = smo.classifyInstance(data.instance(i));
		    double[] confidence = smo.distributionForInstance(data.instance(i));
		    data.instance(i).setClassValue(clsLabel);
		   
		    System.out.println( data.classAttribute().value((int) clsLabel));
		    String big = new BigDecimal(data.instance(i).value(0)).toPlainString();
		    System.out.println(big);
		    
		    updateNewInstances("_l3", data.classAttribute().value((int) clsLabel), big, con );
		    
		  }
		
	}
	
	public static void crossValidation(Instances data,Classifier model){
		
	}

	
	public static void updateNewInstances(String level, String label, String id , Connection con) throws SQLException{
		
	   Statement st = null;
			     	   
	   String query = "UPDATE fb_post SET post_type"+level+" = '" + label + "' where fbpid = " + id;
	   String query2 = "UPDATE pre_proc_post2 SET post_type"+level+" = '" + label + "' where fbpid = " + id;
	   
	   int rs = 0;
	  	     
		try {
			st = con.createStatement();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs = st.executeUpdate(query);
			rs = st.executeUpdate(query2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}
	
}
