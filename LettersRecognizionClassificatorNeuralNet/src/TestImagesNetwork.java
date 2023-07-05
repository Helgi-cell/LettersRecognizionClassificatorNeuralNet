import Entity.network.ImageClassificatorNetwork;
import serializator.ImagesClassificatorSerializator;
import service.NeuralNetForImages;

import java.util.ArrayList;
import java.util.List;

public class TestImagesNetwork {

    public static void main(String [] args){

        Character[] isLetter = new Character[] { 'a','o','i' , 't', 'm' , 'w'
        };
        List<List<Double>> learningData = new ArrayList<>();
        Character[] letters = new Character[] { 'a',
                                                'o',
                                                 'i',
                                                 't',
                                                 'm',
                                                  'w'
        };


        List<List<NeuralNetForImages>> allImages = new ArrayList<>();
        List<NeuralNetForImages> imagesList = new ArrayList<>();
        for (Character letter : letters) {
            imagesList = ImagesClassificatorSerializator.getInputDataFromFile("learnClass_" + letter + ".net");
            allImages.add(imagesList);
        }
        for (List<NeuralNetForImages> images : allImages) {
            for (int i = 0; i < images.size(); i++) {
                learningData.add(images.get(i).getInputNet());
                //outputData.add(images.get(i).getOutNet());
            }
        }

        ImageClassificatorNetwork network =
                ImagesClassificatorSerializator.getPredictiveNetworkFromFile("neuralNetWorkImage_a.net");

        List<Character> res = new ArrayList<>();

        System.out.println("For letter  results :");

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

        }

        System.out.println("input = "
                + "      output = " + res);




    }

}
