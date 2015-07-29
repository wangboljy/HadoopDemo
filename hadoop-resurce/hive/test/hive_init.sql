create database if not exists test_database;
use test_database;

create table test_database.asin_override(
	Asin string, 
	Quantile string, 
	Quantity double, 
	EvaluationTrackingID string, 
	OverrideCategory string, 
	OverrideDecisionCode int,
	OverrideDecisionReason string, 
	reason string)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE;
