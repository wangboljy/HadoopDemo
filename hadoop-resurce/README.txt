Hadoop Docs: http://hadoop.apache.org/docs/current/

download hadoop source: http://www.apache.org/dyn/closer.cgi/hadoop/common/
build 64 bits version: https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/NativeLibraries.html
setup single node mode: http://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-common/SingleCluster.html

config hadoop
http://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-common/core-default.xml
http://hadoop.apache.org/docs/current/hadoop-mapreduce-client/hadoop-mapreduce-client-core/mapred-default.xml
http://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-hdfs/hdfs-default.xml
http://hadoop.apache.org/docs/current/hadoop-yarn/hadoop-yarn-common/yarn-default.xml
http://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-common/DeprecatedProperties.html

for setup
format: bin/hadoop namenode -format
start hdfs: sbin/start-dfs.sh
start resource/node manager: sbin/start-yarn.sh


visit website:
hdfs: http://localhost:50070
mr: http://localhost:8088

mr tutorials:
http://hadoop.apache.org/docs/current/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html
