# a2cm (Analytics To Custom Metrics)
AppDynamics Analytics to Controller custom metrics

This is a Machine Agent extension developed to query AppDynamics Analytics (Events Service) and return metric values readable by Machine Agent to be preserved in AppDynamics Controller.


To configure connectivity to AppDynamics Analytics the below information need to be populated in app-config.xml
```
eventsEndpoint : https://analytics.api.appdynamics.com
globalAccount : AccountName_xxxxxx-xxxxxx-xxxxxx-xxxxxx-xxxxxx
apiAccessKey : xxxxxx-xxxxxx-xxxxxx-xxxxxx-xxxxxx
```

To provide Appdynamics queries , metrics-config must be updated with required queries that will produce metrics using aggregate functions as in the below example:
```
listOfMetrics:
 - name: "Simple Query"
   metric-path: name=Custom Metrics|AnalyticsQuery|BT Count (1h)
   query: SELECT count(transactionName) FROM transactions SINCE 1 hours
```




