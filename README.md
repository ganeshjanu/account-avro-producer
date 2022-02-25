# account-avro-producer

This microservice is mainly used to produce records into a topic by using avro format.

APIs

HTTP POST = /account

HTTP POST = /accountTransaction

## Docker Commands

  1. Login to docker
  
        _docker login_

  2. Setup the Kafka env in docker
  
        _docker-compose -f docker-compose.yml up -d_
  
  3. List of Containers
  
        _docker ps -a_
  
  4. List of images
      
      _docker images_
      
 ## Topic Utility
 
  1. Inside the broker container
  
      _docker exec -it <kafka_broker_container_name> bash_
      
  2. Create a topic using utility
      
      _kafka-topics --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 2 --topic account_
    
  3. No of msg in a topic
  
      _kafka-console-consumer  --from-beginning --bootstrap-server 127.0.0.1:9092 --property print.key=true  --property print.value=false --property print.partition  --topic account --timeout-ms 5000 | tail -n 10|grep "Processed a total of"_
  
  4. Delete a topic
  
      _kafka-topics --zookeeper zookeeper:2181 --delete --topic account_


## Build & Run Microservice as a Container

  1. Go to Dockerfile directory

      _cd <Go_to_Dockerfile_directory>_

  2. Build an image
  
      _docker build -t <docker_username>/account-avro-producer:0.1 ._
  
  3. Run a container
  
      _docker run -p 9010:8080 <docker_username>/account-avro-producer-0.1_
  
  4. Push an image
  
      _docker push <docker_username>/account-avro-producer:0.1_
      
  
 ## Account & Account template files
 
  1. Currently the template files are under the src/main/resources/avro folder

  2. By using maven plugin, read the template files and create the respective beans.

