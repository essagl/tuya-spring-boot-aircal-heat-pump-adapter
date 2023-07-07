package de.essagl.tuya.aircal.adapter.service.errorprocessor;

import com.tuya.connector.api.context.Context;
import com.tuya.connector.api.error.ErrorInfo;
import com.tuya.connector.api.error.ErrorProcessor;
import com.tuya.connector.api.exceptions.ConnectorException;
import com.tuya.connector.api.plugin.Invocation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubscriptionExpiredProcessor implements ErrorProcessor {

    @Override
    @SneakyThrows
    public Object process(ErrorInfo errorInfo, Invocation invocation, Context context) {
        log.warn("SubscriptionExpiredProcessor: {}", errorInfo);
         throw new ConnectorException(errorInfo.toString()+ " The cloud development enterprise edition subscription has expired and there is no permission.)");
    }

    @Override
    public String getErrorCode() {
        return "28841002";
    }
}
