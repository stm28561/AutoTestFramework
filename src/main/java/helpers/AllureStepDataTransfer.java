package helpers;

import io.qameta.allure.model.StepResult;

public class AllureStepDataTransfer {
    private static AllureStepDataTransfer instance;

    private String lastStepUUID;
    private StepResult lastStepResult;

    private AllureStepDataTransfer() {
    }

    public static AllureStepDataTransfer getInstance() {
        if (instance == null) {
            instance = new AllureStepDataTransfer();
        }
        return instance;
    }

    public String getLastStepUUID() {
        return lastStepUUID;
    }

    public void setLastStepUUID(String lastStepUUID) {
        this.lastStepUUID = lastStepUUID;
    }

    public StepResult getLastStepResult() {
        return lastStepResult;
    }

    public void setLastStepResult(StepResult lastStepResult) {
        this.lastStepResult = lastStepResult;
    }
}
