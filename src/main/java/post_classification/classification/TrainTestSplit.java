package post_classification.classification;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;



public class TrainTestSplit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            
            
            //-out file di output 
            //-tr training
            //-ts test
            //-c class attribute
            // loads data 
            //Instances data = DataSource.read(Utils.getOption("t", args));

            //file con l'output della classificazion
            //scrive nella prima colonna il gold standard
            //nella seconda la prediction
            String outputFile = new String(Utils.getOption("out", args));

            String line = new String("gold,prediction");
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            //stampa la riga di intestazione
            writer.append(line);
            writer.newLine();

            Instances trainingData = DataSource.read(Utils.getOption("tr", args));
            Instances testingData = DataSource.read(Utils.getOption("ts", args));

            String clsIndex = Utils.getOption("c", args);
            if (clsIndex.length() == 0) {
                clsIndex = "last";
            }
            if (clsIndex.equals("first")) {
                trainingData.setClassIndex(0);
                testingData.setClassIndex(0);
            } else if (clsIndex.equals("last")) {
                trainingData.setClassIndex(trainingData.numAttributes() - 1);
                testingData.setClassIndex(testingData.numAttributes() - 1);
            } else {
                trainingData.setClassIndex(Integer.parseInt(clsIndex) - 1);
                testingData.setClassIndex(Integer.parseInt(clsIndex) - 1);
            }
            // classifier
            String[] tmpOptions;
            String classname;
            tmpOptions = Utils.splitOptions(Utils.getOption("W", args));
            classname = tmpOptions[0];
            tmpOptions[0] = "";
            Classifier cls = (Classifier) Utils.forName(Classifier.class, classname, tmpOptions);

            cls.buildClassifier(trainingData);

            Evaluation eval = new Evaluation(testingData);

            System.out.println();
            System.out.println("=== Setup ===");
            //System.out.println("Classifier: " + cls.getClass().getName() + " " + Utils.joinOptions(cls.getOptions()));
            System.out.println("Dataset: " + trainingData.relationName());
            System.out.println();

            int gold = 0;
            int predicted = 0;

            //train classifier
            //quindi stampo l'output per ogni istanza del test set, inclusa l'istanza stessa           
            for (int i = 0; i < testingData.numInstances(); i++) {

                gold = (int) testingData.instance(i).value(testingData.instance(i).classAttribute());
                predicted = (int) cls.classifyInstance(testingData.instance(i));
                line = new String(gold + ";" + predicted);

                //System.out.println(gold + " " + predicted);

                //stampa nel file
                writer.append(line);
                writer.newLine();

                //System.out.println(testingData.instance(i).toString() + " " + cls.classifyInstance(testingData.instance(i)));
            }

            //stampo le stastistiche
            eval.evaluateModel(cls, testingData);
            System.out.println("F:" + eval.weightedFMeasure());
            System.out.println("ROC:" + eval.weightedAreaUnderROC());
            System.out.println("Prec:" + eval.weightedPrecision());
            System.out.println("Rec:" + eval.weightedRecall());
            System.out.println("Precision class 0:" + eval.precision(0) +  "Recall class 0:" + eval.precision(0));
            System.out.println("Precision class 1:" + eval.precision(1) +  "Recall class 0:" + eval.precision(1));
                        
            //System.out.println("PRC:" + eval.weightedAreaUnderPRC());
            // output evaluation
            System.out.println();
            writer.close();

        } catch (Exception ex) {
            Logger.getLogger(TrainTestSplit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
