(1) Created Maven Project
(2) Imported the JMS artemis all client dependency
(3) Created - JNDI property file under resources (make sure the name 
	of the file is "jndi.properties", bcoz thats what activeMQ will look for the file for initial context)
(4) Under the property file: configur JNDI properties

		Define the initial context: command + shift + T (search ActiveMQInitial) -> retrieve packagename + class name

		java.naming.factory.initial=org.apache.activemq.artemis.jndi.ActiveMQInitialContextFactory
		
		-- Default location where JMS server runs
		connectionFactory.ConnectionFactory=tcp://localhost:61616

		queue.queue/myQueue=dmQueue

(5) Create connection
(6) Create session
(7) Look up for the destination (queue)
(8) Send/Receive the message


#############

(1) JMS Basics Package:
	- First Queue - Point 2 Point Method - Using JMS 1.0
	- First Topic - Publisher/Subscriber - Using JMS 1.0
	- JMS Context Demo - Using JMS 2.0
	- Queue Browser - Browsing messages in Queue.

(2) Message Structure Package
	- Message Priority: message can be send using the setPriority method of the producer.

	- Request Reply structure
		- setJMSReplyTo queue to message for consumer to send ack back to producer
		- createTemporaryQueue - create temporary queue for ack
		- Retrieve JMSMessageID from the received message at consumer
		- Use the same JMSMessageID to setJMSCorrelationID in ack message back to Initial producer by creating producer, since producer is the one who can send message
		- At initial producer create another consumer to retrieve ack message and correlation id to co-relate intial request, and it could be at temporary reply queue / permenent reply queue.

	- setTimeToLive: set Expiry Time  for message to live. time being set in millisecond.	
	
	- Expiry Queue - Add JNDI entry for expiry queue to refer, default expiry queue config available under broker.xml

	- setDeliveryDelay: it will allow to add delay to the message.

	- Custom properties: setBooleanProperty, setStringProperty, setDouble/int/float..etc: it will have key name value pair. It will be used to send any metadata.

	- Different Message Type: TextMessage, ByteMessage, ObjectMessage, StreamMessage, MapMessage

	

<img width="724" alt="image" src="https://github.com/DRModi/JMS/assets/30615418/14412427-1c10-4943-af03-8c1429004df9">

	


