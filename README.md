# Busylight REST
This is a simple rest interface for [Busylight Driver](https://github.com/tckb/busylight_driver). The application has a built-in [`basic`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Authentication) authentication. The documentation of the rest service is available via the Open Api Console. 


## How to run
- Make sure you have [Busylight](https://www.busylight.com/en/kuando-busylight-uc-omega) plugged into your computer or wherever you wish to run this server on. Otherwise you will get this error:

```
Caused by: java.lang.UnsupportedOperationException: Unable to open the device, is the device connected?
    at com.fyayc.essen.busylight.core.Driver.<init> (Driver.java:43)
    at com.fyayc.essen.busylight.core.Driver.<init> (Driver.java:20)
    at com.fyayc.essen.busylight.core.Driver$DriverHelper.<clinit> (Driver.java:206)
    at com.fyayc.essen.busylight.core.Driver.tryAndAcquire (Driver.java:103)
    at com.fyayc.essen.busylight.server.ServerConfig.driver (ServerConfig.java:14)
    at com.fyayc.essen.busylight.server.ServerConfig$$EnhancerBySpringCGLIB$$b8283b75.CGLIB$driver$0 (<generate
```
Once, the device is connected, you can either build and run it or directly run using maven `spring:boot` goal.

- Make sure the driver is already installed. Follow the [instructions](https://github.com/tckb/busylight_driver/blob/x/ctu/Busylight/README.md#build-and-install) in the driver page.

### with maven plugin
```bash
# quick 
$ cd busylight_rest
$ mvn spring-boot:run -Dspring.security.user.name=busylight -Dspring.security.user.password=busylight
```

### build and run
```bash
# quick 
$ cd busylight_rest
$ mvn clean package
$ java -jar target/busylight-server-1.1.jar  --spring.security.user.name=busylight --spring.security.user.password=busylight
```
## Api-Console
Once the above command is run successfully, the console is available at port *8080*.  For more details, consult the [application.properties](https://github.com/tckb/busylight_rest/blob/master/src/main/resources/application.properties)

### Screenshots

## Installing as System.d service.

Please check the service tempate [here](https://github.com/tckb/busylight_rest/blob/master/src/main/resources/busylight.service.template)

## Show me something cool?

https://twitter.com/this_is_tckb/status/1171320757550485504

![](/media/shortcuts.gif)

