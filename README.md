# Recommendation System
A method call recommender based on implicit developer feedback.

## Getting Started

This project is based on Amann et al.'s publication "Method-call recommendations from implicit developer feedback". To get started make yourself familiar with the concept by reading their [paper](https://doi.org/10.1145/2593728.2593730).

The proposed recommendation system learns from implicit developer feedback, i.e. it analyzes the selected methods and updates the model accordingly.
 
With this implementation, the recommender can propose method calls based on a previously learned model.
### Building
To build all components, execute following command in the root folder:

``mvn clean install``

The components can be built separately. For this purpose, navigate to the module root and execute the same command.

### Example

The recommendation system itself is located in the recommender module. To get started take a look at the example class at 

``recommender\src\main\java\ch\uzh\ifi\seal\ase\cin\recommender\Example.java``

A sample implementation of a method recommender is located at 

``recommender\src\main\java\ch\uzh\ifi\seal\ase\cin\recommender\BasicMethodRecommender.java``

The recommendation system itself is an implementation of KaVE's _ICallsRecommender_ interface. Additionally, RecommendatonSystem provides a method to provide actual user selections. This allows the model to improve based on implicit developer feedback.

## Mining
This project includes a miner which uses KaVE's [context data set](http://www.kave.cc/datasets). The miner analyzes method invocations to build an initial model. 

## Evaluation
The current implementation was tested against 175'000 completions collected by [KaVE](http://www.kave.cc/datasets) from real life developer work containing almost 4000 method invocations. The tested model was gained by the mainer as explained above. The results are presented in the table below:

| Events            | Exact Method  | Method Name   |
| ----------------- |:-------------:| -------------:|
| Total             |  3942         |               |
| Top proposal      |   249 (7%)    |               |
| Top 10 proposals  |   470 (13%)   |               |

We evaluated whether the actual selection was within the best 1 or 10 proposed methods. Additionally, we compared the results between matching the correct overloaded method and the correct method name (i.e. ignoring overloading).

There are several reasons explaining the results:
- The method recommender uses exact query matching which results in low matchin queries. Amann proposed a nearest neighbour approach which has not been implemented yet.
- The results are heavily dependent on the overlap of the libraries used in the test and validation set respectively. Method recommendation based on implicit developer feedback provides only value for libraries that have been used before.
- The recommendation system's strength is incremental, i.e. it gets better the more it is used. For this course, we were not able to conduct a long term study which would provide better insight on the strength of this approach.
## Artifacts 
The artifacts are published in their own [repository](https://github.com/NicoSB/Recommendation-System-Repo). 

