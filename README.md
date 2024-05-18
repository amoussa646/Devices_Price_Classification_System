Devices Price Classification System:

Devices Price Classification System using Python, Flask and Spring Boot.

To run the system:

  Python:   
    Run:
      cd Python
      pip install Flask
      python predictor.py 
      
    it will run on host='0.0.0.0', port=7000
9090
  Spring Boot:  
      cd SpringBoot
      ./gradlew bootRun
      
    it will run on host='0.0.0.0', port=9090


The first 10 devices in the test.csv are already added and their respective price_ranges are predicted and saved to their entities


End points :

   POST request : http://localhost:9090/api/device
      body : {
  "batteryPower": 1520,
  "blue": false,
  "clockSpeed": 0.5,
  "dualSim": false,
  "fc": 1,
  "fourG": false,
  "intMemory": 25,
  "mDep": 0.5,
  "mobileWt": 171,
  "nCores": 3,
  "pc": 20,
  "pxHeight": 52,
  "pxWidth": 1009,
  "ram": 651,
  "scH": 6,
  "scW": 0,
  "talkTime": 5,
  "threeG": true,
  "touchScreen": false,
  "wifi": true
}
    adds this device to the database (you can specify an id)

  GET request: http://localhost:9090/api/devices
    gets all the devices

  GET request: http://localhost:9090/api/devices/10
    gets the device with id 10

  POST request: http://localhost:9090/api/devices/predict/10
    predicts the price range of device with id 10
  
  

Files:

  Python/ml.py: 
      
      loading , inspecting ,splitting, preprocessing, training, evaluation, testing, saving 

      Replaced the missing values with Mean for the numerical features and Mode for the categorical features 
      
      Insights from Heat Map: 
      
        - there is a very strong positive correlation between ram and price (0.92)
        - there is moderate correlation between the following :
            px_height & px_width
            pc & fc
            three_g & four_g
            sc_h & sc_w
            

       Performed Standaradization 
      
       Note:   
           Attemted PCA and MCA to reduce dimentionality but they affected the model's accuracy negatively    
    


      The Classification Model is a Random Forest Classsifier Model. 


      Used K-folds  for Cross- Validation

      
      Model's results:

      Cross-validation scores:      
        [0.890625, 0.828125, 0.896875, 0.853125, 0.878125]
      Mean cross-validation score:
        0.869375
      Confusion Matrix:
        [[100   5   0   0]
        [  6  77   8   0]
        [  0   7  78   7]
        [  0   0  14  98]]
      Classification Report:
                    precision    recall  f1-score   support

               0       0.94      0.95      0.95       105
               1       0.87      0.85      0.86        91
               2       0.78      0.85      0.81        92
               3       0.93      0.88      0.90       112

        accuracy                           0.88       400
       macro avg       0.88      0.88      0.88       400
    weighted avg       0.89      0.88      0.88       400



 
  Python/predictor.py

      An endpoint using Flask to to preprocess the input and do inference 

     
  
      
  DemoApplication.java
  
      The main application class that serves as the entry point for the Spring Boot application. 

  Device.java
  
      The entity class representing a device. 

  DeviceRepository.java
  
      Description: A repository interface for performing operations on Device entities. 

  DeviceService.java
  
      A service class that handles the logic for managing devices.

  DeviceController.java
  
      A REST controller class that defines endpoints for interacting with devices.
      Calls Flask's endpoint on "http://0.0.0.0:7000/predict";

  build/resources/main/application.properties:

     Database Configuration
        spring.datasource.url=jdbc:h2:file:~/testdb
        spring.datasource.driverClassName=org.h2.Driver
        spring.datasource.username=sa
        spring.datasource.password=password
        spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

     Server Configuration
        server.port=9090

        


    
  
