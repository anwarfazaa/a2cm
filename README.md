# a2cm (Analytics To Custom Metrics)
AppDynamics Analytics to Controller custom metrics

This is a Machine Agent extension developed to query AppDynamics Analytics (Events Service) and return metric values readable by Machine Agent to be preserved in AppDynamics Controller.


To configure connectivity to AppDynamics Analytics the below information need to be populated in app-config.xml
```
eventsEndpoint : https://analytics.api.appdynamics.com
globalAccount : AccountName_xxxxxx-xxxxxx-xxxxxx-xxxxxx-xxxxxx
apiAccessKey : xxxxxx-xxxxxx-xxxxxx-xxxxxx-xxxxxx
```

To provide Appdynamics queries , config.xml must be updated with required queries that will produce metrics using aggregate functions as in the below example:
```
<metric-list>
    <analyticsmetric>
        <name>Simple Query</name>
        <metric-path>name=Custom Metrics|AnalyticsQuery|BT Count (1h)</metric-path>
        <query>SELECT count(transactionName) FROM transactions SINCE 1 hours</query>
    </analyticsmetric>
</metric-list>
```

