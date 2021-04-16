# Nubank Challenge

This project has the objective to implement a transaction authorizer


#### The required input format is alist of json object separated by new line

The input line must contain either a*account* object or a *transaction* object 

Account:
```json
{
  "account": {
    "active-card": "Boolean",
    "available-limit": "Number"
  }
}
```

Transaction:
```json
{
  "transaction": {
    "merchant": "String",
    "ammount": "Number",
    "time": "FormatedDateTime*"
  }
}
```

*FormatedDateTime**: YYYY-MM-ddTHH:mm:ss:SSSz

ex:
```bash
{"account": {"active-card": true, "available-limit": 100}}
{"transaction": {"merchant": "Burger King", "amount": 20, "time": "2019-02-13T10:00:00.000Z"}}
{"transaction": {"merchant": "Habbib's", "amount": 90, "time": "2019-02-13T11:00:00.000Z"}}
```

#### The output format is list of json object separated by new line
ex:
```bash
{"account":{"active-card":true,"available-limit":100},"violations":[]}
{"account":{"active-card":true,"available-limit":80},"violations":[]}
{"account":{"active-card":true,"available-limit":80},"violations":["insufficient-limit"]}
```

### Requirements Java Application

[Java 11](https://jdk.java.net/11/)

[Maven 3](https://maven.apache.org/download.cgi)

### Build the jar application

```bash
mvn package
```

### Run jar application
```bash
java -jar target/nuchallenge.jar < input/operations
```

### Requirements Docker Application

[Docker](https://www.docker.com/)

### Build docker image
```bash
docker build -t nuchallenge .
```

### Run docker container
```bash
cat input/operations | docker container run -i nuchallenge
```


### Note:

_[input/operations] options above could be any file path_ 