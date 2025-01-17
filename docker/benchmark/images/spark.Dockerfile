
FROM openjdk:8u332-jdk

WORKDIR /usr/local/arctic_spark

RUN apt update \
    && apt-get install -y vim \
    && apt-get install -y net-tools \
    && apt-get install -y telnet

RUN wget https://dlcdn.apache.org/spark/spark-3.1.3/spark-3.1.3-bin-hadoop2.7.tgz \
    && tar -zxvf spark-3.1.3-bin-hadoop2.7.tgz \
    && rm -rf spark-3.1.3-bin-hadoop2.7.tgz
ENV SPARK_HOME=/usr/local/arctic_spark/spark-3.1.3-bin-hadoop2.7 \
    PATH=${PATH}:${SPARK_HOME}/bin
RUN wget https://github.com/NetEase/arctic/releases/download/v0.3.2-rc1/arctic-spark_3.1-runtime-0.3.2.jar \
    && cp arctic-spark_3.1-runtime-0.3.2.jar ${SPARK_HOME}/jars
COPY spark-config/spark-defaults.conf ${SPARK_HOME}/conf
COPY hive-config/hive-site.xml ${SPARK_HOME}/conf

WORKDIR ${SPARK_HOME}/jars
RUN wget https://repo1.maven.org/maven2/org/apache/iceberg/iceberg-spark-runtime-3.1_2.12/1.0.0/iceberg-spark-runtime-3.1_2.12-1.0.0.jar
RUN wget https://repo1.maven.org/maven2/org/apache/hudi/hudi-spark3.1-bundle_2.12/0.12.1/hudi-spark3.1-bundle_2.12-0.12.1.jar
RUN wget https://repo1.maven.org/maven2/io/delta/delta-core_2.12/1.0.0/delta-core_2.12-1.0.0.jar

RUN mkdir -p -m 777 /tmp/hive/warehouse
RUN mkdir -p -m 777 /tmp/arctic/warehouse
WORKDIR ${SPARK_HOME}

CMD ["bash","-c","./sbin/start-thriftserver.sh && tail -f /dev/null"]


