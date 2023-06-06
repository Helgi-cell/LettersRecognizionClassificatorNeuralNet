package Entity.network;

import Api.FinctionsApi.FunctionEncountingNodesInterface;
import Api.LayersApi.LayerCommonI;
import Api.NeuralNetApi.NeuralNetI;
import Entity.layers.HiddenLayer;
import Entity.layers.InputLayer;
import Entity.layers.OutputLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PredictiveNetwork implements NeuralNetI, Serializable {

    private List<LayerCommonI> layers = new ArrayList<>();
    private Double stepLearnimg;
    private Double midSquareError;
    private FunctionEncountingNodesInterface func;
    private List<List<Double>> derivatives = new ArrayList<>();
    private List<List<Double>> netErrors = new ArrayList<>();
    private Stack<List<Double>> nodesStack = new Stack<>();
    private Stack<List<Double>> derivateStack = new Stack<>();
    private Stack<List<Double>> errorsStack = new Stack<>();
    private Stack<List<List<Double>>> weigthStack = new Stack<>();
    private Stack<List<Double>> thresholdStack = new Stack<>();



    public PredictiveNetwork(Integer numNeuronsInputLayer, Integer nimNeuronsOutputLayer,
                             Integer numHiddenLayers, Double stepLearnimg, Double midSquareError,
                             FunctionEncountingNodesInterface func) {
        this.stepLearnimg = stepLearnimg;
        this.midSquareError = midSquareError;
        this.func = func;
        initNetwork(numNeuronsInputLayer, nimNeuronsOutputLayer, numHiddenLayers);

    }



    @Override
    public List<LayerCommonI> initNetwork(Integer numNeuronsInputLayer, Integer nimNeuronsOutputLayer,
                                          Integer numHiddenLayers) {

        this.layers.add(new InputLayer(numNeuronsInputLayer));
        int numNeuprev = numNeuronsInputLayer;
        int numNeu = numNeuronsInputLayer + 1;
        for(int i = 0; i < numHiddenLayers; i++){
            this.layers.add(new HiddenLayer(numNeu, numNeuprev, this.func ));
            numNeuprev = numNeu;
        }
        this.layers.add(new OutputLayer(nimNeuronsOutputLayer, numNeu, this.func));

        for(int i = 0; i < numHiddenLayers; i++){
            List<Double> errorNode = new ArrayList<>();
            for (int j = 0; j < numNeu; j++){
                errorNode.add(0.0d);
            }
            this.netErrors.add(errorNode);
            this.derivatives.add(errorNode);
        }

        List<Double> errorNode = new ArrayList<>();
        for(int i = 0; i < nimNeuronsOutputLayer; i++){
                errorNode.add(0.0d);
        }
        this.netErrors.add(errorNode);
        this.derivatives.add(errorNode);

        return this.layers;
    }

    @Override
    public List<Double> encountNet(List<Double> templateOfLearning) {
        InputLayer inputLayer = (InputLayer) this.layers.get(0);
        List<Double> neuronsActivation = inputLayer.encountNeuron(templateOfLearning);
        for (int i = 1; i < this.layers.size() -1; i++){
            HiddenLayer hiddenLayer = (HiddenLayer)  this.layers.get(i);
            neuronsActivation = hiddenLayer.encountNeuron(neuronsActivation);
        }
        OutputLayer outputLayer = (OutputLayer)  this.layers.get(this.layers.size() - 1);
        neuronsActivation = outputLayer.encountNeuron(neuronsActivation);
        for (int i = 0; i < neuronsActivation.size(); i++){
            neuronsActivation.set(i, neuronsActivation.get(i) *  templateOfLearning.get(i) + templateOfLearning.get(i));
            //neuronsActivation.set(i, neuronsActivation.get(i) *  templateOfLearning.get(i));
            //neuronsActivation.set(i, neuronsActivation.get(i));
        }
        return neuronsActivation;
    }

    @Override
    public List<List<Double>> encountDerivatives() {
        List<Double> layerDerivatives = new ArrayList<>();
        this.derivatives.clear();
        for(int i = 1; i < this.layers.size() -1; i++){
            for (Double neuron: ((HiddenLayer) this.layers.get(i)).getNeurons()) {
                layerDerivatives.add(func.derivativeResultOfNode(neuron));
            }
            this.derivatives.add(layerDerivatives);
            layerDerivatives = new ArrayList<>();
        }
        for (Double neuron:((OutputLayer) this.layers.get(this.layers.size() -1)).getNeurons()) {
        layerDerivatives.add(func.derivativeResultOfNode(neuron));
        }
        this.derivatives.add(layerDerivatives);

        return this.derivatives;
    }


    @Override
    public List<List<Double>> encountNetErrors(List<Double> inputTemplateOfLearning, List<Double> outputTemplateOfLearning) {
        this.errorsStack.clear();
        this.derivateStack.clear();
        this.weigthStack.clear();
        List<Double> errors = new ArrayList<>();
        OutputLayer outputLayer = (OutputLayer) this.layers.get(this.layers.size() - 1);

        for (int i = 0; i < outputLayer.getNeurons().size(); i++){
            errors.add(outputLayer.getNeurons().get(i) //* inputTemplateOfLearning.get(i)
                    //   + inputTemplateOfLearning.get(i)
                    - outputTemplateOfLearning.get(i));
        }
        this.netErrors.set(this.netErrors.size() - 1, errors);
        OutputLayer layerCommon = (OutputLayer) this.layers.get(this.layers.size() - 1);
        List<List<Double>> weigths = layerCommon.getWeigths();
        this.netErrors.set(this.netErrors.size() - 1, errors);
            fillStackEncountErrors();
            Double sumGamma = 0.0d;
        List<Double> listDeriv = this.derivateStack.pop();
        List<Double> nextErrors = this.errorsStack.pop();
        List<List<Double>> nextWeigths = this.weigthStack.pop();

        while (!this.errorsStack.empty()) {
            errors = this.errorsStack.pop();
                for (int j = 0; j < errors.size(); j++ ) {
                    for (int i = 0; i < nextErrors.size(); i++){
                        sumGamma += listDeriv.get(i) * nextErrors.get(i) * nextWeigths.get(i).get(j);
                    }
                    errors.set(j, sumGamma);
                    sumGamma = 0.0d;
                }
        }
        return this.netErrors;
    }

    @Override
    public Double encountMidSquareError(List<List<Double>> inputLearningData, List<List<Double>> outputLearningData) {
            Double midError = 0.0d;
            List<Double> inpData;
            List<Double> outData;
            for (int i = 0; i < inputLearningData.size(); i++){
                inpData = inputLearningData.get(i);
                outData = outputLearningData.get(i);
                encountNet(inpData);
                encountDerivatives();
                encountNetErrors(inpData, outData);
                //encountWeight();
                encountNet(inpData);
                List<Double> errors = this.netErrors.get(this.netErrors.size() -1);
                for(Double err : errors){
                    midError += Math.pow(err, 2);
                }
            }

        return midError * 0.5d;
    }

    @Override
    public Double encountMaxError(List<List<Double>> inputLearningData, List<List<Double>> outputLearningData) {
        Double midError = Double.MIN_VALUE;
        List<Double> inpData;
        List<Double> outData;
        for (int i = 0; i < inputLearningData.size(); i++){
            inpData = inputLearningData.get(i);
            outData = outputLearningData.get(i);
            encountNet(inpData);
            encountDerivatives();
            encountNetErrors(inpData, outData);
            //encountWeight();
            encountNet(inpData);
            List<Double> errors = this.netErrors.get(this.netErrors.size() -1);
            for(Double err : errors){
                if (midError < Math.abs(err)){
                    midError =  Math.abs(err);
                }
            }
        }

        return midError;
    }


    private void fillStackEncountErrors(){

        for (List<Double> err : this.netErrors){ this.errorsStack.push(err); }
        for (List<Double> der : this.derivatives){ this.derivateStack.push(der);}

        HiddenLayer hiddenLayer;
        for(int i = 2; i < this.layers.size() - 1; i++){
            hiddenLayer = (HiddenLayer)this.layers.get(i);
            this.weigthStack.push(hiddenLayer.getWeigths());
        }
        OutputLayer outputLayer;
        outputLayer = (OutputLayer) this.layers.get(this.layers.size() - 1);
        this.weigthStack.push(outputLayer.getWeigths());
    }


    @Override
    public Double encountWeight() {
        fillStackEncountWeigths();
        List<List<Double>> weigthsOfLayer;
        List<Double> derivativesOfLayer;
        List<Double> errorsOfLayer;
        List<Double> prevNodes;
        List<Double> thresholdOfLayer;
        while (!thresholdStack.empty()){//!this.weigthStack.empty()){
            weigthsOfLayer = this.weigthStack.pop();
            derivativesOfLayer = this.derivateStack.pop();
            errorsOfLayer = this.errorsStack.pop();
            prevNodes = this.nodesStack.pop();
            thresholdOfLayer = this.thresholdStack.pop();
            for (int i = 0; i < thresholdOfLayer.size(); i++ ){
                thresholdOfLayer.set(i, (thresholdOfLayer.get(i)
                                        + this.stepLearnimg * errorsOfLayer.get(i) * derivativesOfLayer.get(i)));
            }


            for (int j = 0; j < weigthsOfLayer.size(); j++){
                    List<Double> wgth = weigthsOfLayer.get(j);

                for(int i = 0; i < weigthsOfLayer.get(j).size(); i++){
                    wgth.set(i,wgth.get(i) - this.stepLearnimg * errorsOfLayer.get(j)
                                                                        * derivativesOfLayer.get(j) * prevNodes.get(i));

                }
            }
        }

        return null;
    }

    private void fillStackEncountWeigths(){
        HiddenLayer hiddenLayer;
        for (int i = 1; i < this.layers.size() - 1; i++){
            hiddenLayer = (HiddenLayer) this.layers.get(i);
            this.weigthStack.push(hiddenLayer.getWeigths());
            this.thresholdStack.push(hiddenLayer.getThreshold());
        }

        OutputLayer outputLayer = (OutputLayer) this.layers.get(this.layers.size() - 1);
        this.weigthStack.push(outputLayer.getWeigths());
        this.thresholdStack.push(outputLayer.getThreshold());

        for (List<Double> err : this.netErrors){ this.errorsStack.push(err); }
        for (List<Double> der : this.derivatives){ this.derivateStack.push(der);}

        InputLayer inputLayer = (InputLayer) this.layers.get(0);
        this.nodesStack.push(inputLayer.getNeurons());
        for (int i = 1; i < this.layers.size() - 1; i++){
            hiddenLayer = (HiddenLayer) this.layers.get(i);
            this.nodesStack.push(hiddenLayer.getNeurons());
        }
    }

    @Override
    public List<LayerCommonI> incrementNodes() {
        InputLayer inputLayer = (InputLayer) this.layers.get(0);
        int prevNodes = inputLayer.getNeurons().size();

        for(int i = 1; i < this.layers.size() - 1; i++){
            HiddenLayer hiddenLayer = (HiddenLayer) this.layers.get(i);
            hiddenLayer.setNeurons(hiddenLayer.addNeuronInLayer(prevNodes));
            layers.set(i,hiddenLayer);
            prevNodes = hiddenLayer.getNeurons().size();
        }

        OutputLayer outputLayer = (OutputLayer) this.layers.get(this.layers.size() - 1);
        outputLayer.addWeightsByNewNeuronInPrevLayer(prevNodes);

        for (int i = 1; i < this.layers.size() -1; i++ ){
            this.netErrors.get(i - 1).add(0.0d);
        }


        return this.layers;
    }

    @Override
    public List<LayerCommonI> incrementNewLayer() {
        HiddenLayer prevHiddenLayer = (HiddenLayer) this.layers.get(1);
        HiddenLayer newHidenlayer = new HiddenLayer(prevHiddenLayer.getNeurons().size()
                                                    , prevHiddenLayer.getNeurons().size(), func);
        this.layers.add(2, newHidenlayer);
        return this.layers;
    }

    public List<LayerCommonI> getLayers() {
        return layers;
    }

    public void setLayers(List<LayerCommonI> layers) {
        this.layers = layers;
    }

    public Double getStepLearnimg() {
        return stepLearnimg;
    }

    public void setStepLearnimg(Double stepLearnimg) {
        this.stepLearnimg = stepLearnimg;
    }

    @Override
    public Double getMidSquareError() {
        return midSquareError;
    }

    @Override
    public Integer getNumberNeuronsInHiddenLayer() {
        HiddenLayer hiddenLayer = (HiddenLayer) this.layers.get(1);

        return hiddenLayer.getNeurons().size();
    }

    @Override
    public Integer getNumberHiddenLayers() {
        return this.layers.size() - 2;
    }


    public void setMidSquareError(Double midSquareError) {
        this.midSquareError = midSquareError;
    }

    public FunctionEncountingNodesInterface getFunc() {
        return func;
    }

    public void setFunc(FunctionEncountingNodesInterface func) {
        this.func = func;
    }

    public List<List<Double>> getDerivatives() {
        return derivatives;
    }

    public void setDerivatives(List<List<Double>> derivatives) {
        this.derivatives = derivatives;
    }



    @Override
    public String toString() {
        return "PredictiveNetwork{\n" +
                "layers=\n\t" + layers +
                /*", stepLearnimg=" + stepLearnimg +*/
                "\n\t, netErrors=" + netErrors +
                /*", func=" + func +*/
                "\n\t, derivatives=\n\t" + derivatives +
                '}';
    }


}
