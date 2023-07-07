import Entity.network.ImageClassificatorNetwork;
import serializator.ImagesClassificatorSerializator;
import service.NeuralNetForImages;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class TestImagesNetwork {

    public static void main(String [] args){

        Character[] isLetter = new Character[] { 'a','o','i' , 't', 'm' , 'w'
        };
        List<List<Double>> learningData = new ArrayList<>();
        Character[] letters = new Character[] { 'a' ,
                                                'o',
                                                 'i'/*,
                                                 't',
                                                 'm',
                                                  'w'*/
        };

        List<ImageClassificatorNetwork> networks = new ArrayList<>();
        for (Character letter : letters) {
            ImageClassificatorNetwork network =
                    ImagesClassificatorSerializator.getPredictiveNetworkFromFile("neuralNetWorkImage_" + letter + ".net");
            networks.add(network);
        }
        List<List<NeuralNetForImages>> allImages = new ArrayList<>();
        List<NeuralNetForImages> imagesList;// = new ArrayList<>();
        for (Character letter : letters) {
            imagesList = ImagesClassificatorSerializator.getInputDataFromFile("learnClass_" + letter + ".net");
            allImages.add(imagesList);
        }
        for (List<NeuralNetForImages> images : allImages) {
            for (int i = 0; i < images.size(); i++) {
               Double minSimilarFunc = Double.MAX_VALUE;
               Integer numLetter = 0;
               for (int j = 0; j < networks.size(); j++) {
                   ImageClassificatorNetwork network = networks.get(j);
                   if (minSimilarFunc > TestImagesNetwork.encountSimilarFunction(images.get(i), network)){
                       minSimilarFunc = TestImagesNetwork.encountSimilarFunction(images.get(i), network);
                       numLetter = j;
                   }

               }

                System.out.println("image real = " + images.get(i).getLetter() + "  images test network = "
                        + letters[numLetter]);
                //learningData.add(images.get(i).getInputNet());
                //outputData.add(images.get(i).getOutNet());
            }
        }



/*
        for (List<Double> learn : learningData){

            System.out.println(network.encountNet(learn));
        }

        for (List<Double> learn : learningData){
            List<Double> output = network.encountNet(learn);
            Double max = Double.MIN_VALUE;

            int num = -1;
            for (int i = 0; i < output.size(); i++){
                if(max < output.get(i)){
                    max = output.get(i);
                    num = i;
                }
            }
            res.add(isLetter[num]);

        }*/

    }

    public static Double encountSimilarFunction(NeuralNetForImages inputData, ImageClassificatorNetwork network){
        List<Double> testOutput = inputData.getOutNet();
        List<Double> realOutput = network.encountNet(inputData.getInputNet());

        Double similarFunc = 0.0d;
        for (int i = 0; i < testOutput.size(); i++){
            similarFunc += Math.pow(testOutput.get(i) - realOutput.get(i), 2);
        }

        return similarFunc;
    }


}
