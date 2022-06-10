# Spring Boot - Quartz Scheduler Demo

1. POST (http://localhost:8080/api/fruit ) servisi ile sisteme meyveler kaydedilir.
2.
```
POST(http://localhost:8080/api/schedule-info/schedule)
{
"jobName":"job2",
"jobGroup":"group2",
"startTime":"2022-06-10 08-00-00",
"counter":1,
"gapDuration": 1
}
```
Servisi ile job sisteme kaydedilir ve bu bilgiler kullanılarak yeni bir job oluşturulur.Oluşturduğumız job; serviste verdiğimiz **startTime** zamanında çalışacaktır.


