import pickle
import pandas as pd
import numpy as np
from flask import Flask, request, jsonify
from sklearn.preprocessing import StandardScaler
from sklearn.ensemble import RandomForestClassifier

# Load the trained model and scaler
with open('model.pkl', 'rb') as f:
    model, scaler = pickle.load(f)

categorical_features = ['blue', 'dual_sim', 'four_g', 'three_g', 'touch_screen', 'wifi']


numerical_features = ['battery_power', 'clock_speed', 'fc', 'int_memory', 'm_dep', 
                      'mobile_wt', 'n_cores', 'pc', 'px_height', 'px_width', 'ram', 
                      'sc_h', 'sc_w', 'talk_time']


app = Flask(__name__)

@app.route('/predict', methods=['POST'])
def predict():
    data = request.get_json(force=True)
    features = data['features']

   
    df = pd.DataFrame(features,index=[1])
    print(df.head())
    
    #preprocessing the input
    preprocessed_numerical_features = scaler.transform(df[numerical_features])

    # Combine the preprocessed numerical features with the categorical features
    preprocessed_features = np.concatenate([preprocessed_numerical_features, df[categorical_features]], axis=1)

    
    # Make the prediction using the loaded model
    prediction = model.predict(preprocessed_features)

    prediction = int(prediction)

    return jsonify({'prediction': prediction})

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=7000)