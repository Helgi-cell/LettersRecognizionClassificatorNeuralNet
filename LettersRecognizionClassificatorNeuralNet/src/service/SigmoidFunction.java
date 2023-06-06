package service;

import Api.FinctionsApi.FunctionEncountingNodesInterface;

import java.io.Serializable;

import static java.lang.Math.exp;

public class SigmoidFunction implements FunctionEncountingNodesInterface, Serializable {

    public SigmoidFunction() {
    }

    @Override
    public Double nodeResult(Double argument) {
        return 1/(1 + exp(-1.0d * argument ));
    }

    @Override
    public Double derivativeResultOfNode(Double argument) { return argument * (1 - argument); }
}
