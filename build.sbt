name := """slick-for-production"""

version := "1.0"

scalaVersion := "2.11.7"



libraryDependencies ++= Seq(
                      "mysql"                %     "mysql-connector-java"     %      "5.1.36",
                      "ch.qos.logback"       %     "logback-classic"          %      "1.1.3",
                      "com.zaxxer"           %      "HikariCP-java6"          %      "2.3.9",
                      "com.typesafe.slick"   %%    "slick"            	      %      "3.0.1",
                      "org.scalatest"        %%    "scalatest"    	      %      "2.2.5"     %    "test",
                      "com.h2database"       % 	   "h2"                       %      "1.4.187"    %   "test"
)


