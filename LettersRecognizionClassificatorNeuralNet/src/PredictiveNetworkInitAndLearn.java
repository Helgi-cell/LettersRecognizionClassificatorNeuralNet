import Api.FinctionsApi.FunctionEncountingNodesInterface;
import Api.NeuralNetApi.NeuralNetI;
import Entity.network.ImageClassificatorNetwork;


import serializator.ImagesClassificatorSerializator;
import service.BipolarSigmoidFunction;
import service.NeuralNetForImages;
import service.SigmoidFunction;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PredictiveNetworkInitAndLearn implements Serializable {
    public List<List<Double>> learningData = new ArrayList<>();
    public List<List<Double>> outputData = new ArrayList<>();

    public Character[] letters = new Character[] { 'a', 'o', 'i' , 't', 'm' , 'w'

    };


    public PredictiveNetworkInitAndLearn() {
        loadDataFromFiles(this.letters);
    }

    public static void main(String[] args) throws IOException {


        PredictiveNetworkInitAndLearn rootPoint = new PredictiveNetworkInitAndLearn();
       // FunctionEncountingNodesInterface func = new SigmoidFunction();
        FunctionEncountingNodesInterface func = new BipolarSigmoidFunction();

        //rootPoint.loadDataFromFiles(rootPoint.letters);

        rootPoint.learningNetwork(rootPoint.learningData.get(0).size(), rootPoint.outputData.get(0).size(),
                2, 0.0019d //0.00019d
                , 0.0005d, func);

    }


    public void loadDataFromFiles(Character[] letters){

        List<List<NeuralNetForImages>> allImages = new ArrayList<>();
        List<NeuralNetForImages> imagesList = new ArrayList<>();
                for (Character letter : letters) {
                    imagesList = ImagesClassificatorSerializator.getInputDataFromFile("learnClass_" + letter + ".net");
                    allImages.add(imagesList);
                }
                for (List<NeuralNetForImages> images : allImages) {
                    for (int i = 0; i < images.size(); i++) {
                        this.learningData.add(images.get(i).getInputNet());
                        this.outputData.add(images.get(i).getOutNet());
                    }
                }
    }


    public void learningNetwork(int numNeuronsInput, int numNeuronsOutput, int numHiddenLayers
                            , Double stepLearning, Double midSquareError, FunctionEncountingNodesInterface func) throws IOException {

        ImageClassificatorNetwork predictiveNetwork;

        predictiveNetwork = ImagesClassificatorSerializator.getPredictiveNetworkFromFile("neuralNetWork.net");

        if (predictiveNetwork == null) {
            predictiveNetwork = new ImageClassificatorNetwork(numNeuronsInput, numNeuronsOutput,
                    numHiddenLayers, stepLearning, midSquareError, func);
        }

        Double midSqr = 0.0d;
        Double maxError = Double.MAX_VALUE;

        List<List<Double>> inpData = new ArrayList<>();
        List<List<Double>> outData = new ArrayList<>();
        List<Double> inputNet = new ArrayList<>();
        List<Double> outputNet = new ArrayList<>();
        int ifMustToWriteNetwork;// = 0;

        for (int i = 0; i < this.learningData.size(); i++) {
            inpData.add(this.learningData.get(i));
            outData.add(this.outputData.get(i));

            int score = 0;
            ifMustToWriteNetwork = 0;

            do {
                midSqr = 0.0d;
                for (int j = 0; j < inpData.size(); j++) {
                    inputNet = inpData.get(j);
                    outputNet = outData.get(j);
                    predictiveNetwork.encountNet(inputNet);
                    predictiveNetwork.encountDerivatives();
                    predictiveNetwork.encountNetErrors(inputNet, outputNet);
                    predictiveNetwork.encountWeight();
                }
                midSqr = predictiveNetwork.encountMidSquareError(inpData, outData);
                //score++;

                if (maxError > midSqr) {
                    score = 0;
                    maxError = midSqr;
                } else {
                    score ++;
                    maxError = midSqr;
                }


                System.out.println("score = " + score + " error = " + midSqr + "\n num = " + (i + 1)
                        + "\n   ifMustToWriteFile = " + ifMustToWriteNetwork + "\n\n\n");
                if (score > 5) {
                    System.out.println("NEW!!!! neuron ! error = "
                            + midSqr + "\n num = " + (i + 1)
                            + "\n\n\n");
                    predictiveNetwork.incrementNodes();
                    score = 0;
                    //ifMustToWriteNetwork = 0;
                }


                if(ifMustToWriteNetwork == 1000) {
                    ifMustToWriteNetwork = 0;
                    ImagesClassificatorSerializator.deleteFile("neuralNetWork.net");
                    System.out.println("The file started to write....");
                    ImagesClassificatorSerializator.writePredictiveNetworkToFile(
                                                                predictiveNetwork
                                                                ,"neuralNetWork.net");
                    System.out.println("The file have been written....");
                }
                ifMustToWriteNetwork++;
                //score++;
            } while (midSqr > (predictiveNetwork.getMidSquareError()// * (i + 1)/2//
                     ));


            ImagesClassificatorSerializator.deleteFile("neuralNetWork.net");
            System.out.println("The file started to write....");
            ImagesClassificatorSerializator.writePredictiveNetworkToFile(
                    predictiveNetwork
                    ,"neuralNetWork.net");
            System.out.println("The file have been written....");


           // predictiveNetworkSerializator.writePredictiveNetworkToFile(predictiveNetwork,"neuralNet.net");
        }


        //predictiveNetworkSerializator.writePredictiveNetworkToFile(predictiveNetwork,"neuralNet.net");


            System.out.println("Number neurons in the each hidden layer = " + predictiveNetwork.getNumberNeuronsInHiddenLayer() + "\n\n");
        System.out.println("Number lsyers in hidden layers = " + predictiveNetwork.getNumberHiddenLayers() + "\n\n");

            System.out.println("error = " + midSqr + "\n\n\n");
            for (List<Double> learn : this.learningData){
                System.out.println("input = "
                        //+ learn
                        + "      output = " + predictiveNetwork.encountNet(learn));
            }


            this.learningData.clear();
            loadDataFromFiles(this.letters);

        System.out.println("For letter i results :");
        for (List<Double> learn : this.learningData){
            System.out.println("input = "
                    //+ learn
                    + "      output = " + predictiveNetwork.encountNet(learn));
        }




/*        PredictiveNetworkSerializator predictiveNetworkSerializator = new PredictiveNetworkSerializator();
        predictiveNetworkSerializator.writePredictiveNetworkToFile((PredictiveNetwork) predictiveNetwork, "fibonacci.net");*/
    }






    }