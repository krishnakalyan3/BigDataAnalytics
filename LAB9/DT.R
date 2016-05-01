setwd("~/LAB09")
install.packages("rpart.plot")
library("rpart")
library("rpart.plot")
#Read the data
play_decision <- read.table("DTdata.csv",header=TRUE,sep=",")
play_decision
summary(play_decision)
#Build the tree to "fit" the model
fit <- rpart(Play ~ Outlook + Temperature + Humidity + Wind, method="class", data=play_decision,
    control=rpart.control(minsplit=1),parms=list(split='information'))
summary(fit)
#Plot the tree
rpart.plot(fit, type=4, extra=1)
#Predict if Play is possible for condition rainy, mild humidity, high temperature and no wind
 newdata <- data.frame(Outlook="rainy",Temperature="mild",Humidity="high",Wind=FALSE)
newdata
predict(fit,newdata=newdata,type=c("class"))

