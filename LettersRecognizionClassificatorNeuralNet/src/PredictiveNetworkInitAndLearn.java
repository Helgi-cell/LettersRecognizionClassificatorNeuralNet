import Api.FinctionsApi.FunctionEncountingNodesInterface;
import Api.NeuralNetApi.NeuralNetI;
import Entity.network.PredictiveNetwork;
import Serializator.PredictiveNetworkSerializator;
import service.SigmoidFunction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PredictiveNetworkInitAndLearn implements Serializable {
    List<List<Double>> learningData = new ArrayList<>();
    List<List<Double>> outputData = new ArrayList<>();

    public PredictiveNetworkInitAndLearn() {
        createLearningData();
    }

    public static void main(String[] args) {


        PredictiveNetworkInitAndLearn rootPoint = new PredictiveNetworkInitAndLearn();
        //rootPoint.createLearningData();
        FunctionEncountingNodesInterface func = new SigmoidFunction();
        //FunctionEncountingNodesInterface func = new BipolarSigmoidFunction();

        rootPoint.learningNetwork(1, 1,
                4, 0.00000000000000000000000000000000000000999d
                , 0.15d, func);

    }


    public void learningNetwork(int numNeuronsInput, int numNeuronsOutput, int numHiddenLayers
                            , Double stepLearning, Double midSquareError, FunctionEncountingNodesInterface func) {

        NeuralNetI predictiveNetwork = new PredictiveNetwork(numNeuronsInput, numNeuronsOutput,
                numHiddenLayers, stepLearning, midSquareError, func);
     /*   predictiveNetwork.incrementNodes();
        predictiveNetwork.incrementNodes();
        predictiveNetwork.incrementNodes();
        predictiveNetwork.incrementNodes();
        predictiveNetwork.incrementNodes();
        predictiveNetwork.incrementNodes();
        predictiveNetwork.incrementNodes();
        predictiveNetwork.incrementNodes();
        predictiveNetwork.incrementNodes();
*/

        System.out.println(predictiveNetwork + "\n\n\n");

        Double midSqr = 0.0d;
        //Double growError = Double.MAX_VALUE;

        List<List<Double>> inpData = new ArrayList<>();
        List<List<Double>> outData = new ArrayList<>();
        List<Double> inputNet = new ArrayList<>();
        List<Double> outputNet = new ArrayList<>();


        for (int i = 0; i < this.learningData.size(); i++) {
            inpData.add(this.learningData.get(i));
            outData.add(this.outputData.get(i));

            int score = 0;
            //int scoreGrowError = 0;
            do {
                midSqr = 0.0d;

               // growError = 0.0d;

                for (int j = 0; j < inpData.size(); j++) {
                    inputNet = inpData.get(j);
                    outputNet = outData.get(j);
                    predictiveNetwork.encountNet(inputNet);
                    predictiveNetwork.encountDerivatives();
                    predictiveNetwork.encountNetErrors(inputNet, outputNet);
                    predictiveNetwork.encountWeight();
                }

                midSqr = predictiveNetwork.encountMidSquareError(inpData, outData);
                //midSqr = predictiveNetwork.encountMaxError(inpData, outData);

               /* if(growError >= midSqr){
                    //growError = midSqr;
                    scoreGrowError = 0;
                } else {
                    growError = midSqr;
                    scoreGrowError++;
                }

                if (scoreGrowError > 10){
                    predictiveNetwork.incrementNewLayer();
                    System.out.println("new layer = " + predictiveNetwork.getNumberHiddenLayers() + "\n num = " + (i + 1) + "\n\n\n");
                    growError = 0.0d;
                    scoreGrowError = 0;
                    //i = 0;
                    midSqr = 0.0d;
                }
*/
                score++;
                if (score > 0) {
                    System.out.println("error = " + midSqr + "\n num = " + (i + 1) + "\n\n\n");
                    predictiveNetwork.incrementNodes();

                    score = 0;
                }

                score++;
            } while (midSqr > (predictiveNetwork.getMidSquareError() + (i / 10)));

        //} while (midSqr > predictiveNetwork.getMidSquareError());

        }
           // System.out.println(predictiveNetwork + "\n\n");
            System.out.println("Number neurons in the each hidden layer = " + predictiveNetwork.getNumberNeuronsInHiddenLayer() + "\n\n");
        System.out.println("Number lsyers in hidden layers = " + predictiveNetwork.getNumberHiddenLayers() + "\n\n");

            System.out.println("error = " + midSqr + "\n\n\n");
            for (List<Double> learn : this.learningData){
                System.out.println("input = " + learn + "      output = " + predictiveNetwork.encountNet(learn));
            }


        PredictiveNetworkSerializator predictiveNetworkSerializator = new PredictiveNetworkSerializator();
        predictiveNetworkSerializator.writePredictiveNetworkToFile((PredictiveNetwork) predictiveNetwork, "fibonacci.net");
    }

    public void createLearningData(){


        Double [] inputArray = new Double[]
                {1.0, 2.0, 3.0, 5.0, 8.0, 13.0, 21.0, 34.0, 55.0, 89.0, 144.0, 233.0, 377.0, 610.0
                         };
        Double [] outputArray = new Double[]
                {2.0, 3.0, 5.0, 8.0, 13.0, 21.0, 34.0, 55.0, 89.0, 144.0, 233.0, 377.0, 610.0, 987.0
                         };



        for (int i = 0 ; i < inputArray.length; i++){
             List<Double> learning = new ArrayList<>();
             learning.add(inputArray[i]);
             this.learningData.add(learning);
         }


        for (int i = 0 ; i < outputArray.length; i++){
            List<Double> learning = new ArrayList<>();
            learning.add(outputArray[i]);
            this.outputData.add(learning);
        }

        PredictiveNetworkSerializator predictiveNetworkSerializator = new PredictiveNetworkSerializator();

        predictiveNetworkSerializator.writeInputDataToFile(this.learningData,"fibonacciInput.dat");
        predictiveNetworkSerializator.writeInputDataToFile(this.outputData,"fibonacciOutput.dat");

    }

    }