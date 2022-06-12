# Spring Boot - Quartz-Email Scheduling

1
```
POST(http://localhost:8080/api/schedule-info/schedule)
{
"email":"asefacihangir@gmail.com",
"subject":"Özlü Sözler",
"body":"Bir iş yaparken, bir hedefe yürürken mükemmel olmaya değil; istikrar ve kararlılıkla adım atmaya ihtiyacın var. (Pınar Kaçar Özkent)",
"dateTime":"2022-06-12T10:27:00",
"timeZone":"Europe/Istanbul"
}
```
Servisi ile job sisteme kaydedilir ve bu bilgiler kullanılarak yeni bir job oluşturulur.Oluşturduğumız job; serviste verdiğimiz **dateTime** zamanında çalışacaktır.


