add jar hdfs:///user/wangboo/hive-demo/jars/Customization.jar;

create database if not exists test_database;
use test_database;

drop table if exists test_database.asinset;

create external table test_database.asinset(
	asin string,
	price double,
	dph int,
	fcsts string)
stored by 'hive.tutorial.customization.inputs.AsinHiveStorageHandler'
location '/user/wangboo/hive-demo/data/asinset/';

