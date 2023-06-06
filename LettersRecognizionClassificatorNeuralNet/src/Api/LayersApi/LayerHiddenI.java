package Api.LayersApi;

import java.util.List;

public interface LayerHiddenI extends LayerCommonI {

    public List<Double> addNeuronInLayer(Integer prevNeuronsNum);
    public void changeWeigths();
}
