#Application for converting Open Document Sheets to InfluxDb format


Simple converter that converts home-grown Open Office Excel sheets into entities.

The Excel contains daily money expenses in certain categories. Excel format for one sheet is following

timestamp | category1 | category2 | category3 | category4 <br/>
1.1.2016	12,50		200,60		160			
2.1.2016	16			500						0,5



Technologies used
- Spring Batch http://projects.spring.io/spring-batch/ 
- Spring Boot http://projects.spring.io/spring-boot/
- JOpenDocument API http://www.jopendocument.org/
- Lombok for generating getters, setters and toString
- SLFJ as logging facade and LogBack as logging implementation

And, what is InfluxDB
- Time series database
- Can be run as Docker image, see https://hub.docker.com/_/influxdb/
- provides line protocol https://docs.influxdata.com/influxdb/v0.13/write_protocols/line/ and HTTP API
