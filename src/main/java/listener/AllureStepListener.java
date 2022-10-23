package listener;

import helpers.AllureStepDataTransfer;
import helpers.CustomUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.StepResult;

public class AllureStepListener implements StepLifecycleListener {
    private AllureStepDataTransfer data = AllureStepDataTransfer.getInstance();

    @Override
    public void afterStepStart(StepResult result) {
        String uuid = Allure.getLifecycle().getCurrentTestCaseOrStep().get();
        this.data.setLastStepUUID(uuid);
        this.data.setLastStepResult(result);
    }

    @Override
    public void afterStepUpdate(StepResult result) {
        this.data.setLastStepResult(result);
    }

    @Override
    public void beforeStepStop(StepResult result) {
        String uuid = Allure.getLifecycle().getCurrentTestCaseOrStep().get();
        this.data.setLastStepUUID(uuid);
        this.data.setLastStepResult(result);

    }
}
