name := """slick-starting-on-the-right-foot"""

version := "1.0"

scalaVersion := "2.11.8"



libraryDependencies ++= Seq(
                      "mysql"                %     "mysql-connector-java"     %      "5.1.36",
		      "com.typesafe.slick"  %%     "slick-hikaricp"           %      "3.1.1",
                      "ch.qos.logback"       %     "logback-classic"          %      "1.1.3",
                      "com.typesafe.slick"   %%    "slick"            	      %      "3.1.1",
                      "org.scalatest"        %%    "scalatest"    	      %      "2.2.5"     %    "test",
                      "com.h2database"       % 	   "h2"                       %      "1.4.187"    %   "test"
)


