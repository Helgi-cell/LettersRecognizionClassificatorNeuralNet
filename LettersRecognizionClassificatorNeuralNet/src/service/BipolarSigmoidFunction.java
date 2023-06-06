package service;

import Api.FinctionsApi.FunctionEncountingNodesInterface;

public class BipolarSigmoidFunction implements FunctionEncountingNodesInterface {
    @Override
    public Double nodeResult(Double argument) {
        return 2.0d / (1.0d + Math.exp(argument * (-1.0d))) - 1.0d;
    }

    @Override
    public Double derivativeResultOfNode(Double argument) {
        return  (1 - Math.pow(argument, 2))/2;
    }
}
