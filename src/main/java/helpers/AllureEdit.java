package helpers;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.internal.AllureStorage;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AllureEdit {
    private static AllureStepDataTransfer dataTransfer = AllureStepDataTransfer.getInstance();

    private static void replaceStepFromAllureReport(String uuid, StepResult result) {
        AllureLifecycle lifecycle = Allure.getLifecycle();
        AllureStorage lifecycleStorage = null;
        try {
            Field lifecycleStorageField = lifecycle.getClass().getDeclaredField("storage");
            lifecycleStorageField.setAccessible(true);
            lifecycleStorage = (AllureStorage) lifecycleStorageField.get(lifecycle);
            Field allureStorageField = lifecycleStorage.getClass().getDeclaredField("storage");
            allureStorageField.setAccessible(true);
            Map<String, Object> allureStorage = (Map<String, Object>) allureStorageField.get(lifecycleStorage);
            allureStorage.replace(uuid, result);
            allureStorageField.set(lifecycleStorage, allureStorage);
            lifecycleStorageField.set(lifecycle, lifecycleStorage);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setLastStepStatusFailed() {
        String uuid = dataTransfer.getLastStepUUID();
        StepResult result = dataTransfer.getLastStepResult();
        result.setStatus(Status.FAILED);
        replaceStepFromAllureReport(uuid, result);
    }

    public static void setLastStepName(String newStepName) {
        String uuid = dataTransfer.getLastStepUUID();
        StepResult result = dataTransfer.getLastStepResult();
        result.setName(newStepName);
        replaceStepFromAllureReport(uuid, result);
    }

    public static void deleteLastStepParameter(String parameterName) {
        String uuid = dataTransfer.getLastStepUUID();
        StepResult result = dataTransfer.getLastStepResult();
        List<Parameter> parameters = result.getParameters();
        parameters = parameters.stream().filter(parameter -> !parameter.getName().equals(parameterName)).collect(Collectors.toList());
        result.setParameters(parameters);
        replaceStepFromAllureReport(uuid, result);
    }
}
