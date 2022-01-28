import pandas as pd
import time
import json
from pymongo import MongoClient
import pymongo

def get_database():
    # Provide the mongodb atlas url to connect python to mongodb using pymongo
    CONNECTION_STRING = "mongodb+srv://user:user@cluster0.42wft.mongodb.net/Mycoiffeur?retryWrites=true&w=majority"
    # Create a connection using MongoClient. You can import MongoClient or use pymongo.MongoClient
    from pymongo import MongoClient
    client = MongoClient(CONNECTION_STRING)
    # Create the database for our example (we will use the same database throughout the tutorial
    return client['Mycoiffeur']
# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    start_time = time.time()
    dbname = get_database()
    collection = dbname['Reviews']
    item_details = collection.find()
    items = []
    for item in item_details:
        # This does not give a very readable output
        items.append([item['clientId'], item['profileId'], item['note']])
    items_df = pd.DataFrame(items, columns=['clientId', 'profileId', 'note'])
    df = items_df.pivot_table(index='clientId', columns='profileId', values='note')
    item_util_matrix = df.apply(lambda col : col.fillna(col.mean()), axis=0)
    item_corr_matrix = item_util_matrix.corr()
    recommendation = []
    for index in item_corr_matrix:
        coiffeurI_corr = item_corr_matrix[index]
        coiffeurI_corr = coiffeurI_corr.sort_values(ascending=False)
        coiffeurI_corr.dropna(inplace=True)
        coiffeurs_similar_to_coiffeur4 = pd.DataFrame(data=coiffeurI_corr.values, columns=['Correlation'],
                                                      index=coiffeurI_corr.index)
        coiffeurs_similar_to_coiffeur4 = coiffeurs_similar_to_coiffeur4.join(
            pd.DataFrame(data= 100 - df.isna().sum(), columns=['total ratings'], index=coiffeurI_corr.index))
        similar = coiffeurs_similar_to_coiffeur4[(coiffeurs_similar_to_coiffeur4['Correlation'] > 0)].sort_values(
            ascending=False, by=['Correlation'])
        similar = similar.to_dict()
        similar['_id'] = index
        recommendation.append(similar)
    dbname["Recommendation"].insert_many(recommendation)



