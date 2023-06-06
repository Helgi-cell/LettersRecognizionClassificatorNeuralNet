package Entity.layers;

import Api.FinctionsApi.FunctionEncountingNodesInterface;
import Api.LayersApi.LayerFinalI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OutputLayer implements LayerFinalI, Serializable {
    private List<Double> neurons = new ArrayList<>();
    private List <List<Double>> weigths = new ArrayList<>();
    private List <Double> threshold = new ArrayList<>();
    private FunctionEncountingNodesInterface function;

    public OutputLayer(Integer numNeurons, Integer numNeuronsPrevLayer, FunctionEncountingNodesInterface function) {
        initLayer(numNeurons, numNeuronsPrevLayer);
        this.function = function;
    }

    private void initLayer(Integer numNeurons, Integer numNeuronsPrevLayer) {
        for (int i = 0; i < numNeurons; i++) {
            this.neurons.add(0.0d);
            this.threshold.add((Math.random() / 10.0d - 0.015d)/10);
        }
        for (int i = 0; i < numNeurons; i++){
            List<Double> weightsToNeuron = new ArrayList<>();
            for (int j = 0; j < numNeuronsPrevLayer; j++){
                Double wght = (Math.random() / 10.0d - 0.015d)/10;
                weightsToNeuron.add(wght);
            }
            this.weigths.add(weightsToNeuron);
        }
    }

    @Override
    public List<Double> addWeightsByNewNeuronInPrevLayer(Integer prevNeurons) {

                for (int i = 0; i < this.weigths.size(); i++) {
                    Double wght = (Math.random() / 10.0d - 0.015d)/10;
                    List<Double> weighToNeuron = this.weigths.get(i);
                    weighToNeuron.add(wght);
                    this.weigths.set(i, weighToNeuron);
                }
      return this.neurons;
    }


    @Override
    public List<Double> encountNeuron(List<Double> prevNeurons) {
        List<Double> weightsByNeuron = new ArrayList<>();
        Double sumWeigths;// = 0.0d;
        for(int i = 0; i < this.neurons.size(); i++){
            sumWeigths = 0.0d;
            weightsByNeuron = this.weigths.get(i);
            for (int j = 0; j < prevNeurons.size(); j++){

                sumWeigths += weightsByNeuron.get(j) * prevNeurons.get(j);
            }
            sumWeigths = function.nodeResult(sumWeigths - this.threshold.get(i));
            this.neurons.set(i, sumWeigths);
        }

        return this.neurons;
    }

    @Override
    public void changeWeigths() {

    }

    public List<Double> getNeurons() {
        return neurons;
    }

    public void setNeurons(List<Double> neurons) {
        this.neurons = neurons;
    }

    public List<List<Double>> getWeigths() {
        return weigths;
    }

    public void setWeigths(List<List<Double>> weigths) {
        this.weigths = weigths;
    }

    public List<Double> getThreshold() {
        return threshold;
    }

    public void setThreshold(List<Double> threshold) {
        this.threshold = threshold;
    }

    public FunctionEncountingNodesInterface getFunction() {
        return function;
    }

    public void setFunction(FunctionEncountingNodesInterface function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return "OutputLayer{\n" +
                "neurons=" + neurons +
                ", \nweigths=" + weigths +
                ", \nthreshold=" + threshold +
                "}";
    }



}
