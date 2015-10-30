package post_classification.classification;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.supervised.instance.SMOTE;
import weka.filters.unsupervised.attribute.Remove;

public class ConstructModel {

	public static void main(String[] args) throws Exception {

		 FilteredClassifier fc = null;
		 DataSource source = null;
		try {
			source = new DataSource("C:/Users/fabio/Google Drive/TESI - Q&A SL/TESI 2.0/Classificazione/Classification 2/training test test - with stopwords_l3_rep.csv");
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
	
		 System.out.println( data.classAttribute().value(0));
//		 
		 fc = filter1();
		 constructModel3(data, fc);
		 
//		 crossValidation(data,fc);
		 
	}

	public SMOTE filterSmote(String[] options){
					
		SMOTE smote = new SMOTE();
		try {
			smote.setOptions(options);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return smote;
	}
	
	public static FilteredClassifier filter1(){
		
		 // filter
		 Remove rm = new Remove();
		 rm.setAttributeIndices("1");  // remove 1st attribute
		 
		 // meta-classifier
		 FilteredClassifier fc = new FilteredClassifier();
		 fc.setFilter(rm);
		 return fc;
	}
	
	// i constructModel fanno la stessa cosa (è differente solo il file di salvataggio) potrebbero quindi essere cancellati gli altri due
	public static void constructModel1(Instances data, FilteredClassifier fc) throws Exception{
				
//		SMOTE smote;
//		ConstructModel filter = new ConstructModel();
		
		String[] smoteOptions = new String[]{"-C","0","-K", "5", "-P", "100.0", "-S", "1"};
		
		// create new instance of scheme
		 weka.classifiers.functions.SMO scheme = new weka.classifiers.functions.SMO();
		 // set options
		 scheme.setOptions(weka.core.Utils.splitOptions("-C 1.0 -L 0.0010 -P 1.0E-12 -N 0 -V -1 -W 1 -K \"weka.classifiers.functions.supportVector.PolyKernel -C 250007 -E 1.0\""));
		 
//		 System.out.println(data.numInstances());
//    	smote = filter.filterSmote(smoteOptions);
//	  	smote.setInputFormat(data);
//		data = Filter.useFilter(data , smote);
//		 System.out.println(data.numInstances());
	
		 fc.setClassifier(scheme);
		 fc.buildClassifier(data);
		
		// serialize model
		 ObjectOutputStream oos = new ObjectOutputStream(
		                            new FileOutputStream("C:/Users/fabio/Google Drive/TESI - Q&A SL/TESI 2.0/Classificazione/models/smo1.model"));
		 oos.writeObject(fc);
		 oos.flush();
		 oos.close();

	}
	
	
public static void constructModel2(Instances data, FilteredClassifier fc) throws Exception{
		
		
		String[] smoteOptions = new String[]{"-C","0","-K", "5", "-P", "100.0", "-S", "1"};
	
		// create new instance of scheme
		 weka.classifiers.functions.SMO scheme = new weka.classifiers.functions.SMO();
		 // set options
		 scheme.setOptions(weka.core.Utils.splitOptions("-C 1.0 -L 0.0010 -P 1.0E-12 -N 0 -V -1 -W 1 -K \"weka.classifiers.functions.supportVector.PolyKernel -C 250007 -E 1.0\""));
			 
		 fc.setClassifier(scheme);
		 fc.buildClassifier(data);
		
		// serialize model
		 ObjectOutputStream oos = new ObjectOutputStream(
		                            new FileOutputStream("C:/Users/fabio/Google Drive/TESI - Q&A SL/TESI 2.0/Classificazione/models/smo2.model"));
		 oos.writeObject(fc);
		 oos.flush();
		 oos.close();

	}
	
public static void constructModel3(Instances data, FilteredClassifier fc) throws Exception{
	
	
	String[] smoteOptions = new String[]{"-C","0","-K", "5", "-P", "100.0", "-S", "1"};
	
	// create new instance of scheme
	 weka.classifiers.functions.SMO scheme = new weka.classifiers.functions.SMO();
	 // set options
	 scheme.setOptions(weka.core.Utils.splitOptions("-C 1.0 -L 0.0010 -P 1.0E-12 -N 0 -V -1 -W 1 -K \"weka.classifiers.functions.supportVector.PolyKernel -C 250007 -E 1.0\""));
	 
	 fc.setClassifier(scheme);
	 fc.buildClassifier(data);
	
	// serialize model
	 ObjectOutputStream oos = new ObjectOutputStream(
	                            new FileOutputStream("C:/Users/fabio/Google Drive/TESI - Q&A SL/TESI 2.0/Classificazione/models/smo3.model"));
	 oos.writeObject(fc);
	 oos.flush();
	 oos.close();

}
	public static void crossValidation(Instances data,FilteredClassifier fc) throws Exception{
		
		// create new instance of scheme
				 weka.classifiers.functions.SMO smo = new weka.classifiers.functions.SMO();
				 // set options
				 smo.setOptions(weka.core.Utils.splitOptions("-C 1.0 -L 0.0010 -P 1.0E-12 -N 0 -V -1 -W 1 -K \"weka.classifiers.functions.supportVector.PolyKernel -C 250007 -E 1.0\""));
		
				 fc.setClassifier(smo);
				 fc.buildClassifier(data);
				 
		StringBuffer predictions = new StringBuffer();
		weka.core.Range attsToOutput = null; 
		Boolean outputDistribution = new Boolean(true); 
		Evaluation eval = new Evaluation(data);
		
		 eval.crossValidateModel(smo, data, 10, new Random(1), predictions, attsToOutput,outputDistribution );
		
		FastVector fv = eval.predictions();
		 
		 System.out.println(  eval.predictions());
	
	}
	
	

	
}
