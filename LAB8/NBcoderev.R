setwd("~/LAB08")
# Let us get the appropriate libraries loaded for NB Classifier. Select a mirror closer to 
# your location to download the packages
install.packages("e1071")
library("e1071")
# read the data into a table from the file
sample <- read.table("sample1.csv",header=TRUE,sep=",")
# we will now define the data frames to use the NB classifier
traindata <- as.data.frame(sample[1:14,])
testdata <- as.data.frame(sample[15,])
#Display data frames
traindata
testdata
#Bulid the model maually
tprior <- table(traindata$Enrolls)
tprior <- tprior/sum(tprior)

ageCounts <-table(traindata[,c("Enrolls", "Age")])
ageCounts <- ageCounts/rowSums(ageCounts)
incomeCounts <- table(traindata[,c("Enrolls", "Income")])
incomeCounts <- incomeCounts/rowSums(incomeCounts)
jsCounts <- table(traindata[,c("Enrolls", "Jobsatisfaction")])
jsCounts<-jsCounts/rowSums(jsCounts)
desireCounts <- table(traindata[,c("Enrolls", "Desire")])
desireCounts <- desireCounts/rowSums(desireCounts)
#Predict - Compute the probabilities for Age<=30, Income = Medium,
#Jobsatisfaction = yes and Desire = Fair
pyes <- 
         ageCounts["Yes","<=30"]*
         incomeCounts["Yes","Medium"]*
         jsCounts["Yes","Yes"]*
         desireCounts["Yes","Fair"]*
         tprior["Yes"]
pno <- 
         ageCounts["No","<=30"]*
         incomeCounts["No","Medium"]*
         jsCounts["No","Yes"]*
         desireCounts["No","Fair"]*
         tprior["No"]
print (pyes)
print (pno)
print(max(pyes,pno))
# use the NB classifier 
model <- naiveBayes(Enrolls ~.,traindata)
# display model
model
# predict with testdata
results <- predict (model,testdata)
# display results
results

# use the NB classifier with Lapalce smoothing
model1 <- naiveBayes(Enrolls ~.,traindata,laplace=.01)
# display model
model1
# predict with testdata
results1 <- predict (model1,testdata)
# display results
results1

#Part2 - Read in the Data for the model
#Read input data from the database
#First up let us load the library RODBC
library('RODBC')
ch <- odbcConnect("Greenplum",uid="gpadmin",
      case="postgresql",pwd="changeme")
sqlDrop(ch,"NBtrain")
sqlQuery(ch,"
CREATE TABLE NBtrain (
     age VARCHAR(8),
     sex VARCHAR(8),
     educ VARCHAR(8),
     income VARCHAR(8) )
     DISTRIBUTED BY (age);
INSERT INTO NBtrain 
SELECT   
     t1.age 
     ,t1.sex
     ,t1.educ
     ,t1.income  
FROM 
     (
     SELECT 
         CASE 
            WHEN inctot BETWEEN 10000 AND 50000 THEN '10-50K' 
            WHEN inctot BETWEEN 50000+1 AND 80000 THEN '50-80K'
            WHEN inctot > 80000  THEN 'GT 80K'
                ELSE 'unknown i'
           END income ,
          CASE 
            WHEN age BETWEEN 20 AND 30 THEN '20-30'
            WHEN age BETWEEN 31 AND 45 THEN '31-45'
            WHEN age > 45 THEN 'GT 45' 
                ELSE 'unknown age'       
          END age, 
         CASE WHEN sex = 1  THEN 'M'
            WHEN sex = 2 THEN 'F'
             ELSE 'unknown sex'
         END  sex,
         CASE WHEN educ >14 THEN 'Prof/Phd'
            WHEN educ BETWEEN 12 AND 14 THEN 'College'
            WHEN educ <12 THEN 'Others'
              ELSE 'unknwon educ'
         END educ
FROM 
    persons) AS t1

WHERE 
    not (t1.age like 'unk%' or t1.sex like 'unk%' or t1.educ like 'unk%' or t1.income like 'unk%')
ORDER BY 
    RANDOM ()
    LIMIT 10010;
         ")
NBtrain <- (sqlFetch(ch,"NBtrain"))
NBtrain1 <- NBtrain[1:10000,]


# Let us Extract the last 10 records as testdata 

NBtest <- NBtrain[10001:10010,]
odbcClose(ch)
# model
model <- naiveBayes(income ~.,NBtrain1,laplace=.01)
model
# predict with testdata
results <- predict (model,NBtest[1:10,-1])
results

results1 <- predict (model,NBtest[1:10,-1],type="raw")
results1
conf <- table(actual=NBtest[1:10,4],predicted=results)
conf
accuracy  <- sum(diag(conf))/sum(conf)