



import serializator.ImagesClassificatorSerializator;
import service.OperatingImage;
import service.NeuralNetForImages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Image {
    public static void main(String[] args) throws IOException, InterruptedException {

        String [] files = new String [] {
                                         "a1.png", "a2.png","a3.png","a4.png","a5.png","a6.png","a7.png","a8.png",
                                         "a9.png", "a10.png","a11.png","a12.png","a13.png","a14.png","a15.png","a16.png"
                                        };
        //Character letter = 'a';
        Character[] letters = new Character[] { 'a', 'o', 'i' , 't', 'm' , 'w'

                                                };



        for (Character letter : letters) {

            List<Double> outByLetter = new ArrayList<>();
            outByLetter.add(100.0d);


            //OperatingImage picture = new OperatingImage(files);
            OperatingImage picture = new OperatingImage(letter);
            List<NeuralNetForImages> arrTemplates = new ArrayList<>();

            for (List<Double> input : picture.learnInputData) {
                arrTemplates.add(new NeuralNetForImages(letter, input, outByLetter));
            }

            ImagesClassificatorSerializator.writeInputDataToFile(arrTemplates, "learnClass_" + letter + ".net");
            arrTemplates = ImagesClassificatorSerializator.getInputDataFromFile("learnClass_" + letter + ".net");

            System.out.println(arrTemplates);
        }

    }

}

