x <- runif(100, 0, 10)                  # 100 draws between 0 & 10
y <- 5 + 6*x + rnorm(100)               # beta = [2, 3] and sigma = 1
#Plot it
plot (x,y)
# OLS model
d <- lm(y ~ x)
# Learn about this object by saying ?lm and str(d)
str(d)
# Compact model results --
print(d)
# Pretty graphics for regression diagnostics --
par(mfrow=c(2,2))
plot(d)
ypred <- predict(d)
par(mfrow=c(1,1))
plot(y,y, type="l", xlab="true y", ylab="predicted y")
points(y, ypred)

d1 <- summary(d)
# Detailed model results --
print(d1)
summary(d1)
# Learn about this object by saying ?summary.lm and by saying str(d)
cat("OLS gave slope of ", d1$coefficients[2,1],   
    "and an R-sqr of ", d1$r.squared, "\n")
x1 <- runif(100)
# introduce a slight nonlinearity
y1 = 5 + 6*x1 + 0.1*x1*x1 + rnorm(100) 
m <- lm(y1 ~ x1)
x2 <- runif(100)
y2 = 5 + 6*x2  + 0.1*x2*x2 + rnorm(100)
y2pred <- predict(m,data.frame(x2))

par(mfrow=c(1,1))
plot(y2,y2, type="l", xlab="true y", ylab="predicted y")
points(y2, y2pred)

# Learn about this object by saying ?summary.lm and by saying str(d)
cat("OLS gave slope of ", d1$coefficients[2,1],   
    "and an R-sqr of ", d1$r.squared, "\n")

x1 <- runif(100)
# introduce a slight nonlinearity
y1 = 5 + 6*x1 + 0.1*x1*x1 + rnorm(100) 
m <- lm(y1 ~ x1)
x2 <- runif(100)
y2 = 5 + 6*x2  + 0.1*x2*x2 + rnorm(100)
y2pred <- predict(m,data.frame(x2))

par(mfrow=c(1,1))
plot(y2,y2, type="l", xlab="true y", ylab="predicted y")
points(y2, y2pred)

x2 <- x1
y2 = y1 + 2*x2*x2
par(mfrow=c(1,1))
plot(x2,y2)
d2 <- lm(y2 ~ x2)
summary(d2)

par(mfrow=c(2,2))
plot(d2)
par(mfrow=c(1,1))
plot (x2,y2)
points(sort(x2), d2$fitted[order(x2)], type = "l")
x2squared <- x2^2
d3 <- lm(y2 ~ x2 + x2squared)
summary(d3)

par(mfrow=c(2,2))
plot(d3)
par(mfrow=c(1,1))
plot(x2,y2)
points(sort(x2), d3$fitted[order(x2)], type = "l")



