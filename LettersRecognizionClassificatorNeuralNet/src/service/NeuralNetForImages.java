package service;

import java.io.Serializable;
import java.util.List;

public class NeuralNetForImages implements Serializable {
    private Character letter;

    private List<Double> outNet;// = new ArrayList<>();
    private List<Double> inputNet;

    public NeuralNetForImages(Character letter, List<Double> inputNet, List<Double> outNet) {
        this.letter = letter;
        this.outNet = outNet;
        this.inputNet = inputNet;
    }

    public List<Double> getOutNet() { return outNet; }

    public void setOutNet(List<Double> outNet) { this.outNet = outNet; }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }

    public List<Double> getInputNet() {
        return inputNet;
    }

    public void setInputNet(List<Double> inputNet) {
        this.inputNet = inputNet;
    }

    @Override
    public String toString() {
        return "ImagesNeuralNet{" +
                "letter=" + letter +
                ", outNet=" + outNet +
                ", inputNet=" + inputNet +

                '}' + "\n\n\n";
    }
}