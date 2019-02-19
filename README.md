# DataQualityReportGenerator

Generate a csv data quality report from an [.arff](https://www.cs.waikato.ac.nz/ml/weka/arff.html "Attribute-Relation File Format (ARFF) Documentation") file.

## Description

A simple Java program for use as a data quality report creator from .arff formatted files. The current version of this program handles numerical and categorical features. Cardinality, quartiles, and standard deviation are calculated for numeric features. For categorical features, it will calculate cardinality and first and second mode data.

## Getting Started

### Installing

* Just download all three files into a folder with the .arff file you intend to generate a data quality report for

### Executing program

* Run the program from a terminal with the following commands:
```
javac GenerateDataQualityReport.java
java GenerateDataQualityReport "[FILENAME].arff"
```
