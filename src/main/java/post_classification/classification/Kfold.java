package post_classification.classification;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import post_classification.stemming.Version;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.SerializedObject;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;

public class Kfold {

    public static boolean removeFirst = false;

    /**
     * Performs the cross-validation. See Javadoc of class for information on
     * command-line parameters.
     *
     * @param args the command-line parameters
     */
    public static void main(String[] args) {
        try {
        	
        	   Statement st = null;
        	  
               ResultSet rs = null;
               
        	
        	Connection con = null;
        	con = Version.connection();
    		
    		try {
    			st = con.createStatement();
    			
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
        	
        	
        	 String outputFile = new String(Utils.getOption("out", args));

             String line = new String("message § gold § prediction");
             String fold_n = new String("");
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
             //stampa la riga di intestazione
             writer.append(line);
             writer.newLine();

        	
            // loads data and set class index
            Instances data = DataSource.read(Utils.getOption("t", args));
            List<String> list = new ArrayList();
            for (int i = 0; i < data.numInstances(); i++) {
                list.add(data.instance(i).stringValue(0));
            }
            if (removeFirst) {
                data.deleteAttributeAt(0);
            }
            String clsIndex = Utils.getOption("c", args);
            if (clsIndex.length() == 0) {
                clsIndex = "last";
            }
            if (clsIndex.equals("first")) {
                data.setClassIndex(0);
            } else if (clsIndex.equals("last")) {
                data.setClassIndex(data.numAttributes() - 1);
            } else {
                data.setClassIndex(Integer.parseInt(clsIndex) - 1);
            }

            // classifier
            String[] tmpOptions;
            String classname;
            tmpOptions = Utils.splitOptions(Utils.getOption("W", args));
            classname = tmpOptions[0];
            tmpOptions[0] = "";
            Classifier cls = (Classifier) Utils.forName(Classifier.class, classname, tmpOptions);

            // other options
            int folds = Integer.parseInt(Utils.getOption("x", args));

            // randomize data
            Random rand = new Random(System.currentTimeMillis());
            Instances randData = new Instances(data);
            randData.randomize(rand);
            if (randData.classAttribute().isNominal()) {
                randData.stratify(folds);
            }

            // perform cross-validation
            System.out.println();
            System.out.println("=== Setup ===");
            System.out.println("Classifier: " + cls.getClass().getName() + " " + Utils.getGlobalInfo(cls, false));
            System.out.println("Dataset: " + data.relationName());
            System.out.println("Folds: " + folds);
            System.out.println();
            Evaluation evalAll = new Evaluation(randData);
            for (int n = 0; n < folds; n++) {
                Evaluation eval = new Evaluation(randData);
                Instances train = randData.trainCV(folds, n);
                Instances test = randData.testCV(folds, n);
                

                
                // the above code is used by the StratifiedRemoveFolds filter, the
                // code below by the Explorer/Experimenter:
                // Instances train = randData.trainCV(folds, n, rand);

                // build and evaluate classifier
                //Classifier clsCopy = Classifier.makeCopy(cls);
                Classifier clsCopy = (Classifier) new SerializedObject(cls).getObject();
                clsCopy.buildClassifier(train);
                eval.evaluateModel(clsCopy, test);
                evalAll.evaluateModel(clsCopy, test);

                int gold = 0;
                String gold_name = "";
                String predicted_name = "";
                int predicted = 0;
                int fold_number = n + 1;
                fold_n = new String("FOLD "+fold_number + " § § ");

                //stampa nel file
                writer.append(fold_n);
                writer.newLine();
                
                for (int i = 0; i < test.numInstances(); i++) {

                    gold = (int) test.instance(i).value(test.instance(i).classAttribute());
                    gold_name = train.classAttribute().value(gold);
                    predicted = (int) clsCopy.classifyInstance(test.instance(i));
                    predicted_name = test.classAttribute().value(predicted);
                    
                    try {
            			rs = st.executeQuery("SELECT * from qeanalysis.fb_post where fbpid ="+test.instance(i).value(1));
            			
            		} catch (SQLException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}
                    String message = "";
                    if(rs.next()){
                    	message = rs.getString("message");
                    	message = message.replace("\n", "");
                    }
                    
                    line = new String(message+"§"+ gold_name + "§" + predicted_name);

                    //System.out.println(gold + " " + predicted);

                    //stampa nel file
                    writer.append(line);
                    writer.newLine();

                    //System.out.println(testingData.instance(i).toString() + " " + cls.classifyInstance(testingData.instance(i)));
                }
               
                
                // output evaluation
                System.out.println();
                System.out.println(eval.toMatrixString("=== Confusion matrix for fold " + (n + 1) + "/" + folds + " ===\n"));
                System.out.println();
                System.out.println(eval.toSummaryString("=== " + folds + "-fold Cross-validation ===", false));
                System.out.println("Weighted F: " + eval.weightedFMeasure());
                System.out.println("Weighted AUC: " + eval.weightedAreaUnderROC());
                System.out.println("Weighted P: " + eval.weightedPrecision());
                System.out.println("Weighted P: " + eval.weightedRecall());
                Attribute classAttribute = data.classAttribute();
                int numValues = classAttribute.numValues();
                for (int i = 0; i < numValues; i++) {
                    System.out.println("F-" + classAttribute.value(i) + ": " + eval.fMeasure(i));
                }
            }

            // output evaluation
            System.out.println();
            System.out.println(evalAll.toSummaryString("=== " + folds + "-fold Cross-validation ===", false));
            System.out.println("Weighted F: " + evalAll.weightedFMeasure());
            Attribute classAttribute = data.classAttribute();
            int numValues = classAttribute.numValues();
            for (int i = 0; i < numValues; i++) {
                System.out.println("F-" + classAttribute.value(i) + ": " + evalAll.fMeasure(i));
            }
            writer.close();
            /*System.out.println();
             FastVector predictions = evalAll.predictions();
             for (int i=0;i<list.size();i++) {
             System.out.println(list.get(i)+"\t"+predictions.elementAt(i).toString());
             }*/
        } catch (Exception ex) {
            Logger.getLogger(Kfold.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
